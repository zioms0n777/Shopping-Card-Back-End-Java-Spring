package com.shopbackend.shoppingcard.Service.order;

import com.shopbackend.shoppingcard.Model.Order;
import com.shopbackend.shoppingcard.dto.OrderDto;
import com.shopbackend.shoppingcard.dto.OrderItemDto;

import java.util.List;

public interface IOrderService {



    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToOrderDto(Order order);
}
