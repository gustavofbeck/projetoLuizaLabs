package com.luizalabs.logistica.fixtures.controller;

import com.luizalabs.logistica.controller.response.OrderResponse;
import com.luizalabs.logistica.controller.response.UserOrdersResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.luizalabs.logistica.fixtures.controller.OrderResponseFixture.createOrderResponse;
import static com.luizalabs.logistica.fixtures.controller.ProductResponseFixture.createProductResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOrdersResponseFixture {

    public static List<UserOrdersResponse> createUserOrdersResponseList() {
        return List.of(
                createUserOrdersResponse(1, "Gustavo Felipe", List.of(
                        createOrderResponse(1, LocalDate.parse("2024-01-01"), List.of(createProductResponse(1, BigDecimal.valueOf(100))), BigDecimal.valueOf(100)),
                        createOrderResponse(2, LocalDate.parse("2024-01-10"), List.of(createProductResponse(2, BigDecimal.valueOf(100))), BigDecimal.valueOf(100))
                )),
                createUserOrdersResponse(2, "Felipe Beck", List.of(
                        createOrderResponse(3, LocalDate.parse("2024-01-05"), List.of(createProductResponse(3, BigDecimal.valueOf(150))), BigDecimal.valueOf(150))
                ))
        );
    }

    private static UserOrdersResponse createUserOrdersResponse(final Integer userId, final String name, final List<OrderResponse> orders) {
        return UserOrdersResponse.builder()
                .userId(userId)
                .name(name)
                .orders(orders)
                .build();
    }
}
