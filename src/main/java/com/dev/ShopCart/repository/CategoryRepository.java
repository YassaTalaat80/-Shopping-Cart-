package com.dev.ShopCart.repository;

import com.dev.ShopCart.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByNameContainingIgnoreCase(String name);
}
