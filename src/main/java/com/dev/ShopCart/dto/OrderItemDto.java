package com.dev.ShopCart.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        int quantity,
        BigDecimal price,
        ProductDto product
) {}
