package com.example.brokerservice.broker.controllers;

import com.example.brokerservice.auth.entity.User;
import com.example.brokerservice.auth.service.UserService;
import com.example.brokerservice.broker.entity.Order;
import com.example.brokerservice.broker.enums.OrderSide;
import com.example.brokerservice.broker.service.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@WithMockUser(authorities = "CUSTOMER")
class OrderControllerTest {
    @MockitoBean
    OrderService orderService;

    @MockitoBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void whenGetOrders_thenReturnOrders() throws Exception {
        var order = new Order();
        order.setId(938L);
        order.setOrderSide(OrderSide.BUY);
        order.setAssetName("APPL");
        order.setPrice(10);
        order.setSize(5);

        Mockito.when(userService.getByUserName(Mockito.anyString())).thenReturn(new User());
        Mockito.when(orderService.getOrders(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(List.of(order));


        mockMvc.perform(get("/api/v1/orders?startDate=2017-07-12&endDate=2025-07-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].assetName", Matchers.is("APPL")));
    }

}