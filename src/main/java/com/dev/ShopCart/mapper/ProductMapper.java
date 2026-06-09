package com.dev.ShopCart.mapper;

import com.dev.ShopCart.dto.CategoryDto;
import com.dev.ShopCart.dto.ImageDto;
import com.dev.ShopCart.dto.ProductDto;
import com.dev.ShopCart.entity.Product;
import com.dev.ShopCart.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;
    private final ImageMapper imageMapper;

    public Product toEntity(AddProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        product.setDescription(request.getDescription());
        return product;
    }

    public ProductDto toDto(Product product) {
        List<ImageDto> imageDtos = product.getImages() != null
                ? product.getImages().stream().map(imageMapper::toDto).toList()
                : List.of();
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                categoryMapper.toDto(product.getCategory()),
                imageDtos);
    }
}
