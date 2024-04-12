package com.luizalabs.logistica.fixtures.controller;

import com.luizalabs.logistica.controller.response.OrderResponse;
import com.luizalabs.logistica.controller.response.ProductResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponseFixture {

    public static OrderResponse createOrderResponse(final Integer orderId, final LocalDate date, final List<ProductResponse> products, final BigDecimal total) {
        return OrderResponse.builder()
                .orderId(orderId)
                .date(date)
                .products(products)
                .total(total)
                .build();
    }
}
