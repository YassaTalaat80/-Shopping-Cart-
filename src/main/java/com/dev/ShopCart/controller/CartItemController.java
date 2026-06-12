package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.CartItemDto;
import com.dev.ShopCart.security.user.ShopUserDetails;
import com.dev.ShopCart.service.cart.ICartItemService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {
    private final ICartItemService cartItemService;

    @PostMapping
    public ResponseEntity<Void> addItemToCart(
            @AuthenticationPrincipal ShopUserDetails userDetails,
            @Positive(message = "Product ID must be a positive number") @RequestParam Long productId,
            @Min(value = 1, message = "Quantity must be at least 1") @RequestParam int quantity) {
        cartItemService.addItemToCart(userDetails.getId(), productId, quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @AuthenticationPrincipal ShopUserDetails userDetails,
            @Positive(message = "Product ID must be a positive number") @PathVariable Long productId) {
        cartItemService.removeItemFromCart(userDetails.getId(), productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateItemQuantity(
            @AuthenticationPrincipal ShopUserDetails userDetails,
            @Positive(message = "Product ID must be a positive number") @PathVariable Long productId,
            @Min(value = 1, message = "Quantity must be at least 1") @RequestParam int quantity) {
        cartItemService.updateItemQuantity(userDetails.getId(), productId, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CartItemDto> getCartItem(
            @AuthenticationPrincipal ShopUserDetails userDetails,
            @Positive(message = "Product ID must be a positive number") @PathVariable Long productId) {
        return ResponseEntity.ok(cartItemService.getCartItem(userDetails.getId(), productId));
    }
}
