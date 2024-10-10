package com.shopbackend.shoppingcard.Repository;

import com.shopbackend.shoppingcard.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{

    void deleteAllByCartId(Long id);
}