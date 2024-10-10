package com.shopbackend.shoppingcard.Repository;


import com.shopbackend.shoppingcard.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {


    Cart findByUserId(Long userId);
}
