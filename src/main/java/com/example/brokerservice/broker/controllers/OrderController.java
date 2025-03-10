package com.example.brokerservice.broker.controllers;

import com.example.brokerservice.auth.service.UserService;
import com.example.brokerservice.broker.dto.OrderDto;
import com.example.brokerservice.broker.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public List<OrderDto> getOrders(@RequestParam("startDate") LocalDate startDate,
                                    @RequestParam("endDate") LocalDate endDate,
                                    Principal principal) {
        var user = userService.getByUserName(principal.getName());
        return orderService
                .getOrders(user.getId(), startDate, endDate)
                .stream()
                .map(OrderDto::new)
                .toList();
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrderDto> getOrders(@PathVariable("customerId") Long customerId,
                                    @RequestParam("startDate") LocalDate startDate,
                                    @RequestParam("endDate") LocalDate endDate) {
        return orderService
                .getOrders(customerId, startDate, endDate)
                .stream()
                .map(OrderDto::new)
                .toList();
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDto, Principal principal) {
        var user = userService.getByUserName(principal.getName());
        orderDto.setCustomerId(user.getId());
        var order = orderService.createOrder(orderDto.toOrder());
        return new OrderDto(order);
    }

    @PostMapping("/customer/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrderDto createOrder(@RequestBody OrderDto orderDto, @PathVariable("customerId") Long customerId) {
        orderDto.setCustomerId(customerId);
        var order = orderService.createOrder(orderDto.toOrder());
        return new OrderDto(order);
    }

    @PostMapping("/{orderId}/match")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void matchOrder(@PathVariable("orderId") Long orderId) {
        orderService.matchOrder(orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        var user = userService.getByUserName(principal.getName());
        orderService.deleteOrder(orderId, user.getId());
    }

    @DeleteMapping("/{orderId}/customer/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteOrder(@PathVariable("orderId") Long orderId, @PathVariable("customerId") Long customerId) {
        orderService.deleteOrder(orderId, customerId);
    }
}
