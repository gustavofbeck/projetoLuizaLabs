package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.domain.Order;
import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.domain.Product;
import com.luizalabs.logistica.domain.UserOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrganizeUserOrders {

    public List<UserOrders> execute(final List<Orders> ordersList, final Map<Integer, Map<Integer, List<Orders>>> filteredOrders) {

        final Map<Integer, String> userIdToNameMap = ordersList.stream()
                .collect(Collectors.toMap(Orders::getUserId, Orders::getName, (existing, replacement) -> existing));

        final List<UserOrders> userOrdersList = new ArrayList<>();

        filteredOrders.forEach((userId, ordersByOrderId) -> {
            final List<Order> userOrders = new ArrayList<>();

            ordersByOrderId.forEach((orderId, orders) -> {

                final List<Product> products = orders.stream()
                        .map(order -> Product.builder()
                                .productId(order.getProdId())
                                .value(order.getValue())
                                .build())
                        .sorted(Comparator.comparing(Product::getProductId))
                        .collect(Collectors.toList());

                final BigDecimal total = products.stream()
                        .map(Product::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                userOrders.add(Order.builder()
                        .orderId(orderId)
                        .date(orders.get(0).getDate())
                        .total(total)
                        .products(products)
                        .build());
            });

            userOrders.sort(Comparator.comparing(Order::getDate));

            userOrdersList.add(UserOrders.builder()
                    .userId(userId)
                    .name(userIdToNameMap.get(userId))
                    .orders(userOrders)
                    .build());
        });

        return userOrdersList;
    }

}