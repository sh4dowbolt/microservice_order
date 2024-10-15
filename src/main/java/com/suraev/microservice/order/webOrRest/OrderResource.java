package com.suraev.microservice.order.webOrRest;


import com.suraev.microservice.order.domain.Order;
import com.suraev.microservice.order.exception.BadRequestAlertException;
import com.suraev.microservice.order.repository.OrderRepository;
import com.suraev.microservice.order.service.OrderService;
import com.suraev.microservice.order.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name="Управление заказами", description = "Панель управления заказами")
public class OrderResource {
    private final Logger log = LoggerFactory.getLogger(OrderResource.class);
    private static final String ENTITY_NAME = "order";

    @Value("${spring.application.name}")
    private String applicationName;

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderResource(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @Operation(summary = "Создание заказа", description = "Позволяет создавать заказ")
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to save Order: {}", order);

        if(order.getId()!=null) {
            throw new BadRequestAlertException("A new order cannot already have an ID",ENTITY_NAME,"idexist");
        }

        final var result = orderRepository.save(order);
        orderService.createOrder(result);

        HttpHeaders headers= new HttpHeaders();
        String message = String.format("A new %s is created with identifier %s", ENTITY_NAME, result.getId().toString());
        headers.add("X-" + applicationName + "-alert", message);
        headers.add("X-" + applicationName + "-params", result.getId().toString());

        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).headers(headers).body(result);
    }

    @Operation(summary = "Обновить заказ", description = "Позволяет обновить существующий заказ клиента")
    @PutMapping("/orders")
    @Transactional
    public ResponseEntity<Order> update(@Valid @RequestBody Order order) {
        log.debug("REST request to update Order: {}", order);

        if(order.getId() == null) {
            throw new BadRequestAlertException("Invalid id",ENTITY_NAME,"idnull");
        }
        final var result = orderRepository.save(order);
        orderService.updateOrder(result);

        HttpHeaders headers= new HttpHeaders();
        String message = String.format("A %s is updated with identifier %s", ENTITY_NAME, result.getId().toString());
        headers.add("X-" + applicationName + "-alert", message);
        headers.add("X-" + applicationName + "-params", result.getId().toString());

        return ResponseEntity.ok().headers(headers).body(result);
    }

    @Operation(summary = "Получить все заказы", description = "Позволяет получить все существующие заказы")
    @GetMapping("/orders")
    @Transactional
    public List<Order> getAllOrders() {
        log.debug("REST request to get all Orders");
        return orderRepository.findAll();
    }

    @Operation(summary = "Получить заказ по ID", description = "Позволяет получать заказ по ID")
    @GetMapping("/orders/{id}")
    @Transactional
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        log.debug("REST request to get Order : {}", id);
        final var order = orderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(order);
    }

    @Operation(summary = "Удалить заказ", description = "Позволяет удалить заказ по ID")
    @DeleteMapping("/orders/{id}")
    @Transactional
    public ResponseEntity <Void> deleteOrder(@PathVariable String id) {
        log.debug("REST request to delete Order: {}", id);

        final var orderOptional = orderRepository.findById(id);
        orderRepository.deleteById(id);

        if(orderOptional.isPresent()) {
            orderService.deleteOrder(orderOptional.get());
        }

        HttpHeaders headers= new HttpHeaders();
        String message = String.format("A %s is deleted with identifier %s", ENTITY_NAME, id);
        headers.add(applicationName, message);

        return ResponseEntity.noContent().headers(headers).build();

    }
}
