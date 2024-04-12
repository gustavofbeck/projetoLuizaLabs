package com.luizalabs.logistica.mappers;

import com.luizalabs.logistica.controller.response.UserOrdersResponse;
import com.luizalabs.logistica.domain.UserOrders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.luizalabs.logistica.fixtures.controller.UserOrdersResponseFixture.createUserOrdersResponseList;
import static com.luizalabs.logistica.fixtures.domain.UserOrdersFixture.createUserOrdersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserOrdersMapperUnitTest {

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Test
    void shouldConvertDomainToResponse() {
        final List<UserOrders> userOrders = createUserOrdersList();
        final List<UserOrdersResponse> userOrdersResponse = this.userOrdersMapper.toResponseList(userOrders);

        assertNotNull(userOrdersResponse);
        assertEquals(createUserOrdersResponseList(), userOrdersResponse);
    }
}
