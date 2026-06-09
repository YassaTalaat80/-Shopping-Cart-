package com.dev.ShopCart.service.product;

import com.dev.ShopCart.dto.ProductDto;
import com.dev.ShopCart.request.AddProductRequest;
import com.dev.ShopCart.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
  ProductDto addProduct(AddProductRequest product);

  ProductDto getProductById(Long id);

  void deleteProductById(Long id);

  ProductDto updateProduct(UpdateProductRequest product, Long productId);

  List<ProductDto> getAllProducts();

  List<ProductDto> getProductsByCategory(String category);

  List<ProductDto> getProductsByBrand(String brand);

  List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);

  List<ProductDto> getProductsByName(String name);

  List<ProductDto> getProductsByBrandAndName(String brand, String name);

  Long countProductsByBrandAndName(String brand, String name);

}
