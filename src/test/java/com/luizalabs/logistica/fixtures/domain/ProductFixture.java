package com.luizalabs.logistica.fixtures.domain;

import com.luizalabs.logistica.domain.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductFixture {

    public static Product createProduct(final Integer productId, final BigDecimal value) {
        return Product.builder()
                .productId(productId)
                .value(value)
                .build();
    }
}
