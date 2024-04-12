package com.luizalabs.logistica.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Orders {

    private Integer userId;
    private String name;
    private Integer orderId;
    private Integer prodId;
    private BigDecimal value;
    private LocalDate date;
}