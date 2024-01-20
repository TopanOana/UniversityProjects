package com.example.Books.Exception;

public class BookValidationException extends RuntimeException{
    public BookValidationException(String errors){
        super("Bad Request:\n"+errors);
    }
}
