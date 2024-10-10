package com.shopbackend.shoppingcard.Service.cart;

import com.shopbackend.shoppingcard.Model.Cart;
import com.shopbackend.shoppingcard.Model.User;
import com.shopbackend.shoppingcard.Repository.CartItemRepository;
import com.shopbackend.shoppingcard.Repository.CartRepository;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {


    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal total = cart.getTotal();
        cart.setTotal(total);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
 Cart cart = getCart(id);
 cartItemRepository.deleteAllByCartId(id);
 cart.getItems().clear();
 cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotal();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()->
                {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });



    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

}
