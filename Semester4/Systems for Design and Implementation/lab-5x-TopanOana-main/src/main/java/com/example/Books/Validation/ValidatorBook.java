package com.example.Books.Validation;

import com.example.Books.Exception.BookValidationException;
import com.example.Books.Model.Book;


public class ValidatorBook implements Validator<Book>{
    public void validate(Book book){
        StringBuilder errors = new StringBuilder();
        if(book.getTitle().length()<4)
            errors.append("book title too short (length <4 characters)\n");
        if(book.getAuthor().length()<4)
            errors.append("book author too short (length <4 characters)\n");
        if(book.getGenre().length()<4)
            errors.append("book genre too short (length <4 characters)\n");
        if(book.getNrPages() < 1)
            errors.append("book number of pages (nrPages <1\n");
        if(book.getRating()<0 || book.getRating()>5)
            errors.append("book rating not in [0,5] interval\n");
        if(!errors.isEmpty())
            throw new BookValidationException(errors.toString());
    }
}
