package com.shopbackend.shoppingcard.dto;

import com.shopbackend.shoppingcard.Model.Category;
import com.shopbackend.shoppingcard.Model.Image;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class ProductDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;
    private Category category;
    private List<ImageDto> images;

}
