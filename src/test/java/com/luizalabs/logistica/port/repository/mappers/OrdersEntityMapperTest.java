package com.luizalabs.logistica.port.repository.mappers;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.port.repository.entity.OrdersEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.luizalabs.logistica.fixtures.domain.OrdersEntityFixture.createOrdersEntity;
import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrdersEntityMapperTest {

    @Autowired
    private OrdersEntityMapperImpl ordersEntityMapper;

    @Test
    void toEntity() {
        final Orders orders = createOrders(1, "Gustavo Felipe", 2, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10"));

        final OrdersEntity ordersEntity = this.ordersEntityMapper.toEntity(orders);

        assertNotNull(ordersEntity);
        assertEquals(orders.getUserId(), ordersEntity.getUserId());
        assertEquals(orders.getName(), ordersEntity.getName());
        assertEquals(orders.getOrderId(), ordersEntity.getOrderId());
        assertEquals(orders.getProdId(), ordersEntity.getProdId());
        assertEquals(orders.getValue(), ordersEntity.getValue());
        assertEquals(orders.getDate(), ordersEntity.getDate());
    }

    @Test
    void toDomain() {
        final OrdersEntity ordersEntity = createOrdersEntity(1, "Gustavo Felipe", 2, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10"));

        final Orders orders = this.ordersEntityMapper.toDomain(ordersEntity);

        assertNotNull(orders);
        assertEquals(ordersEntity.getUserId(), orders.getUserId());
        assertEquals(ordersEntity.getName(), orders.getName());
        assertEquals(ordersEntity.getOrderId(), orders.getOrderId());
        assertEquals(ordersEntity.getProdId(), orders.getProdId());
        assertEquals(ordersEntity.getValue(), orders.getValue());
        assertEquals(ordersEntity.getDate(), orders.getDate());
    }

    @Test
    void toDomainList() {
        final List<OrdersEntity> ordersEntityList = List.of(createOrdersEntity(1, "Gustavo Felipe", 2, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10")));

        final List<Orders> ordersList = this.ordersEntityMapper.toDomainList(ordersEntityList);

        assertNotNull(ordersEntityList);
        assertEquals(ordersEntityList.size(), ordersList.size());

        final OrdersEntity ordersEntity = ordersEntityList.get(0);
        final Orders orders = ordersList.get(0);

        assertEquals(ordersEntity.getUserId(), orders.getUserId());
        assertEquals(ordersEntity.getName(), orders.getName());
        assertEquals(ordersEntity.getOrderId(), orders.getOrderId());
        assertEquals(ordersEntity.getProdId(), orders.getProdId());
        assertEquals(ordersEntity.getValue(), orders.getValue());
        assertEquals(ordersEntity.getDate(), orders.getDate());
    }
}
