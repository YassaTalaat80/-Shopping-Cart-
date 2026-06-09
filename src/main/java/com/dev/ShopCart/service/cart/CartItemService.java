package com.dev.ShopCart.service.cart;

import com.dev.ShopCart.dto.CartItemDto;
import com.dev.ShopCart.entity.Cart;
import com.dev.ShopCart.entity.CartItem;
import com.dev.ShopCart.entity.Product;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.mapper.CartMapper;
import com.dev.ShopCart.repository.CartItemRepository;
import com.dev.ShopCart.repository.CartRepository;
import com.dev.ShopCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .ifPresentOrElse(
                        existing -> {
                            existing.setQuantity(existing.getQuantity() + quantity);
                            existing.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(existing.getQuantity())));
                            cartItemRepository.save(existing);
                        },
                        () -> {
                            CartItem item = new CartItem();
                            item.setCart(cart);
                            item.setProduct(product);
                            item.setQuantity(quantity);
                            item.setUnitPrice(product.getPrice());
                            item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                            cart.getItems().add(item);
                            cartItemRepository.save(item);
                        }
                );

        recalculateCartTotal(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        CartItem item = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found!"));
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        recalculateCartTotal(cart);
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        CartItem item = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found!"));
        item.setQuantity(quantity);
        item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItemRepository.save(item);
        recalculateCartTotal(item.getCart());
    }

    @Override
    public CartItemDto getCartItem(Long cartId, Long productId) {
        CartItem item = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found!"));
        return cartMapper.toCartItemDto(item);
    }

    private void recalculateCartTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
        cartRepository.save(cart);
    }
}
