package com.luizalabs.logistica.controller;

import com.luizalabs.logistica.controller.response.UserOrdersResponse;
import com.luizalabs.logistica.domain.UserOrders;

import com.luizalabs.logistica.exception.DataBaseErrorException;
import com.luizalabs.logistica.mappers.UserOrdersMapper;
import com.luizalabs.logistica.service.FindUserOrdersService;
import com.luizalabs.logistica.service.ProcessOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static com.luizalabs.logistica.fixtures.controller.UserOrdersResponseFixture.createUserOrdersResponseList;
import static com.luizalabs.logistica.fixtures.domain.UserOrdersFixture.createUserOrdersList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerUnitTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessOrderService processOrderService;

    @MockBean
    private FindUserOrdersService findUserOrdersService;

    @MockBean
    private UserOrdersMapper userOrdersMapper;

    @Test
    void shouldProcessOrderAndReturnUserOrders() throws Exception {
        final String ORDER_REQUEST = """
                0000000001                               Gustavo Felipe00000000010000000001      100.0020240101
                0000000001                               Gustavo Felipe00000000020000000002      100.0020240101
                0000000002                                  Felipe Beck00000000030000000003      150.0020240101""";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/process")
                        .content(ORDER_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.processOrderService, times(1)).processOrderToJson(any());
    }

    @Test
    void shouldHandleDataBaseErrorException() throws Exception {
        final String ORDER_REQUEST = """
                0000000001                               Gustavo Felipe00000000010000000001      100.0020240101
                0000000001                               Gustavo Felipe00000000020000000002      100.0020240101
                0000000002                                  Felipe Beck00000000030000000003      150.0020240101""";


        doThrow(DataBaseErrorException.class).when(processOrderService).processOrderToJson(any());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/process")
                        .content(ORDER_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        verify(this.processOrderService, times(1)).processOrderToJson(any());
    }


    @Test
    void shouldFindUsersOrders() throws Exception {

        final List<UserOrdersResponse> expectedUserOrdersResponse = createUserOrdersResponseList();
        final List<UserOrders> userOrders = createUserOrdersList();

        when(this.findUserOrdersService.findOrders(null, null, null)).thenReturn(userOrders);
        when(this.userOrdersMapper.toResponseList(anyList())).thenReturn(expectedUserOrdersResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedUserOrdersResponse.size())))
                .andReturn();

        verify(this.findUserOrdersService, times(1)).findOrders(any(), any(), any());
    }

    @ParameterizedTest
    @MethodSource("provideStartDateEndDate")
    void shouldHandleInvalidDateRangeException(final String startDate, final String endDate) throws Exception {

        final String errorMessage = "Error in the request, both startDate and endDate must be either filled or null.";

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("startDate", startDate)
                        .param("endDate", endDate))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage))
                .andReturn();
    }

    @Test
    void shouldHandleInvalidDateRangeException() throws Exception {

        final String errorMessage = "Error in the request, it's possible to filter only by orderId or by date range.";

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-01-01")
                        .param("orderId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage))
                .andReturn();
    }

    private static Stream<Arguments> provideStartDateEndDate() {
        return Stream.of(
                Arguments.of("2024-01-01", null),
                Arguments.of(null, "2024-01-01")
        );
    }

}
