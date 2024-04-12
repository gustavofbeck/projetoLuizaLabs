package com.luizalabs.logistica.port.repository;

import com.luizalabs.logistica.port.repository.entity.OrdersEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrdersRepository extends MongoRepository<OrdersEntity, String> {

    OrdersEntity findByOrderId(final Integer orderId);

    @Query("{ 'date' : { $gte: ?0, $lte: ?1 } }")
    List<OrdersEntity> findByDateBetween(final LocalDate startDate, final LocalDate endDate);

    boolean existsByUserIdAndNameAndOrderIdAndProdIdAndValueAndDate(final Integer userId, final String name, final Integer orderId, final Integer prodId, final BigDecimal value, final LocalDate date);
}