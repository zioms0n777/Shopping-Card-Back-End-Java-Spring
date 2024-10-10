package com.shopbackend.shoppingcard.Service.order;

import com.shopbackend.shoppingcard.Model.Cart;
import com.shopbackend.shoppingcard.Model.Order;
import com.shopbackend.shoppingcard.Model.OrderItem;
import com.shopbackend.shoppingcard.Model.Product;
import com.shopbackend.shoppingcard.Repository.OrderRepository;
import com.shopbackend.shoppingcard.Repository.ProductRepository;
import com.shopbackend.shoppingcard.Service.cart.CartService;
import com.shopbackend.shoppingcard.dto.OrderDto;
import com.shopbackend.shoppingcard.enums.OrderStatus;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
Cart cart = cartService.getCartByUserId(userId);
Order order = createOrder(cart);
List<OrderItem> orderItemList = createOrderItems(order, cart);
order.setOrderItems(new HashSet<>(orderItemList));
order.setOrderTotalAmount(calculateTotal(orderItemList));
Order orderSaved = orderRepository.save(order);

cartService.clearCart(cart.getId());

return orderSaved;
    }


    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    private List<OrderItem>  createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem->
        {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()- cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    product,
                    order,
                    cartItem.getUnitPrice(),
                    cartItem.getQuantity()
            );
        }).toList();
    }

    private BigDecimal calculateTotal(List<OrderItem> orderItems) {
        return orderItems.stream().map(item-> item.getUnitPrice()
                .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToOrderDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List <Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToOrderDto).toList();
    }
@Override
public OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
