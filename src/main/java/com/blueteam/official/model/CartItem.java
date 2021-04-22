package com.blueteam.official.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Product product;

    private Integer quantity;

}