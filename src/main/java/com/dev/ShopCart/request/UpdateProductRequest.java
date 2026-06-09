package com.dev.ShopCart.request;

import com.dev.ShopCart.dto.CategoryDto;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateProductRequest {
  private String name;
  private String brand;
  private BigDecimal price;
  private int inventory;
  private String description;
  private CategoryDto category;
}
