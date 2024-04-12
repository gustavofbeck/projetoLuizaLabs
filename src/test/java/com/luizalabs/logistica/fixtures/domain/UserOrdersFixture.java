package com.luizalabs.logistica.fixtures.domain;

import com.luizalabs.logistica.domain.Order;
import com.luizalabs.logistica.domain.UserOrders;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.luizalabs.logistica.fixtures.domain.OrderFixture.createOrder;
import static com.luizalabs.logistica.fixtures.domain.ProductFixture.createProduct;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOrdersFixture {

    public static List<UserOrders> createUserOrdersList() {
        return List.of(
                createUserOrders(1, "Gustavo Felipe", List.of(
                        createOrder(1, LocalDate.parse("2024-01-01"), List.of(createProduct(1, BigDecimal.valueOf(100))), BigDecimal.valueOf(100)),
                        createOrder(2, LocalDate.parse("2024-01-10"), List.of(createProduct(2, BigDecimal.valueOf(100))), BigDecimal.valueOf(100))
                )),
                createUserOrders(2, "Felipe Beck", List.of(
                        createOrder(3, LocalDate.parse("2024-01-05"), List.of(createProduct(3, BigDecimal.valueOf(150))), BigDecimal.valueOf(150))
                ))
        );
    }

    private static UserOrders createUserOrders(final Integer userId, final String name, final List<Order> orders) {
        return UserOrders.builder()
                .userId(userId)
                .name(name)
                .orders(orders)
                .build();
    }
}
