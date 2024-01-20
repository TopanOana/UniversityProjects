package com.example.Books.Exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Long id){
        super("Employee not found with id: "+id.toString());
    }
}
