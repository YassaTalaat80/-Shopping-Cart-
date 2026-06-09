package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.CartItemDto;
import com.dev.ShopCart.service.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {
    private final ICartItemService cartItemService;

    @PostMapping
    public ResponseEntity<Void> addItemToCart(
            @RequestParam Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        cartItemService.addItemToCart(cartId, productId, quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable Long cartId, @PathVariable Long productId) {
        cartItemService.removeItemFromCart(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<Void> updateItemQuantity(
            @PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        cartItemService.updateItemQuantity(cartId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}/{productId}")
    public ResponseEntity<CartItemDto> getCartItem(
            @PathVariable Long cartId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartItemService.getCartItem(cartId, productId));
    }
}
