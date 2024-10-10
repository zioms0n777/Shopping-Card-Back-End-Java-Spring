package com.shopbackend.shoppingcard.dto;

import com.shopbackend.shoppingcard.Model.Product;
import lombok.Data;

import java.math.BigDecimal;
@Data

public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
