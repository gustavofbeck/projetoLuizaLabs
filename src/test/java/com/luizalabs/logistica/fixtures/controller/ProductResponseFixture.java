package com.luizalabs.logistica.fixtures.controller;

import com.luizalabs.logistica.controller.response.ProductResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseFixture {

    public static ProductResponse createProductResponse(final Integer productId, final BigDecimal value) {
        return ProductResponse.builder()
                .productId(productId)
                .value(value)
                .build();
    }
}
