package com.suraev.microservice.order.exception;

import org.zalando.problem.AbstractThrowableProblem;

import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;

public class ProductOrderException extends AbstractThrowableProblem {

        public ProductOrderException(final String uuid, final String reason) {
            super(ErrorConstants.ORDER_MICROSERVICE, "Product Microservice Error", INTERNAL_SERVER_ERROR, String.format("For Order UUID: %s, Order Microservice Message: %s", uuid, reason));
        }

        public ProductOrderException(final String uuid, final int response) {
            super(ErrorConstants.ORDER_MICROSERVICE, "Product Microservice Bad Request", BAD_REQUEST, String.format("For Order UUID: %s, Order Microservice Response: %d", uuid, response));
        }

}
