package com.luizalabs.logistica.usecases;

import com.luizalabs.logistica.domain.Orders;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class MapUserOrders {

    public Map<Integer, Map<Integer, List<Orders>>> execute(final List<Orders> ordersList) {
        return new TreeMap<>(ordersList.stream()
                .collect(Collectors.groupingBy(Orders::getUserId,
                        Collectors.groupingBy(Orders::getOrderId))));

    }

}