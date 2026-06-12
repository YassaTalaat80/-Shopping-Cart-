package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.CartDto;
import com.dev.ShopCart.security.user.ShopUserDetails;
import com.dev.ShopCart.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Validated
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal ShopUserDetails userDetails) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal ShopUserDetails userDetails) {
        Long userId = userDetails.getId();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total-price")
    public ResponseEntity<BigDecimal> getTotalPrice(@AuthenticationPrincipal ShopUserDetails userDetails) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(cartService.getTotalPrice(userId));
    }
}
