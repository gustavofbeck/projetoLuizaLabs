package com.luizalabs.logistica.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Schema(description = "An order")
public class OrderResponse {

    private final Integer orderId;
    private final BigDecimal total;
    private final LocalDate date;
    private final List<ProductResponse> products;
}
