package com.tehzzcode.orderservice.controller;

import com.tehzzcode.orderservice.dto.OrderPlaced;
import com.tehzzcode.orderservice.dto.OrderRequest;
import com.tehzzcode.orderservice.service.OrderService;
import com.tehzzcode.orderservice.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/place")
    String placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderPlaced placeOrder = orderService.placeOrder(orderRequest);
        if (placeOrder.getSaved()) {
            return "Order Placed successfully";
        }
        return "Order Placed failed";
    }
}
