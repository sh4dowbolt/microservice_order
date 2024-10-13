package com.suraev.microservice.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Schema(description = "Сущность продукта")
public class Product {

    @Id
    private String id;

    @Field("product_title")
    private String title;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant updatedAt;

    @Field("product_details")
    private String productDetails;

    //TODO: сделать загрузку изображения для продукта
    @Field("image")
    private String image;

}
