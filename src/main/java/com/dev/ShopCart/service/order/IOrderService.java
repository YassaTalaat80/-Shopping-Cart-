package com.dev.ShopCart.service.order;

import com.dev.ShopCart.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    OrderDto createOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
