package com.example.Books.Model.DTO;

import com.example.Books.Model.Book;
import com.example.Books.Model.Store;

public class StockDTO {
    private Long id;
    private Store store;
    private Book book;
    private int quantity;
    private String username;

    public StockDTO(Long id, Store store, Book book, int quantity, String username) {
        this.id = id;
        this.store = store;
        this.book = book;
        this.quantity = quantity;
        this.username = username;
    }

    public StockDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
