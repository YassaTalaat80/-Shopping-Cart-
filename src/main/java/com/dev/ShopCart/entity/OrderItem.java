package com.dev.ShopCart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_items")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "price",nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
}
