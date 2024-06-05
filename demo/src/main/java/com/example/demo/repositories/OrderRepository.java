package com.example.demo.repositories;

import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
    @Query("SELECT o FROM Order o WHERE o.user_id = :userId")
    List<Order> findOrdersByUserId(@Param("userId") long userId);
}