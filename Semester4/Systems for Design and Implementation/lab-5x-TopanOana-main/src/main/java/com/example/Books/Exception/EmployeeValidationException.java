package com.example.Books.Exception;

public class EmployeeValidationException extends RuntimeException{
    public EmployeeValidationException(String errors){
        super("Bad Request:\n"+errors);
    }
}
