package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.BookOrder;
import com.example.demo.models.Cart;
import com.example.demo.models.Order;
import com.example.demo.repositories.BookOrderRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookOrderRepository bookOrderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Operation(summary = "Finish order")
    @PostMapping("/closeorder")
    public ResponseEntity<String> closeOrder(@RequestBody Order order) {
        Order orderObj = orderRepository.save(order);
        List<Cart> cartItems = cartRepository.findCartByUserId(orderObj.getUser_id());
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        for (Cart cartItem : cartItems) {
            BookOrder bookOrder = new BookOrder();
            bookOrder.setOrder_id(orderObj.getId());
            bookOrder.setBook_id(cartItem.getBook_id());
            bookOrderRepository.save(bookOrder);
        }
        cartRepository.deleteCartByUserId(orderObj.getUser_id());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(summary = "Get orders list by user")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable("userId") long userId) {
        List<Order> orders = orderRepository.findOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Operation(summary = "Get order by id")
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable("orderId") long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        List<BookOrder> bookOrders = bookOrderRepository.findBookOrdersByOrderId(orderId);
        List<Book> books = new ArrayList<>();
        for (BookOrder bookOrder : bookOrders) {
            Optional<Book> book = bookRepository.findById(bookOrder.getBook_id());
            book.ifPresent(books::add);
        }
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }
}