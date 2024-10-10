package com.shopbackend.shoppingcard.Model;

import jakarta.persistence.*;
import jdk.jfr.Period;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;


@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Image> images;


    public Product(String name, String brand, BigDecimal price, String description, int inventory, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.inventory = inventory;
        this.category = category;
    }
}
