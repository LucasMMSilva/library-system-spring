package com.example.demo.repositories;

import com.example.demo.models.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    @Query("SELECT bo FROM BookOrder bo WHERE bo.order_id = :orderId")
    List<BookOrder> findBookOrdersByOrderId(@Param("orderId") long orderId);
}
