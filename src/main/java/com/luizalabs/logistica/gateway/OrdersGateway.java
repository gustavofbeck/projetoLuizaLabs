package com.luizalabs.logistica.gateway;

import com.luizalabs.logistica.domain.Orders;

import java.time.LocalDate;
import java.util.List;

public interface OrdersGateway {

    void saveAll(final List<Orders> orders);

    List<Orders> findAll();

    Orders findByOrderId(final Integer orderId);

    List<Orders> findByStartDateAndEndDate(final LocalDate startDate, final LocalDate endDate);
}
