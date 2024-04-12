package com.luizalabs.logistica.service;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.domain.UserOrders;
import com.luizalabs.logistica.usecases.FindOrders;
import com.luizalabs.logistica.usecases.MapUserOrders;
import com.luizalabs.logistica.usecases.OrganizeUserOrders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserOrdersService {

    private final FindOrders findOrders;
    private final MapUserOrders mapUserOrders;
    private final OrganizeUserOrders organizeUserOrders;

    public List<UserOrders> findOrders(final Integer orderId, final LocalDate startDate, final LocalDate endDate) {
        log.info("Starting find orders.");
        final List<Orders> orders = this.findOrders.execute(orderId, startDate, endDate);

        log.info("Orders found successfully, starting to mapping.");
        final Map<Integer, Map<Integer, List<Orders>>> mappedOrders = this.mapUserOrders.execute(orders);

        log.info("Orders mapped successfully, starting to organize by user.");
        final List<UserOrders> userOrders = this.organizeUserOrders.execute(orders, mappedOrders);

        log.info("Orders organized successfully.");
        return userOrders;
    }
}
