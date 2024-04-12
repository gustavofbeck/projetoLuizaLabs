package com.luizalabs.logistica.port;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.exception.DataBaseErrorException;
import com.luizalabs.logistica.port.repository.OrdersRepository;
import com.luizalabs.logistica.port.repository.entity.OrdersEntity;
import com.luizalabs.logistica.port.repository.mappers.OrdersEntityMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.luizalabs.logistica.fixtures.domain.OrdersEntityFixture.createOrdersEntity;
import static com.luizalabs.logistica.fixtures.domain.OrdersEntityFixture.createOrdersEntityList;
import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrders;
import static com.luizalabs.logistica.fixtures.domain.OrdersFixture.createOrdersList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrdersGatewayImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrdersEntityMapper ordersEntityMapper;

    @InjectMocks
    private OrdersGatewayImpl ordersGateway;


    @Test
    void shouldSaveAllOrders() {
        final Orders orders = createOrders(2, "Felipe Beck", 3, 3, BigDecimal.valueOf(150), LocalDate.parse("2024-01-05"));
        final OrdersEntity ordersEntity = createOrdersEntity(2, "Felipe Beck", 3, 3, BigDecimal.valueOf(150), LocalDate.parse("2024-01-05"));

        when(this.ordersEntityMapper.toEntity(any())).thenReturn(ordersEntity);

        this.ordersGateway.saveAll(List.of(orders));

        verify(this.ordersRepository).save(any());
    }

    @Test
    void shouldNotSaveWhenAnOrderAlreadyExists() {
        final Orders orders = createOrders(2, "Felipe Beck", 3, 3, BigDecimal.valueOf(150), LocalDate.parse("2024-01-05"));

        when(this.ordersRepository.existsByUserIdAndNameAndOrderIdAndProdIdAndValueAndDate(any(), any(), any(), any(), any(), any())).thenReturn(true);

        this.ordersGateway.saveAll(List.of(orders));

        verify(this.ordersRepository, times(0)).save(any());
        verifyNoInteractions(this.ordersEntityMapper);
    }

    @Test
    void shouldFindAllOrders() {
        final List<Orders> orders = createOrdersList();
        final List<OrdersEntity> ordersEntities = createOrdersEntityList();

        when(this.ordersRepository.findAll()).thenReturn(ordersEntities);
        when(this.ordersEntityMapper.toDomainList(any())).thenReturn(orders);

        final List<Orders> ordersList = this.ordersGateway.findAll();

        assertEquals(ordersEntities.size(), ordersList.size());
    }

    @Test
    void shouldFindByOrderId() {
        final Integer orderId = 1;
        final OrdersEntity ordersEntity = createOrdersEntity(1, "Gustavo Felipe", 1, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10"));

        when(this.ordersRepository.findByOrderId(orderId)).thenReturn(ordersEntity);
        when(this.ordersEntityMapper.toDomain(any())).thenReturn(createOrders(1, "Gustavo Felipe", 1, 2, BigDecimal.valueOf(200), LocalDate.parse("2024-01-10")));

        final Orders orders = this.ordersGateway.findByOrderId(orderId);

        assertNotNull(orders);
        assertEquals(ordersEntity.getUserId(), orders.getUserId());
        assertEquals(ordersEntity.getDate(), orders.getDate());
        assertEquals(ordersEntity.getOrderId(), orders.getOrderId());
        assertEquals(ordersEntity.getProdId(), orders.getProdId());
        assertEquals(ordersEntity.getValue(), orders.getValue());
        assertEquals(ordersEntity.getDate(), orders.getDate());
    }

    @Test
    void shouldFindByStartDateAndEndDate() {
        final LocalDate startDate = LocalDate.parse("2024-01-01");
        final LocalDate endDate = LocalDate.parse("2024-01-10");
        final List<OrdersEntity> ordersEntities = createOrdersEntityList();

        when(this.ordersRepository.findByDateBetween(startDate, endDate)).thenReturn(ordersEntities);
        when(this.ordersEntityMapper.toDomainList(any())).thenReturn(createOrdersList());

        final List<Orders> ordersList = this.ordersGateway.findByStartDateAndEndDate(startDate, endDate);

        assertNotNull(ordersList);
        assertEquals(3, ordersList.size());

        for (int i = 0; i < 3; i++) {
            assertEquals(ordersEntities.get(i).getUserId(), ordersList.get(i).getUserId());
            assertEquals(ordersEntities.get(i).getDate(), ordersList.get(i).getDate());
            assertEquals(ordersEntities.get(i).getOrderId(), ordersList.get(i).getOrderId());
            assertEquals(ordersEntities.get(i).getProdId(), ordersList.get(i).getProdId());
            assertEquals(ordersEntities.get(i).getValue(), ordersList.get(i).getValue());
            assertEquals(ordersEntities.get(i).getDate(), ordersList.get(i).getDate());
        }

    }

    @Test
    void shouldThrowDataBaseErrorExceptionWhenSaveAllFails() {
        final List<Orders> orders = createOrdersList();

        doThrow(new RuntimeException()).when(this.ordersRepository).save(any());

        assertThrows(DataBaseErrorException.class, () -> this.ordersGateway.saveAll(orders));
    }

    @Test
    void shouldThrowDataBaseErrorExceptionWhenFindAllFails() {
        doThrow(new RuntimeException()).when(this.ordersRepository).findAll();

        assertThrows(DataBaseErrorException.class, () -> this.ordersGateway.findAll());
    }

    @Test
    void shouldThrowDataBaseErrorExceptionWhenFindByOrderIdFails() {
        final Integer orderId = 1;
        when(this.ordersRepository.findByOrderId(orderId)).thenThrow(new RuntimeException());

        assertThrows(DataBaseErrorException.class, () -> this.ordersGateway.findByOrderId(orderId));
    }

    @Test
    void shouldThrowDataBaseErrorExceptionWhenFindByStartDateAndEndDateFails() {
        final LocalDate startDate = LocalDate.now().minusDays(7);
        final LocalDate endDate = LocalDate.now();
        when(this.ordersRepository.findByDateBetween(startDate, endDate)).thenThrow(new RuntimeException());

        assertThrows(DataBaseErrorException.class, () -> this.ordersGateway.findByStartDateAndEndDate(startDate, endDate));
    }
}
