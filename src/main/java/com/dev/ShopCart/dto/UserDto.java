package com.dev.ShopCart.dto;
import java.util.Set;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        CartDto cart,
        Set<String> roles
) {}
