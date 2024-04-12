package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.exception.InvalidOrdersFieldsException;
import com.luizalabs.logistica.gateway.OrdersGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessOrderUnitTest {

    @Mock
    private OrdersGateway ordersGateway;

    @InjectMocks
    private ProcessOrder processOrder;

    @Test
    void shouldProcessAStringToAnOrderList() {
        final String orderString = """
                0000000001                               Gustavo Felipe00000000010000000001      100.0020240101
                0000000001                               Gustavo Felipe00000000020000000002      100.0020240101
                0000000002                                  Felipe Beck00000000030000000003      150.0020240101""";

        this.processOrder.execute(orderString);

        verify(this.ordersGateway, times(1)).saveAll(anyList());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalido",
            "invalido01                               Gustavo Felipe00000000010000000001      100.0020240101",
            "0000000001                               Gustavo Felipeinvalido010000000001      100.0020240101",
            "0000000001                               Gustavo Felipe0000000001invalido01      100.0020240101",
            "0000000001                               Gustavo Felipe00000000010000000001    invalido20240101",
            "0000000001                               Gustavo Felipe00000000010000000001      100.00invalido"})
    void shouldThrownAnInvalidOrdersExceptionWhenTheOrdersStringIsInvalid(final String orderString) {
        assertThrows(InvalidOrdersFieldsException.class,
                () -> this.processOrder.execute(orderString));

        verifyNoInteractions(this.ordersGateway);
    }
}
