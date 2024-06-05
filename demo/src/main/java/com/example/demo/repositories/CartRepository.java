package com.example.demo.repositories;

import com.example.demo.models.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long> {
    @Query(value = "SELECT * FROM carts WHERE user_id = :user_id", nativeQuery = true)
    List<Cart> findCartByUserId(@Param("user_id") long user_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user_id = :userId")
    void deleteCartByUserId(@Param("userId") long userId);
}
