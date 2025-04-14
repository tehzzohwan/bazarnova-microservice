package com.tehzzcode.orderservice.service;

import com.tehzzcode.orderservice.dto.OrderPlaced;
import com.tehzzcode.orderservice.dto.OrderRequest;

public interface OrderService {
    OrderPlaced placeOrder(OrderRequest orderRequest);
}
