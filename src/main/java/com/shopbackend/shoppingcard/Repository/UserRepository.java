package com.shopbackend.shoppingcard.Repository;

import com.shopbackend.shoppingcard.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
