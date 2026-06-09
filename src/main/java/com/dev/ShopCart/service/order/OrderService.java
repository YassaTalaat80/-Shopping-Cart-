package com.dev.ShopCart.service.order;

import com.dev.ShopCart.dto.OrderDto;
import com.dev.ShopCart.entity.Cart;
import com.dev.ShopCart.entity.Order;
import com.dev.ShopCart.entity.OrderItem;
import com.dev.ShopCart.entity.CartItem;
import com.dev.ShopCart.entity.User;
import com.dev.ShopCart.enums.OrderStatus;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.mapper.OrderMapper;
import com.dev.ShopCart.repository.CartRepository;
import com.dev.ShopCart.repository.OrderRepository;
import com.dev.ShopCart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user!"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty cart!");
        }

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(user);

        BigDecimal totalAmount = BigDecimal.ZERO;
        HashSet<OrderItem> orderItems = new HashSet<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getUnitPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(cartItem.getTotalPrice());
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }
}
