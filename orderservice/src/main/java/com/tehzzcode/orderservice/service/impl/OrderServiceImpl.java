package com.tehzzcode.orderservice.service.impl;

import com.tehzzcode.orderservice.dto.InventoryResponse;
import com.tehzzcode.orderservice.dto.OrderLineItemsDto;
import com.tehzzcode.orderservice.dto.OrderPlaced;
import com.tehzzcode.orderservice.dto.OrderRequest;
import com.tehzzcode.orderservice.model.Order;
import com.tehzzcode.orderservice.model.OrderLineItems;
import com.tehzzcode.orderservice.repository.OrderRepository;
import com.tehzzcode.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public OrderPlaced placeOrder(OrderRequest orderRequest) {
        OrderPlaced orderPlaced = new OrderPlaced();
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //  call inventory service and place order if product is in stock
        try{
            InventoryResponse[] inventoryResponseArr = webClientBuilder.build().get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("""
                            http://localhost:9083/api/inventory/is-in-stock""");
                        skuCodes.forEach(skuCode -> uriBuilder.queryParam("skuCode", skuCode));
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            Boolean allProductsInStock = Arrays.stream(inventoryResponseArr).allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                orderPlaced.setSaved(true);
            } else {
                log.error("Product is not in Stock, please try again later");
            }
            return orderPlaced;
        } catch (Exception e) {
            e.printStackTrace();
            orderPlaced.setSaved(false);
            return orderPlaced;
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderListItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderListItemsDto.getPrice());
        orderLineItems.setQuantity(orderListItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderListItemsDto.getSkuCode());
        return orderLineItems;
    }
}
