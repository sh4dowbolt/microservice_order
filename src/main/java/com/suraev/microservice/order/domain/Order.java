package com.suraev.microservice.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Document(collection = "order")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "Сущность заказа")
public class Order implements Serializable {
    private static final long serialVersionUID= 1L;

    @Id
    @Schema(description = "Идентификатор заказа", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotBlank
    @Field("customer_id")
    @Schema(description = "Идентификатор клиента")
    private String customerId;

    @Field("created_at")
    @CreatedDate
    @Schema(description = "Время создания")
    private Instant createdAt;

    @Field("updated_at")
    @LastModifiedDate
    @Schema(description = "Время обновления")
    private Instant updatedAt;

/*    @Version
    public Integer version;*/
    @Field("status")
    @Schema(description = "Статус")
    private OrderStatus status= OrderStatus.CREATED;

    @Field("payment_status")
    @Schema(description = "Статус оплаты заказа")
    private Boolean paymentStatus = Boolean.FALSE;

    @NotNull
    @Field("payment_details")
    @Schema(description = "Детали платежа")
    private String paymentDetails;

    @Field("shipping_address")
    @Schema(description = "Адрес")
    private Address shippinhAddress;

    @Field("products")
    @Schema(description = "Список продуктов")
    @NotEmpty
    private Set<@Valid Product> products;


    public void addProductToOrder(@Valid Product product) {
        if (products == null) {
            products = new HashSet<>();
        }
        products.add(product);
    }
    public void removeProductFromOrder(@Valid Product product) {
        products.remove(product);
    }


}
