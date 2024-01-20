package com.example.Books.Exception;

public class StoreNotFoundException extends RuntimeException{
    public StoreNotFoundException(Long id){
        super("Store couldn't be found with id:"+id);
    }
}
