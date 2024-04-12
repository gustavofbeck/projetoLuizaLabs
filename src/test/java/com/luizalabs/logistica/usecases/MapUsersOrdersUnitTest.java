package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.domain.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrdersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MapUsersOrdersUnitTest {

    @Autowired
    private MapUserOrders mapUserOrders;

    @Test
    void shouldFilterUserOrdersWithDefaultFilter() {
        final List<Orders> orders = createOrdersList();

        final Map<Integer, Map<Integer, List<Orders>>> ordersMap = this.mapUserOrders.execute(orders);

        assertEquals(2, ordersMap.size());

        final Map<Integer, List<Orders>> userOneOrders = ordersMap.get(1);
        assertNotNull(userOneOrders);
        assertEquals(2, userOneOrders.size());

        userOneOrders.values().forEach(ordersList -> ordersList.forEach(order -> {
            assertEquals(1, order.getUserId());
            assertEquals("Gustavo Felipe", order.getName());
            assertNotNull(order.getOrderId());
            assertNotNull(order.getProdId());
            assertNotNull(order.getValue());
            assertNotNull(order.getDate());
        }));

        final Map<Integer, List<Orders>> userTwoOrders = ordersMap.get(2);
        assertNotNull(userTwoOrders);
        assertEquals(1, userTwoOrders.size());
        userTwoOrders.values().forEach(ordersList -> ordersList.forEach(order -> {
            assertEquals(2, order.getUserId());
            assertEquals("Felipe Beck", order.getName());
            assertNotNull(order.getOrderId());
            assertNotNull(order.getProdId());
            assertNotNull(order.getValue());
            assertNotNull(order.getDate());
        }));
    }

}
