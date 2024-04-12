package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.gateway.OrdersGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindOrders {

    private final OrdersGateway ordersGateway;

    public List<Orders> execute(final Integer orderId, final LocalDate startDate, final LocalDate endDate) {
        if (orderId != null) {
            return List.of(this.ordersGateway.findByOrderId(orderId));
        } else if (startDate != null && endDate != null) {
            return this.ordersGateway.findByStartDateAndEndDate(startDate, endDate);
        } else {
            return this.ordersGateway.findAll();
        }

    }

}