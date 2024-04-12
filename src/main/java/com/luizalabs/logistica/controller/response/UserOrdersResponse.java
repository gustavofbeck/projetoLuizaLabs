package com.luizalabs.logistica.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Processed user orders")
public class UserOrdersResponse {
    private final Integer userId;
    private final String name;
    private final List<OrderResponse> orders;
}
