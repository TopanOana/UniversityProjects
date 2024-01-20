package com.example.books.service;

import com.example.books.model.Book;
import com.example.books.repository.BookRepository;
import com.example.books.websockets.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        System.out.println("Get all books accessed!");
        return bookRepository.findAll();
    }

    public Book addBook(Book newBook) {
        System.out.printf("Book %s added to repository\n", newBook.getTitle());
        return bookRepository.save(newBook);
    }

    public Book updateBook(Integer id, Book updatedBook) {
        System.out.printf("Book with id %d updated\n", id);
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setNrPages(updatedBook.getNrPages());
            book.setStatus(updatedBook.getStatus());
            book.setGenre(updatedBook.getGenre());
            return bookRepository.save(book);
        }).orElseGet(() -> {
            updatedBook.setId(id);
            return bookRepository.save(updatedBook);
        });
    }

    public void deleteBook(Integer id) {
        System.out.printf("Book with id %d deleted\n", id);
        bookRepository.deleteById(id);
    }

}
