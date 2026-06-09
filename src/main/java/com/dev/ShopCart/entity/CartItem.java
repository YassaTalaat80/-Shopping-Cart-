package com.dev.ShopCart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Setter
@Getter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price",nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

}
