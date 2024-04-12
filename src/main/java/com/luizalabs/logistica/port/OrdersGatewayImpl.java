package com.luizalabs.logistica.port;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.exception.DataBaseErrorException;
import com.luizalabs.logistica.gateway.OrdersGateway;
import com.luizalabs.logistica.port.repository.OrdersRepository;
import com.luizalabs.logistica.port.repository.mappers.OrdersEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrdersGatewayImpl implements OrdersGateway {

    private final OrdersRepository ordersRepository;
    private final OrdersEntityMapper ordersEntityMapper;

    @Override
    public void saveAll(final List<Orders> orders) {
        try {
            log.info("Saving orders in database");
            orders.forEach(order -> {
                final boolean exists = this.ordersRepository.existsByUserIdAndNameAndOrderIdAndProdIdAndValueAndDate(order.getUserId(),
                        order.getName(), order.getOrderId(), order.getProdId(), order.getValue(), order.getDate());

                if (!exists) {
                    this.ordersRepository.save(this.ordersEntityMapper.toEntity(order));
                } else {
                    log.error("Ignoring existing order: {}", order);
                }
            });
            log.info("Orders saved successfully");
        } catch (final Exception e) {
            log.error("Error savings the orders in the database");
            throw new DataBaseErrorException("Database error: " + e.getMessage());
        }
    }

    @Override
    public List<Orders> findAll() {
        try {
            log.info("Finding all orders in database");
            return this.ordersEntityMapper.toDomainList(this.ordersRepository.findAll());
        } catch (final Exception e) {
            log.error("Error finding orders in database.");
            throw new DataBaseErrorException("Database error: " + e.getMessage());
        }
    }

    @Override
    public Orders findByOrderId(final Integer orderId) {
        try {
            log.info("Finding order by orderId in database");
            return this.ordersEntityMapper.toDomain(this.ordersRepository.findByOrderId(orderId));
        } catch (final Exception e) {
            log.error("Error finding order by order id in database.");
            throw new DataBaseErrorException("Database error: " + e.getMessage());
        }
    }

    @Override
    public List<Orders> findByStartDateAndEndDate(final LocalDate startDate, final LocalDate endDate) {
        try {
            log.info("Finding all orders between startDate and endDate in database");
            return this.ordersEntityMapper.toDomainList(this.ordersRepository.findByDateBetween(startDate, endDate));
        } catch (final Exception e) {
            log.error("Error finding orders between startDate and endDate in database.");
            throw new DataBaseErrorException("Database error: " + e.getMessage());
        }
    }


}
