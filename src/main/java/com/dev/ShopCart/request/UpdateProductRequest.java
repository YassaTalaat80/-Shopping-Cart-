package com.dev.ShopCart.request;

import com.dev.ShopCart.dto.CategoryDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @Min(value = 0, message = "Inventory cannot be negative")
    private int inventory;

    private String description;

    private CategoryDto category;
}