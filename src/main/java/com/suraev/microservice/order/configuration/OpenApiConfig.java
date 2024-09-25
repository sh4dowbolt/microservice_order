package com.suraev.microservice.order.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        title = "microservice_order",
        description = "Order Microservice", version = "0.0.1-SNAPSHOT",
        contact = @Contact(
                name = "Suraev Vitalij",
                email = "suraevvvitaly@gmail.com",
                url = "https://t.me/sh4dowb0lt"
        )
))
public class OpenApiConfig {
}
