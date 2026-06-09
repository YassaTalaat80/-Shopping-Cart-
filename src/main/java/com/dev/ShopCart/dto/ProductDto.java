package com.dev.ShopCart.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String brand,
        BigDecimal price,
        int inventory,
        String description,
        CategoryDto category,
        List<ImageDto> images
) {}
