package com.luizalabs.logistica.fixtures.domain;

import com.luizalabs.logistica.domain.Orders;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersFixture {

    public static List<Orders> createOrdersList() {
        final List<Orders> ordersList = new ArrayList<>();
        ordersList.add(createOrders(2, "Felipe Beck", 3, 3, BigDecimal.valueOf(150), LocalDate.parse("2024-01-05")));
        ordersList.add(createOrders(1, "Gustavo Felipe", 1, 1, BigDecimal.valueOf(100), LocalDate.parse("2024-01-01")));
        ordersList.add(createOrders(1, "Gustavo Felipe", 2, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10")));

        return ordersList;
    }

    public static Map<Integer, Map<Integer, List<Orders>>> createFilteredOrdersMap() {
        final Map<Integer, Map<Integer, List<Orders>>> filteredOrders = new TreeMap<>();

        final Map<Integer, List<Orders>> userOneOrders = new HashMap<>();
        userOneOrders.put(1, createOrdersList(1, "Gustavo Felipe", 1, 1, BigDecimal.valueOf(100), LocalDate.parse("2024-01-01")));
        userOneOrders.put(2, createOrdersList(1, "Gustavo Felipe", 2, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10")));
        filteredOrders.put(1, userOneOrders);

        final Map<Integer, List<Orders>> userTwoOrders = new HashMap<>();
        userTwoOrders.put(3, createOrdersList(2, "Felipe Beck", 3, 3, BigDecimal.valueOf(150), LocalDate.parse("2024-01-05")));
        filteredOrders.put(2, userTwoOrders);

        return filteredOrders;
    }

    private static List<Orders> createOrdersList(final Integer userId, final String name, final Integer orderId,
                                                 final Integer prodId, final BigDecimal value, final LocalDate date) {
        final List<Orders> ordersList = new ArrayList<>();
        ordersList.add(createOrders(userId, name, orderId, prodId, value, date));
        return ordersList;
    }

    public static Orders createOrders(final Integer userId, final String name, final Integer orderId, final Integer prodId,
                                      final BigDecimal value, final LocalDate date) {
        return Orders.builder()
                .userId(userId)
                .name(name)
                .orderId(orderId)
                .prodId(prodId)
                .value(value)
                .date(date)
                .build();
    }
}
