package com.example.brokerservice.broker.dto;

import com.example.brokerservice.broker.entity.Order;
import com.example.brokerservice.broker.enums.OrderSide;
import com.example.brokerservice.broker.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private Long customerId;

    private String assetName;

    private OrderSide orderSide;

    private Integer size;

    private Integer price;

    private OrderStatus status;

    private LocalDateTime createdDate;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomerId();
        this.assetName = order.getAssetName();
        this.orderSide = order.getOrderSide();
        this.size = order.getSize();
        this.price = order.getPrice();
        this.status = order.getOrderStatus();
        this.createdDate = order.getCreatedDate();
    }

    public Order toOrder() {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setAssetName(assetName);
        order.setOrderSide(orderSide);
        order.setSize(size);
        order.setPrice(price);
        order.setOrderStatus(status);
        order.setCreatedDate(createdDate);
        return order;
    }
}
