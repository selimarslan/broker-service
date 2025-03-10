package com.example.brokerservice.broker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "assets")
@Entity
@Data
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long customerId;

    @Column
    private String assetName;

    @Column
    private Integer size;

    @Column
    private Integer usableSize;
}
