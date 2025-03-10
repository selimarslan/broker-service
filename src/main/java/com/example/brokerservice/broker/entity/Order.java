package com.example.brokerservice.broker.entity;

import com.example.brokerservice.broker.enums.OrderSide;
import com.example.brokerservice.broker.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "orders")
@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private Long customerId;

    @Column
    private String assetName;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;

    @Column
    private Integer size;

    @Column
    private Integer price;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    private LocalDateTime createdDate;
}
