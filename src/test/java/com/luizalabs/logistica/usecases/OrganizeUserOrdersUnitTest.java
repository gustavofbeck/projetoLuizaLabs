package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.domain.Order;
import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.domain.UserOrders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createFilteredOrdersMap;
import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrdersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrganizeUserOrdersUnitTest {

    @Autowired
    private OrganizeUserOrders organizeUserOrders;

    @Test
    void shouldOrganizeAnUserOrders() {
        final Map<Integer, Map<Integer, List<Orders>>> userOrdersMap = createFilteredOrdersMap();

        final List<UserOrders> userOrders = this.organizeUserOrders.execute(createOrdersList(), userOrdersMap);

        assertNotNull(userOrders);
        assertEquals(2, userOrders.size());

        final UserOrders userOneOrders = userOrders.get(0);
        assertEquals(1, userOneOrders.getUserId());
        assertEquals("Gustavo Felipe", userOneOrders.getName());
        assertEquals(2, userOneOrders.getOrders().size());

        final Order userOneOrderOne = userOneOrders.getOrders().get(0);
        assertEquals(1, userOneOrderOne.getOrderId());
        assertEquals(LocalDate.of(2024, 1, 1), userOneOrderOne.getDate());
        assertEquals(BigDecimal.valueOf(100), userOneOrderOne.getTotal());
        assertEquals(1, userOneOrderOne.getProducts().size());

        final Order userOneOrderTwo = userOneOrders.getOrders().get(1);
        assertEquals(2, userOneOrderTwo.getOrderId());
        assertEquals(LocalDate.of(2024, 1, 10), userOneOrderTwo.getDate());
        assertEquals(BigDecimal.valueOf(200), userOneOrderTwo.getTotal());
        assertEquals(1, userOneOrderTwo.getProducts().size());

        final UserOrders userTwoOrders = userOrders.get(1);
        assertEquals(2, userTwoOrders.getUserId());
        assertEquals("Felipe Beck", userTwoOrders.getName());
        assertEquals(1, userTwoOrders.getOrders().size());

        final Order userTwoOrderOne = userTwoOrders.getOrders().get(0);
        assertEquals(3, userTwoOrderOne.getOrderId());
        assertEquals(LocalDate.of(2024, 1, 5), userTwoOrderOne.getDate());
        assertEquals(BigDecimal.valueOf(150), userTwoOrderOne.getTotal());
        assertEquals(1, userTwoOrderOne.getProducts().size());
    }

}
