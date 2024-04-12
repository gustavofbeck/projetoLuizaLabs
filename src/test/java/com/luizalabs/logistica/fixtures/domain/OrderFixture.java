package com.luizalabs.logistica.fixtures.domain;

import com.luizalabs.logistica.domain.Order;
import com.luizalabs.logistica.domain.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderFixture {

    public static Order createOrder(final Integer orderId, final LocalDate date, final List<Product> products, final BigDecimal total) {
        return Order.builder()
                .orderId(orderId)
                .date(date)
                .products(products)
                .total(total)
                .build();
    }
}
