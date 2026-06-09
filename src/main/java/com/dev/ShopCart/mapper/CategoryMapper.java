package com.dev.ShopCart.mapper;

import com.dev.ShopCart.dto.CategoryDto;
import com.dev.ShopCart.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
