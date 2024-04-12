package com.luizalabs.logistica.port.repository.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Document(collection = "orders")
public class OrdersEntity {

    @Id
    private ObjectId id;
    private Integer userId;
    private String name;
    private Integer orderId;
    private Integer prodId;
    private BigDecimal value;
    private LocalDate date;
}
