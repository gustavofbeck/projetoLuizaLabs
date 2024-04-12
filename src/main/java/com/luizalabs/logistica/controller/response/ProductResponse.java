package com.luizalabs.logistica.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "A product in an order")
public class ProductResponse {

    private final Integer productId;
    private final BigDecimal value;
}
