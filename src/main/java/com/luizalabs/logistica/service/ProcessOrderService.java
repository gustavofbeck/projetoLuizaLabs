package com.luizalabs.logistica.service;

import com.luizalabs.logistica.usecases.ProcessOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessOrderService {

    private final ProcessOrder processOrder;

    public void processOrderToJson(final String order) {
        log.info("Starting process order to JSON.");
        this.processOrder.execute(order);
        log.info("Orders processed successfully.");
    }
}
