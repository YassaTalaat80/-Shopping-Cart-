package com.dev.ShopCart.mapper;

import com.dev.ShopCart.dto.CartDto;
import com.dev.ShopCart.dto.CartItemDto;
import com.dev.ShopCart.entity.Cart;
import com.dev.ShopCart.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final ProductMapper productMapper;

    public CartDto toDto(Cart cart) {
        List<CartItemDto> itemDtos = cart.getItems().stream()
                .map(this::toCartItemDto)
                .toList();
        return new CartDto(cart.getId(), cart.getTotalAmount(), itemDtos);
    }

    public CartItemDto toCartItemDto(CartItem item) {
        return new CartItemDto(
                item.getId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice(),
                productMapper.toDto(item.getProduct()));
    }
}
