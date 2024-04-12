package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.exception.InvalidOrdersFieldsException;
import com.luizalabs.logistica.gateway.OrdersGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessOrder {

    private final OrdersGateway ordersGateway;

    public void execute(final String orderFile) {
        final List<String> lines = this.splitLines(orderFile);
        final List<Orders> orders = new ArrayList<>();

        log.info("Processing orders.");
        for (final String line : lines) {
            if (line.length() >= 95) {
                final Orders order = this.buildOrderFromLine(line);
                orders.add(order);
            } else {
                log.error("Error reading the orders file.");
                throw new InvalidOrdersFieldsException("Unable to read the string. The text file of the orders has " +
                        "incorrectly lines.");
            }
        }

        log.info("Orders processed. Sending to save in database.");
        this.ordersGateway.saveAll(orders);
    }

    private List<String> splitLines(final String orderFile) {
        final String[] linesArray = orderFile.split("\\r?\\n");
        final List<String> lines = new ArrayList<>();
        for (final String line : linesArray) {
            lines.add(line.trim());
        }
        return lines;
    }

    private Orders buildOrderFromLine(final String orderLine) {

        try {
            return Orders.builder()
                    .userId(Integer.parseInt(orderLine.substring(0, 10).trim()))
                    .name(orderLine.substring(10, 55).trim())
                    .orderId(Integer.parseInt(orderLine.substring(55, 65).trim()))
                    .prodId(Integer.parseInt(orderLine.substring(65, 75).trim()))
                    .value(new BigDecimal(orderLine.substring(75, 87).trim()))
                    .date(LocalDate.parse(orderLine.substring(87).trim(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                    .build();
        } catch (final Exception e) {
            log.error("Error reading the orders fields.");
            throw new InvalidOrdersFieldsException("Unable to read the string. The text file of the orders has " +
                    "incorrectly formatted fields. Exception message: [" + e.getMessage() + "]");
        }
    }
}