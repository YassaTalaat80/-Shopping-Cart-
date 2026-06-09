package com.dev.ShopCart.service.product;

import com.dev.ShopCart.dto.ProductDto;
import com.dev.ShopCart.entity.Category;
import com.dev.ShopCart.entity.Product;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.mapper.ProductMapper;
import com.dev.ShopCart.repository.CategoryRepository;
import com.dev.ShopCart.repository.ProductRepository;
import com.dev.ShopCart.request.AddProductRequest;
import com.dev.ShopCart.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByNameContainingIgnoreCase(request.getCategory().name()))
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setName(request.getCategory().name());
                    return categoryRepository.save(c);
                });
        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        return productMapper.toDto(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> { throw new ResourceNotFoundException("Product not found!"); });
    }

    @Override
    public ProductDto updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> {
                    existingProduct.setName(request.getName());
                    existingProduct.setBrand(request.getBrand());
                    existingProduct.setPrice(request.getPrice());
                    existingProduct.setInventory(request.getInventory());
                    existingProduct.setDescription(request.getDescription());
                    if (request.getCategory() != null) {
                        Category category = Optional.ofNullable(categoryRepository.findByNameContainingIgnoreCase(request.getCategory().name()))
                                .orElseGet(() -> {
                                    Category c = new Category();
                                    c.setName(request.getCategory().name());
                                    return categoryRepository.save(c);
                                });
                        existingProduct.setCategory(category);
                    }
                    return productRepository.save(existingProduct);
                })
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndNameContainingIgnoreCase(brand, name).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndNameContainingIgnoreCase(brand, name);
    }

}
