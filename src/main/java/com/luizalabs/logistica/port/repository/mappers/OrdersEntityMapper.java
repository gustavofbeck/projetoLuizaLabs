package com.luizalabs.logistica.port.repository.mappers;

import com.luizalabs.logistica.domain.Orders;
import com.luizalabs.logistica.port.repository.entity.OrdersEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersEntityMapper {

    OrdersEntity toEntity(final Orders orders);

    Orders toDomain(final OrdersEntity orders);

    List<Orders> toDomainList(final List<OrdersEntity> orders);
}
