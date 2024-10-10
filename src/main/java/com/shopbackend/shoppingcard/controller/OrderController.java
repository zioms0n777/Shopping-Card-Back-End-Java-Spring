package com.shopbackend.shoppingcard.controller;

import com.shopbackend.shoppingcard.Model.Order;
import com.shopbackend.shoppingcard.Service.order.IOrderService;
import com.shopbackend.shoppingcard.Service.order.OrderService;
import com.shopbackend.shoppingcard.dto.OrderDto;
import com.shopbackend.shoppingcard.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto=  orderService.convertToOrderDto(order);
            return ResponseEntity.ok(new ApiResponse(orderDto   , "Order Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), "Error"));
        }
    }

    @GetMapping("{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId)
    {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse(order, "Order Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), "Error"));
        }
    }
    @GetMapping("{userId}/orders")
    public ResponseEntity<ApiResponse> getUsersOrders(@PathVariable Long userId)
    {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse(order, "Order Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), "Error"));
        }
    }

}
