package com.dev.ShopCart.dto;

import java.math.BigDecimal;

public record CartItemDto(
        Long id,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        ProductDto product
) {}
