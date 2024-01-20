package com.example.books.controller;

import com.example.books.configurations.BookWebSocketConn;
import com.example.books.model.Book;
import com.example.books.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class Controller {
    @Autowired private final BookService bookService;

    @Autowired private  final BookWebSocketConn webSocketConn;
    @GetMapping("/books")
    List<Book> getBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping("/add")
    Book addBook(@RequestBody @Valid Book newBook){
        Book book = bookService.addBook(newBook);
        webSocketConn.broadcast(book.getTitle() + " added!");
        return book;
    }

    @PutMapping("/update/{id}")
    Book updateBook(@RequestBody @Valid Book updatedBook, @PathVariable Integer id){
        Book book = bookService.updateBook(id, updatedBook);
        webSocketConn.broadcast(book.getTitle() + " updated!");
        return bookService.updateBook(id, updatedBook);
    }

    @DeleteMapping("/delete/{id}")
    void deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
        webSocketConn.broadcast(id.toString() + " deleted!");
    }

}
