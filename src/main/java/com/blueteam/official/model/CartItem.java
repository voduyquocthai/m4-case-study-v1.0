package com.blueteam.official.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private double unitPrice;

    @OneToOne
    @JoinColumn( name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn( name = "cart_id")
    private Cart cart;
}