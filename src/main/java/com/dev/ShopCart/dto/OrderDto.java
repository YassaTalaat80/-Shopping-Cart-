package com.dev.ShopCart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Long id,
        LocalDate orderDate,
        BigDecimal totalAmount,
        String orderStatus,
        List<OrderItemDto> items,
        Long userId
) {}
