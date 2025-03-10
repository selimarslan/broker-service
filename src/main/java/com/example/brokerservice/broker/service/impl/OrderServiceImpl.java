package com.example.brokerservice.broker.service.impl;

import com.example.brokerservice.broker.entity.Asset;
import com.example.brokerservice.broker.entity.Order;
import com.example.brokerservice.broker.enums.OrderSide;
import com.example.brokerservice.broker.enums.OrderStatus;
import com.example.brokerservice.broker.repository.AssetRepository;
import com.example.brokerservice.broker.repository.OrderRepository;
import com.example.brokerservice.broker.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;

    public static final String TRY_ASSET = "TRY";

    public OrderServiceImpl(OrderRepository orderRepository, AssetRepository assetRepository) {
        this.orderRepository = orderRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public List<Order> getOrders(Long customerId, LocalDate startDate, LocalDate endDate) {
        return orderRepository
                .findByCustomerIdAndCreatedDateBetweenOrderByCreatedDateAsc(customerId,
                        startDate.atStartOfDay(),
                        endDate.atStartOfDay());
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        if(order.getOrderSide() == OrderSide.BUY) {
            var orderTryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), TRY_ASSET);
            var amount = order.getPrice() * order.getSize();
            if (amount > orderTryAsset.getUsableSize()) {
                throw new RuntimeException("Not sufficient funds");
            }
            orderTryAsset.setUsableSize(orderTryAsset.getUsableSize() - amount);
            assetRepository.save(orderTryAsset);
        } else {
            var stockAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
            if (order.getSize() > stockAsset.getUsableSize()) {
                throw new RuntimeException("Not sufficient asset");
            }
            stockAsset.setUsableSize(stockAsset.getUsableSize() - order.getSize());
            assetRepository.save(stockAsset);
        }
        order.setId(0L);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void matchOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(order.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order cannot be matched");
        }

        var orderTryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), TRY_ASSET);
        var amount = order.getPrice() * order.getSize();
        var isBuy = order.getOrderSide() == OrderSide.BUY;

        var direction = order.getOrderSide() == OrderSide.BUY ? 1 : -1;
        orderTryAsset.setSize(orderTryAsset.getSize() - direction * amount);
        if(!isBuy){
            orderTryAsset.setUsableSize(orderTryAsset.getUsableSize() - direction * amount);
        }
        assetRepository.save(orderTryAsset);

        order.setOrderStatus(OrderStatus.MATCHED);
        orderRepository.save(order);

        var stockAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
        if(stockAsset == null) {
            if(!isBuy) {
                throw new RuntimeException("Asset not found");
            }
            stockAsset = new Asset();
            stockAsset.setCustomerId(order.getCustomerId());
            stockAsset.setAssetName(order.getAssetName());
            stockAsset.setSize(0);
            stockAsset.setUsableSize(0);
        }

        stockAsset.setSize(stockAsset.getSize() + direction * order.getSize());
        if(isBuy){
            stockAsset.setUsableSize(stockAsset.getUsableSize() + direction * order.getSize());
        }
        assetRepository.save(stockAsset);

    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId, Long userId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(!Objects.equals(order.getCustomerId(), userId)) {
            throw new RuntimeException("Order customer not matched");
        }

        if(order.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order cannot be deleted");
        }

        if(order.getOrderSide() == OrderSide.BUY) {
            var orderTryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), TRY_ASSET);
            var amount = order.getPrice() * order.getSize();
            orderTryAsset.setUsableSize(orderTryAsset.getUsableSize() + amount);
            assetRepository.save(orderTryAsset);
        } else {
            var stockAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
            stockAsset.setUsableSize(stockAsset.getUsableSize() + order.getSize());
            assetRepository.save(stockAsset);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
