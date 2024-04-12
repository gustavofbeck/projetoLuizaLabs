package com.luizalabs.logistica.service;

import com.luizalabs.logistica.usecases.ProcessOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProcessOrderServiceUnitTest {

    @Mock
    private ProcessOrder processOrder;

    @InjectMocks
    private ProcessOrderService processOrderService;

    @Test
    void shouldProcessAnUserOrdersToJson() {
        final String orderString = """
                0000000001                               Gustavo Felipe00000000010000000001      100.0020240101
                0000000001                               Gustavo Felipe00000000020000000002      100.0020240105
                0000000002                                  Felipe Beck00000000030000000003      150.0020240110""";

        this.processOrderService.processOrderToJson(orderString);

        verify(this.processOrder, times(1)).execute(any());
    }
}
