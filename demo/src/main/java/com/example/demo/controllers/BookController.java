package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @Operation(summary = "Create new book")
    @PostMapping("/create")
    public Book createBook(@RequestBody Book book) {
        Book newBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook).getBody();
    }

    @Operation(summary = "Get book list")
    @GetMapping("/all")
    public Iterable<Book> getAllBooks() {
        Iterable<Book> books = (Iterable<Book>) bookRepository.findAll();
        return (List<Book>) ResponseEntity.status(HttpStatus.OK).body(books).getBody();
    }

    @Operation(summary = "Get book by id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBook(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @Operation(summary = "Update book by id")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }

    @Operation(summary = "Delete book by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        bookRepository.delete(bookOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}