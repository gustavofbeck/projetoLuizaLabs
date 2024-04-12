package com.luizalabs.logistica.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserOrders {

    private Integer userId;
    private String name;
    private List<Order> orders;
}
