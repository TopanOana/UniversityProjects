package com.example.Books.Exception;

import com.example.Books.Model.Stock;
import com.example.Books.Repository.StockRepository;
import com.example.Books.Validation.Validator;
import org.junit.validator.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;


public class StockValidationException extends RuntimeException {
    public StockValidationException(String errors){
        super("Bad Request: "+errors);
    }
}
