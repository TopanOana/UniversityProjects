package com.example.Books.Validation;

import com.example.Books.Exception.EmployeeValidationException;
import com.example.Books.Model.Employee;

public class ValidatorEmployee implements Validator<Employee> {
    @Override
    public void validate(Employee employee) {
        StringBuilder errors = new StringBuilder();
        if (employee.getFirstName().length()<3)
            errors.append("employee first name too short (length<3)\n");
        if(employee.getLastName().length()<3)
            errors.append("employee last name too short (length<3)\n");
        if(employee.getPhoneNumber().length()!=10)
            errors.append("employee phone number not 10 characters\n");
        if(employee.getSalary()<0)
            errors.append("employee salary negative, not possible\n");
        if (!errors.isEmpty())
            throw new EmployeeValidationException(errors.toString());
    }
}
