package com.luizalabs.logistica.fixtures.domain;

import com.luizalabs.logistica.port.repository.entity.OrdersEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersEntityFixture {

    public static List<OrdersEntity> createOrdersEntityList() {
        final List<OrdersEntity> ordersEntities = new ArrayList<>();
        ordersEntities.add(createOrdersEntity(2, "Felipe Beck", 3, 3, BigDecimal.valueOf(150), LocalDate.parse("2024-01-05")));
        ordersEntities.add(createOrdersEntity(1, "Gustavo Felipe", 1, 1, BigDecimal.valueOf(100), LocalDate.parse("2024-01-01")));
        ordersEntities.add(createOrdersEntity(1, "Gustavo Felipe", 2, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10")));

        return ordersEntities;
    }

    public static OrdersEntity createOrdersEntity(final Integer userId, final String name, final Integer orderId, final Integer prodId,
                                                  final BigDecimal value, final LocalDate date) {
        return OrdersEntity.builder()
                .userId(userId)
                .name(name)
                .orderId(orderId)
                .prodId(prodId)
                .value(value)
                .date(date)
                .build();
    }
}
