package com.example.Books.Exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id){
        super("Book couldn't be found with id:"+id);
    }
}
