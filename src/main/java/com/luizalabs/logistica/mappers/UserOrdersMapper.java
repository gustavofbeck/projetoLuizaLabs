package com.luizalabs.logistica.mappers;

import com.luizalabs.logistica.controller.response.UserOrdersResponse;
import com.luizalabs.logistica.domain.UserOrders;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserOrdersMapper {

    UserOrdersResponse toResponse(final UserOrders userOrders);

    List<UserOrdersResponse> toResponseList(final List<UserOrders> userOrders);
}
