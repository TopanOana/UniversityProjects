package com.example.Books.Exception;

public class StoreValidationException extends RuntimeException{
    public StoreValidationException(String errors){
        super("Bad Request:\n"+errors);
    }
}
