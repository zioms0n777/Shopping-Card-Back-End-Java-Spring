package com.shopbackend.shoppingcard.Service.cart;

import com.shopbackend.shoppingcard.Model.Cart;
import com.shopbackend.shoppingcard.Model.User;

import java.math.BigDecimal;

public interface ICartService {


    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
