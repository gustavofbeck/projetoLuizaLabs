package com.luizalabs.logistica.service;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.domain.UserOrders;
import com.luizalabs.logistica.usecases.FindOrders;
import com.luizalabs.logistica.usecases.MapUserOrders;
import com.luizalabs.logistica.usecases.OrganizeUserOrders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createFilteredOrdersMap;
import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrdersList;
import static com.luizalabs.logistica.fixtures.domain.UserOrdersFixture.createUserOrdersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserOrdersServiceUnitTest {

    @Mock
    private FindOrders findOrders;

    @Mock
    private MapUserOrders mapUserOrders;

    @Mock
    private OrganizeUserOrders organizeUserOrders;

    @InjectMocks
    private FindUserOrdersService findUserOrdersService;

    @Test
    void shouldProcessAnUserOrdersToJson() {
        final List<Orders> orders = createOrdersList();
        final Map<Integer, Map<Integer, List<Orders>>> ordersMap = createFilteredOrdersMap();
        final List<UserOrders> userOrders = createUserOrdersList();

        when(this.findOrders.execute(any(), any(), any())).thenReturn(orders);
        when(this.mapUserOrders.execute(any())).thenReturn(ordersMap);
        when(this.organizeUserOrders.execute(any(), any())).thenReturn(userOrders);

        final List<UserOrders> userOrdersResponse = this.findUserOrdersService.findOrders(null, null, null);

        assertNotNull(userOrdersResponse);
        assertEquals(userOrders, userOrdersResponse);
    }
}
