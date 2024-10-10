package com.shopbackend.shoppingcard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private int quantity;
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    public OrderItem(Product product, Order order, BigDecimal unitPrice, int quantity) {
        this.product = product;
        this.order = order;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}
