package com.dev.ShopCart.mapper;

import com.dev.ShopCart.dto.OrderDto;
import com.dev.ShopCart.dto.OrderItemDto;
import com.dev.ShopCart.entity.Order;
import com.dev.ShopCart.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final ProductMapper productMapper;

    public OrderDto toDto(Order order) {
        List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(this::toOrderItemDto)
                .toList();
        return new OrderDto(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus().name(),
                itemDtos,
                order.getUser().getId());
    }

    public OrderItemDto toOrderItemDto(OrderItem item) {
        return new OrderItemDto(
                item.getId(),
                item.getQuantity(),
                item.getPrice(),
                productMapper.toDto(item.getProduct()));
    }
}
