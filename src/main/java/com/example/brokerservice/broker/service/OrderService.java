package com.example.brokerservice.broker.service;

import com.example.brokerservice.broker.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<Order> getOrders(Long customerId, LocalDate startDate, LocalDate endDate);
    Order createOrder(Order order);
    void matchOrder(Long orderId);
    void deleteOrder(Long orderId, Long userId);
}
