package com.dev.ShopCart.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        BigDecimal totalAmount,
        List<CartItemDto> items
) {}
