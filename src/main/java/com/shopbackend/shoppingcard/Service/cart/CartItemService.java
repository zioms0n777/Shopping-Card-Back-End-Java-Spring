package com.shopbackend.shoppingcard.Service.cart;

import com.shopbackend.shoppingcard.Model.Cart;
import com.shopbackend.shoppingcard.Model.CartItem;
import com.shopbackend.shoppingcard.Model.Product;
import com.shopbackend.shoppingcard.Repository.CartItemRepository;
import com.shopbackend.shoppingcard.Repository.CartRepository;
import com.shopbackend.shoppingcard.Service.product.IProductService;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final CartService cartService;


    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) {
        Cart cart =  cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream().filter(item-> item.getProduct()
                .getId().
                equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null)
        {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else
        {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long cartId, Long productId) {
Cart cart =  cartService.getCart(cartId);
CartItem itemToRemove = getCartItem(cartId, productId);
          cart.removeItem(itemToRemove);
          cartRepository.save(cart);




    }

    @Override
    public void updateCartItem(Long cartId, Long productId, int quantity) {
Cart cart =  cartService.getCart(cartId);
cart.getItems().stream().filter(item-> item.getProduct().getId()
        .equals(productId))
        .findFirst().ifPresent(item -> {
            item.setQuantity(quantity);
            item.setUnitPrice(item.getProduct().getPrice());
            item.setTotalPrice();
        });
        BigDecimal newTotal = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotal(newTotal);
        cartRepository.save(cart);


    }
@Override
public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart =  cartService.getCart(cartId);
        return cart.getItems().stream().filter(item -> item.getProduct()
                .getId().equals(productId)).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));

    }
}
