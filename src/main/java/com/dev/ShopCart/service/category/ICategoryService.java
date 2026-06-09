package com.dev.ShopCart.service.category;

import com.dev.ShopCart.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto getCategoryById(Long id);

    CategoryDto getCategoryByName(String name);

    List<CategoryDto> getAllCategories();

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    void deleteCategoryById(Long id);
}
