package com.example.Books;

import com.example.Books.Model.Book;
import com.example.Books.Repository.BookRepository;
import com.example.Books.Service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookFilterTest {


    private BookRepository bookRepository;

    private BookService bookService;

    @Before
    public void setUp(){
        this.bookRepository = Mockito.mock(BookRepository.class);
        this.bookService = new BookService(bookRepository);
        List<Book> books = new ArrayList<>();
        books.add(new Book("title0", "author0", 1,0.5,"genre0"));
        books.add(new Book("title1", "author1", 1,1.2,"genre1"));
        books.add(new Book("title2", "author2", 1,2.4,"genre"));
        books.add(new Book("title3", "author3", 1,3.5,"genre"));
        books.add(new Book("title4", "author4", 1,4.6,"genre"));
        when(bookRepository.findAll()).thenReturn(books);
    }

    @Test
    public void testFilterBooksByRatingUsingMock(){
//        List<Book> results = bookService.getBooksWithRatingGreaterThan(3.0, 0,5).getContent();
//        assertEquals(results.size(), 2);
//        assertEquals(results.get(0).getRating(), 3.5);
//        assertEquals(results.get(1).getRating(), 4.6);
//
//        List<Book> results1 = bookService.getBooksWithRatingGreaterThan(5.0, 0, 5).getContent();
//        assertEquals(results1.size(), 0);
    }



}
