package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.gateway.OrdersGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrders;
import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrdersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindOrdersUnitTest {

    @Mock
    private OrdersGateway ordersGateway;

    @InjectMocks
    private FindOrders findOrders;

    @Test
    void shouldReturnOrdersByOrderId() {
        final Integer orderId = 1;
        final Orders expectedOrder = createOrders(1, "Gustavo Felipe", 1, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10"));

        when(this.ordersGateway.findByOrderId(orderId)).thenReturn(expectedOrder);

        final List<Orders> orders = this.findOrders.execute(orderId, null, null);

        verify(this.ordersGateway).findByOrderId(orderId);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(expectedOrder, orders.get(0));
    }

    @Test
    void shouldReturnOrdersByStartDateAndEndDate() {
        final LocalDate startDate = LocalDate.parse("2024-01-01");
        final LocalDate endDate = LocalDate.parse("2024-01-10");

        final List<Orders> expectedOrders = createOrdersList();

        when(this.ordersGateway.findByStartDateAndEndDate(startDate, endDate)).thenReturn(expectedOrders);

        final List<Orders> orders = this.findOrders.execute(null, startDate, endDate);

        verify(this.ordersGateway).findByStartDateAndEndDate(startDate, endDate);
        assertNotNull(orders);
        assertEquals(3, orders.size());
        assertEquals(expectedOrders, orders);
    }

    @Test
    void shouldReturnAllOrders() {
        final List<Orders> expectedOrders = createOrdersList();

        when(this.ordersGateway.findAll()).thenReturn(expectedOrders);

        final List<Orders> orders = this.findOrders.execute(null, null, null);

        verify(this.ordersGateway).findAll();
        assertNotNull(orders);
        assertEquals(3, orders.size());
        assertEquals(expectedOrders, orders);
    }
}