package com.blueteam.official.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {

    private Long id;

    @NotEmpty(message = "This field cannot be empty !")
    private String name;

    private double price;

    private String color;

    private String description;

    private int size;

    private MultipartFile imgUrl;

    private Category category;
}
