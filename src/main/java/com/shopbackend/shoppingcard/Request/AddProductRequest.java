package com.shopbackend.shoppingcard.Request;

import com.shopbackend.shoppingcard.Model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class AddProductRequest {


    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;
    private Category category;
}
