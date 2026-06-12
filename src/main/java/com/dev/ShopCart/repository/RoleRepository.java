package com.dev.ShopCart.repository;

import com.dev.ShopCart.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
