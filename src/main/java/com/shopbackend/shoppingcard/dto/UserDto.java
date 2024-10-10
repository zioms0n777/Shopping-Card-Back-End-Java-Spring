package com.shopbackend.shoppingcard.dto;

import com.shopbackend.shoppingcard.Model.Cart;
import com.shopbackend.shoppingcard.Model.Order;
import lombok.Data;

import java.util.List;
@Data

public class UserDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
