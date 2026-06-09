package com.dev.ShopCart.service.cart;

import com.dev.ShopCart.dto.CartDto;
import com.dev.ShopCart.entity.Cart;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.mapper.CartMapper;
import com.dev.ShopCart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public CartDto getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return cartMapper.toDto(cart);
    }

    @Override
    public CartDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user!"));
        return cartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return cartRepository.findById(id)
                .map(Cart::getTotalAmount)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
    }
}
