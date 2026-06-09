package com.dev.ShopCart.dto;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        CartDto cart
) {}
