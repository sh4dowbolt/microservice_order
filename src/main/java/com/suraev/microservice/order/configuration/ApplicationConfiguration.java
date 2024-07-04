package com.suraev.microservice.order.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suraev.microservice.order.service.OrderService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.jackson.ProblemModule;

@Configuration
public class ApplicationConfiguration {
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new ProblemModule());
    }

    @Bean
    public ProblemModule problemModule() {
        return new ProblemModule().withStackTraces(false);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }

}
