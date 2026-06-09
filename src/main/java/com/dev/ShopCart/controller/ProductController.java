package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.ProductDto;
import com.dev.ShopCart.request.AddProductRequest;
import com.dev.ShopCart.request.UpdateProductRequest;
import com.dev.ShopCart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody AddProductRequest request) {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(params = "category")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping(params = "brand")
    public ResponseEntity<List<ProductDto>> getProductsByBrand(@RequestParam String brand) {
        return ResponseEntity.ok(productService.getProductsByBrand(brand));
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<ProductDto>> getProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }

    @GetMapping(params = {"category", "brand"})
    public ResponseEntity<List<ProductDto>> getProductsByCategoryAndBrand(
            @RequestParam String category, @RequestParam String brand) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndBrand(category, brand));
    }

    @GetMapping(params = {"brand", "name"})
    public ResponseEntity<List<ProductDto>> getProductsByBrandAndName(
            @RequestParam String brand, @RequestParam String name) {
        return ResponseEntity.ok(productService.getProductsByBrandAndName(brand, name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProductsByBrandAndName(
            @RequestParam String brand, @RequestParam String name) {
        return ResponseEntity.ok(productService.countProductsByBrandAndName(brand, name));
    }
}
