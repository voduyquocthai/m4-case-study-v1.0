package com.blueteam.official.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "This field cannot be empty !")
    private String name;

    private double price;

    private String color;

    private String description;

    private int size;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}