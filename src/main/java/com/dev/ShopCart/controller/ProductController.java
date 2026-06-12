package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.ProductDto;
import com.dev.ShopCart.request.AddProductRequest;
import com.dev.ShopCart.request.UpdateProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import com.dev.ShopCart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody AddProductRequest request) {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@Positive(message = "Product ID must be a positive number") @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(params = "category")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@NotBlank(message = "Category name is required") @RequestParam String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping(params = "brand")
    public ResponseEntity<List<ProductDto>> getProductsByBrand(@NotBlank(message = "Brand name is required") @RequestParam String brand) {
        return ResponseEntity.ok(productService.getProductsByBrand(brand));
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<ProductDto>> getProductsByName(@NotBlank(message = "Product name is required") @RequestParam String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }

    @GetMapping(params = {"category", "brand"})
    public ResponseEntity<List<ProductDto>> getProductsByCategoryAndBrand(
            @NotBlank(message = "Category name is required") @RequestParam String category,
            @NotBlank(message = "Brand name is required") @RequestParam String brand) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndBrand(category, brand));
    }

    @GetMapping(params = {"brand", "name"})
    public ResponseEntity<List<ProductDto>> getProductsByBrandAndName(
            @NotBlank(message = "Brand name is required") @RequestParam String brand,
            @NotBlank(message = "Product name is required") @RequestParam String name) {
        return ResponseEntity.ok(productService.getProductsByBrandAndName(brand, name));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody UpdateProductRequest request, @Positive(message = "Product ID must be a positive number") @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(request, id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@Positive(message = "Product ID must be a positive number") @PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProductsByBrandAndName(
            @NotBlank(message = "Brand name is required") @RequestParam String brand,
            @NotBlank(message = "Product name is required") @RequestParam String name) {
        return ResponseEntity.ok(productService.countProductsByBrandAndName(brand, name));
    }
}
