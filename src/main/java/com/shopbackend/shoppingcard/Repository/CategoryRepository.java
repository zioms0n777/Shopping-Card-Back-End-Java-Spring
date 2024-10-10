package com.shopbackend.shoppingcard.Repository;

import com.shopbackend.shoppingcard.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
