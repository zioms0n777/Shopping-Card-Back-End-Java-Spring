package com.shopbackend.shoppingcard.controller;

import com.shopbackend.shoppingcard.Model.Cart;
import com.shopbackend.shoppingcard.Model.User;
import com.shopbackend.shoppingcard.Service.cart.CartItemService;
import com.shopbackend.shoppingcard.Service.cart.ICartItemService;
import com.shopbackend.shoppingcard.Service.cart.ICartService;
import com.shopbackend.shoppingcard.Service.user.IUserService;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import com.shopbackend.shoppingcard.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;



    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {

        try {
            User user = userService.getAuthenticatedUser();
           Cart cart=cartService.initializeNewCart(user);


            cartItemService.addCartItem(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse(null, "Successfully added item to the cart"));
        } catch (ResourceNotFoundException e) {
return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
        catch(JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(null, e.getMessage()));
        }




    }

@DeleteMapping("/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartId, @PathVariable Long productId) {
    try {
        cartItemService.removeCartItem(cartId, productId);
        return ResponseEntity.ok(new ApiResponse(null, "Successfully removed item from the cart"));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
    }

}


@PutMapping("/cart/{cartId}/item/{productId}/update")
   public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity)
   {
       try {
           cartItemService.updateCartItem(cartId, productId, quantity);
           return ResponseEntity.ok(new ApiResponse(null, "Successfully updated the cart"));
       } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
       }

   }
}
