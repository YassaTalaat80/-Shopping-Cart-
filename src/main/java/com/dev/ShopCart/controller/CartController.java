package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.CartDto;
import com.dev.ShopCart.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clearCart(@PathVariable Long id) {
        cartService.clearCart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/total-price")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getTotalPrice(id));
    }
}
