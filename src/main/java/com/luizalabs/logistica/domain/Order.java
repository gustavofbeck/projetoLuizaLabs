package com.luizalabs.logistica.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Order {

    private Integer orderId;
    private LocalDate date;
    private List<Product> products;
    private BigDecimal total;
}
