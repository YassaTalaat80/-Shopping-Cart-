package com.dev.ShopCart.service.cart;

import com.dev.ShopCart.dto.CartDto;

import java.math.BigDecimal;

public interface ICartService {
    CartDto getCartByUserId(Long userId);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
