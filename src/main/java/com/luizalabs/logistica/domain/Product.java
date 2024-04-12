package com.luizalabs.logistica.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {

    private Integer productId;
    private BigDecimal value;
}
