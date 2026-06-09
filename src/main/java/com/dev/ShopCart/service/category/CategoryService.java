package com.dev.ShopCart.service.category;

import com.dev.ShopCart.dto.CategoryDto;
import com.dev.ShopCart.entity.Category;
import com.dev.ShopCart.exceptions.AlreadyExistsException;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.mapper.CategoryMapper;
import com.dev.ShopCart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!")));
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByNameContainingIgnoreCase(name);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found!");
        }
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (categoryRepository.findByNameContainingIgnoreCase(categoryDto.name()) != null) {
            throw new AlreadyExistsException(categoryDto.name() + " already exists");
        }
        Category category = new Category();
        category.setName(categoryDto.name());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    if (!existing.getName().equals(categoryDto.name())
                            && categoryRepository.findByNameContainingIgnoreCase(categoryDto.name()) != null) {
                        throw new AlreadyExistsException(categoryDto.name() + " already exists");
                    }
                    existing.setName(categoryDto.name());
                    return categoryMapper.toDto(categoryRepository.save(existing));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> { throw new ResourceNotFoundException("Category not found!"); });
    }

}
