package com.dev.ShopCart.service.cart;

import com.dev.ShopCart.dto.CartItemDto;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
    CartItemDto getCartItem(Long cartId, Long productId);
}
