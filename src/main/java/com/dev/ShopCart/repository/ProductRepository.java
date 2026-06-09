package com.dev.ShopCart.repository;

import com.dev.ShopCart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryName(String categoryName);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String categoryName, String brand);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByBrandAndNameContainingIgnoreCase(String brand, String name);

    long countByBrandAndNameContainingIgnoreCase(String brand, String name);
}