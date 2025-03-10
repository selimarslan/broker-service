package com.example.brokerservice.broker.service.impl;

import com.example.brokerservice.broker.entity.Asset;
import com.example.brokerservice.broker.entity.Order;
import com.example.brokerservice.broker.enums.OrderSide;
import com.example.brokerservice.broker.enums.OrderStatus;
import com.example.brokerservice.broker.repository.AssetRepository;
import com.example.brokerservice.broker.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    AssetRepository assetRepository;

    @Test
    void whenGetOrders_thenReturnOrders() {
        var order = new Order();
        order.setId(938L);

        Mockito.when(orderRepository.findByCustomerIdAndCreatedDateBetweenOrderByCreatedDateAsc(
                Mockito.anyLong(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(order));

        var orders = orderService.getOrders(1L, LocalDate.now(), LocalDate.now());

        assertEquals(1, orders.size());
        assertEquals(938L, orders.get(0).getId());
    }

    @Test
    void whenCreateBuyOrder_thenReturnOrder() {
        var order = new Order();
        order.setId(938L);
        order.setOrderSide(OrderSide.BUY);
        order.setPrice(10);
        order.setSize(5);

        var asset = new Asset();
        asset.setSize(100);
        asset.setUsableSize(100);

        Mockito.when(assetRepository.findByCustomerIdAndAssetName(
                        Mockito.any(), Mockito.any()))
                .thenReturn(asset);

        orderService.createOrder(order);

        Mockito.verify(assetRepository, Mockito.times(1)).save(asset);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
    }

    @Test
    void whenCreateSellOrderWithInsufficientSize_thenFails() {
        var order = new Order();
        order.setId(938L);
        order.setOrderSide(OrderSide.SELL);
        order.setPrice(10);
        order.setSize(105);

        var asset = new Asset();
        asset.setSize(100);
        asset.setUsableSize(100);

        Mockito.when(assetRepository.findByCustomerIdAndAssetName(
                        Mockito.any(), Mockito.any()))
                .thenReturn(asset);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(order));
    }

    @Test
    void whenMatchOrder_thenAssetSizeUpdated() {
        var order = new Order();
        order.setCustomerId(938L);
        order.setAssetName("APPL");
        order.setOrderSide(OrderSide.SELL);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPrice(10);
        order.setSize(5);

        var asset = new Asset();
        asset.setSize(100);
        asset.setUsableSize(100);

        var assetAppl = new Asset();
        assetAppl.setSize(100);
        assetAppl.setUsableSize(95);
        assetAppl.setAssetName("APPL");

        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(order));

        Mockito.when(assetRepository.findByCustomerIdAndAssetName(
                        938L, "TRY"))
                .thenReturn(asset);

        Mockito.when(assetRepository.findByCustomerIdAndAssetName(
                        938L, "APPL"))
                .thenReturn(assetAppl);

        var argument = ArgumentCaptor.forClass(Asset.class);


        orderService.matchOrder(938L);


        Mockito.verify(assetRepository, Mockito.times(2)).save(argument.capture());
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
        var stockAsset = argument.getValue();
        assertEquals(95, stockAsset.getSize());
        assertEquals(95, stockAsset.getUsableSize());
    }

    @Test
    void whenDeleteBuyOrder_thenOrderIsCanceled() {
        var order = new Order();
        order.setId(938L);
        order.setCustomerId(3L);
        order.setOrderSide(OrderSide.BUY);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPrice(10);
        order.setSize(105);

        var asset = new Asset();
        asset.setSize(100);
        asset.setUsableSize(100);

        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(order));

        Mockito.when(assetRepository.findByCustomerIdAndAssetName(
                        Mockito.any(), Mockito.any()))
                .thenReturn(asset);


        orderService.deleteOrder(938L, 3L);


        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.assertArg(t -> {
            assertTrue(t.getOrderStatus() == OrderStatus.CANCELLED);
        }));
    }

    @Test
    void whenDeleteOrderWithMatchedAsset_thenItFails() {
        var order = new Order();
        order.setId(938L);
        order.setCustomerId(3L);
        order.setOrderSide(OrderSide.BUY);
        order.setOrderStatus(OrderStatus.MATCHED);
        order.setPrice(10);
        order.setSize(105);

        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(order));


        assertThrows(RuntimeException.class, () -> orderService.deleteOrder(938L, 3L));
    }
}