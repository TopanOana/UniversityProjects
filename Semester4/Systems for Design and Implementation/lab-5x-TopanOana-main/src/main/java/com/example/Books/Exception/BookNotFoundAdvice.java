package com.example.Books.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookNotFoundAdvice {
    @ResponseBody //advice rendered straight into the response body
    @ExceptionHandler(BookNotFoundException.class) //configures the advice to respond only for a BookNotFoundException is thrown
    @ResponseStatus(HttpStatus.NOT_FOUND) //issues and HTTP status not found (404)
    String bookNotFoundHandler(BookNotFoundException exception){
        /*
        the exception message will be shown when the controller finds it

         */
        return exception.getMessage();
    }
}
