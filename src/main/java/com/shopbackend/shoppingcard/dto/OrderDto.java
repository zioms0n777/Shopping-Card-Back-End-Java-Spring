package com.shopbackend.shoppingcard.dto;

import com.shopbackend.shoppingcard.Model.OrderItem;
import com.shopbackend.shoppingcard.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
public class OrderDto {



    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal orderTotalAmount;
    private String orderStatus;
    private Set<OrderItemDto> orderItems = new HashSet<>();

}
