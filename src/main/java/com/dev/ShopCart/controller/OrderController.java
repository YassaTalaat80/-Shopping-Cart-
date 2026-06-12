package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.OrderDto;
import com.dev.ShopCart.security.user.ShopUserDetails;
import com.dev.ShopCart.service.order.IOrderService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@AuthenticationPrincipal ShopUserDetails userDetails) {
        return new ResponseEntity<>(orderService.createOrder(userDetails.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(
            @Positive(message = "Order ID must be a positive number") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders(@AuthenticationPrincipal ShopUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getUserOrders(userDetails.getId()));
    }
}
