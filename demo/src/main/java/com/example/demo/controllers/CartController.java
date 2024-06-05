package com.example.demo.controllers;
import com.example.demo.models.Cart;
import com.example.demo.repositories.CartRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Operation(summary = "Add boot to cart")
    @PostMapping("/add")
    public Cart addInCart(@RequestBody Cart cart){
        Cart newItem = cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem).getBody();
    }

    @Operation(summary = "Show books in cart")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAll (@PathVariable Long id){
        List<Cart> carts = cartRepository.findCartByUserId(id);
        if(carts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(carts);
    }

    @Operation(summary = "Clear cart")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteAll(@PathVariable("userId") Long userId) {
        List<Cart> items = cartRepository.findCartByUserId(userId);
        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        cartRepository.deleteCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
