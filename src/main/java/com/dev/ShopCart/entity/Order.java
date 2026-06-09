package com.dev.ShopCart.entity;

import com.dev.ShopCart.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "order_date",nullable = false)
    private LocalDate orderDate;
    @Column(name = "total_amount",nullable = false)
    private BigDecimal totalAmount;
    @Column(name = "order_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}