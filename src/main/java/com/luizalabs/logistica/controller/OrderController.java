package com.luizalabs.logistica.controller;

import com.luizalabs.logistica.controller.response.UserOrdersResponse;
import com.luizalabs.logistica.domain.UserOrders;
import com.luizalabs.logistica.exception.InvalidDateRangeException;
import com.luizalabs.logistica.exception.InvalidFilterWithOrderIdAndDateException;
import com.luizalabs.logistica.mappers.UserOrdersMapper;
import com.luizalabs.logistica.service.FindUserOrdersService;
import com.luizalabs.logistica.service.ProcessOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Pedidos", description = "Endpoints responsável pelo fluxo de pedidos")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final ProcessOrderService processOrderService;
    private final FindUserOrdersService findUserOrdersService;
    private final UserOrdersMapper userOrdersMapper;

    @GetMapping("/find")
    @Operation(
            summary = "Endpoint responsável por encontrar os pedidos dos usuários",
            description = "Devolve uma lista de pedidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PEDIDOS ENCONTRADOS"),
            @ApiResponse(responseCode = "400", description = "REQUISIÇÃO INVÁLIDA"),
            @ApiResponse(responseCode = "500", description = "ERRO INTERNO NO SERVIDOR DA APLICAÇÃO")
    })
    public ResponseEntity<List<UserOrdersResponse>> findOrders(@Parameter(description = "ID do pedido") @RequestParam(required = false) final Integer orderId,
                                                               @Parameter(description = "Data de início no formato 'yyyy-MM-dd'") @RequestParam(required = false)
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
                                                               @Parameter(description = "Data de término no formato 'yyyy-MM-dd'") @RequestParam(required = false)
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate) {

        this.validateFilterToProcessOrder(orderId, startDate, endDate);

        log.info("Received request to find orders.");

        final List<UserOrders> userOrders = this.findUserOrdersService.findOrders(orderId, startDate, endDate);
        final List<UserOrdersResponse> userOrdersResponseList = this.userOrdersMapper.toResponseList(userOrders);

        log.info("Orders found successfully.");

        return ResponseEntity.ok(userOrdersResponseList);
    }

    @PostMapping("/process")
    @Operation(
            summary = "Endpoint responsável por processar o arquivo de pedidos",
            description = "Recebe um arquivo de pedido e salva os pedidos no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ARQUIVO PROCESSADO COM SUCESSO"),
            @ApiResponse(responseCode = "400", description = "REQUISIÇÃO INVÁLIDA"),
            @ApiResponse(responseCode = "422", description = "ARQUIVO ENVIADO INVÁLIDO"),
            @ApiResponse(responseCode = "500", description = "ERRO INTERNO NO SERVIDOR DA APLICAÇÃO")
    })
    public ResponseEntity<Void> processOrder(@RequestBody final String orderRequest) {

        log.info("Received request to process orders.");

        this.processOrderService.processOrderToJson(orderRequest);

        log.info("Processing orders completed successfully.");

        return ResponseEntity.ok().build();
    }

    private void validateFilterToProcessOrder(final Integer orderId, final LocalDate startDate, final LocalDate endDate) {
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null)) {
            log.error("Bad Request: both starDate and endDate fields must be either filled or null.");
            throw new InvalidDateRangeException("Error in the request, both startDate and endDate must be either filled or null.");
        }
        if (orderId != null && startDate != null) {
            log.error("Bad Request: it's possible to filter only by orderId or by date range.");
            throw new InvalidFilterWithOrderIdAndDateException("Error in the request, it's possible to filter only by orderId or by date range.");
        }
        if (startDate != null & endDate != null && startDate.isAfter(endDate)) {
            log.error("Bad Request: startDate must be before endDate.");
            throw new InvalidDateRangeException("Error in the request, startDate must be before endDate.");
        }
    }

}
