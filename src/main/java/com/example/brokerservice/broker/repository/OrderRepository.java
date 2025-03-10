package com.example.brokerservice.broker.repository;

import com.example.brokerservice.broker.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreatedDateBetweenOrderByCreatedDateAsc(
            Long customerId, LocalDateTime startDate, LocalDateTime endDate);
}
