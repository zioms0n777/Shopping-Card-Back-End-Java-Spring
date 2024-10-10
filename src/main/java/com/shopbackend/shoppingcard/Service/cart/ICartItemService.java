package com.shopbackend.shoppingcard.Service.cart;


import com.shopbackend.shoppingcard.Model.CartItem;

public interface ICartItemService {

    void addCartItem(Long cartId, Long productId, int quantity);
    void removeCartItem(Long cartId, Long productId);
    void updateCartItem(Long cartId, Long productId, int quantity);
    CartItem getCartItem(Long cartId, Long productId);
}
