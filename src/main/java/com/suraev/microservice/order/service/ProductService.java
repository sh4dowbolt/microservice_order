package com.suraev.microservice.order.service;

import com.suraev.microservice.order.domain.Order;
import com.suraev.microservice.order.exception.ProductOrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${spring.application.microservice-product.url}")
    private String productBaseUrl;

    private static final String PRODUCT_ORDER_URL = "checkProducts";

    public boolean checkOrderProducts(Order order) {
        final var url = productBaseUrl+PRODUCT_ORDER_URL;
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("Order request URL: {}",url);
        try {
            final var request = new HttpEntity<>(order, headers);
            final var responseEntity = restTemplate.postForEntity(url, request, Boolean.class);
            
            if (responseEntity.getStatusCode().isError()) {
                log.error("For Order ID: {}, error response: {} is received to check Order in Product Microservice", order.getId(), responseEntity.getStatusCodeValue());
                throw new ProductOrderException(order.getId(), responseEntity.getStatusCodeValue());
            }
            if (responseEntity.hasBody() && responseEntity.getBody() != null) {
                log.info("Order was checked, result is {}", responseEntity.getBody());
                return responseEntity.getBody();
            }
        } catch (Exception e) {
            log.error("For Order ID: {}, cannot check existed products in Product Microservice for reason: {}", order.getId(),e.getMessage());
            throw new ProductOrderException(order.getId(),e.getMessage());
        }
        return false;

    }

}
