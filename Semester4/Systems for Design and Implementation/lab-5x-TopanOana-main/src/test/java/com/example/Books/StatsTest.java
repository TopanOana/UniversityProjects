package com.example.Books;

import com.example.Books.Model.*;
import com.example.Books.Model.DTO.BookStockDTO;
import com.example.Books.Model.DTO.StoreStockDTO;
import com.example.Books.Repository.BookRepository;
import com.example.Books.Repository.StoreRepository;
import com.example.Books.Service.StatService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private StoreRepository storeRepository;

    private StatService statService;


    @Before
    public void setUp(){
        this.storeRepository = Mockito.mock(StoreRepository.class);
        List<Store> stores = new ArrayList<>();
        stores.add(new Store("store1", "address1", "123456789", 10, 20));

        stores.add(new Store("store2", "address2", "223456789", 10, 20));
        stores.add(new Store("store3", "address3", "333456789", 10, 20));

        this.bookRepository = Mockito.mock(BookRepository.class);
        List<Book> books = new ArrayList<>();
        books.add(new Book("title0", "author0", 1,0.5,"genre0"));
        books.add(new Book("title1", "author1", 1,1.2,"genre1"));
        books.add(new Book("title2", "author2", 1,2.4,"genre"));
        books.add(new Book("title3", "author3", 1,3.5,"genre"));
        books.add(new Book("title4", "author4", 1,4.6,"genre"));

        //store1 quantity = 15
        //store2 quantity = 35
        //store3 quantity = 5


        stores.get(0).addStockToStore(new Stock(books.get(0), stores.get(0),10));
        stores.get(0).addStockToStore(new Stock(books.get(3), stores.get(0),5));
        stores.get(1).addStockToStore(new Stock(books.get(2), stores.get(1),15));
        stores.get(1).addStockToStore(new Stock(books.get(4), stores.get(1),20));
        stores.get(2).addStockToStore(new Stock(books.get(1), stores.get(2),5));
        //book0 quantity = 10
        //book1 quantity = 5
        //book2 quantity = 15
        //book3 quantity = 5
        //book4 quantity = 20
        books.get(0).addStock(new Stock(books.get(0),stores.get(0),10));
        books.get(1).addStock(new Stock(books.get(1),stores.get(2),5));
        books.get(2).addStock(new Stock(books.get(2),stores.get(1),15));
        books.get(3).addStock(new Stock(books.get(3),stores.get(0),5));
        books.get(4).addStock(new Stock(books.get(4),stores.get(1),20));

        when(storeRepository.findAll()).thenReturn(stores);
        when(bookRepository.findAll()).thenReturn(books);
        this.statService =  new StatService(storeRepository,bookRepository);
    }

    @Test
    public void convertDataIntoStoreStockDTOTest(){
        Store testStore = new Store("store1", "address1", "123456789", 10, 20);
        testStore.addStockToStore(new Stock(new Book("title0", "author0", 1,0.5,"genre0"), testStore, 10));
        StoreStockDTO result = statService.convertDataIntoStoreStockDTO(testStore);
        assertEquals(result.getQuantity(),10);
        assertEquals(result.getStoreName(),"store1");
    }

    @Test
    public void getAllStoresByNumberOfBooksTest(){
        List<StoreStockDTO> results = statService.getAllStoresByNumberOfBooks();
        assertEquals(results.size(),3);
        assertEquals(results.get(0).getQuantity(), 35);
        assertEquals(results.get(0).getStoreName(), "store2");
        assertEquals(results.get(1).getQuantity(), 15);
        assertEquals(results.get(1).getStoreName(), "store1");
        assertEquals(results.get(2).getQuantity(), 5);
        assertEquals(results.get(2).getStoreName(), "store3");
    }

    @Test
    public void getAllBooksByNumberTest(){
        List<BookStockDTO> results = statService.getAllBooksByNumber();
        assertEquals(results.size(),5);
        assertEquals(results.get(0).getQuantity(),20);
        assertEquals(results.get(0).getTitle(), "title4");
        assertEquals(results.get(1).getQuantity(),15);
        assertEquals(results.get(1).getTitle(), "title2");
        assertEquals(results.get(2).getQuantity(),10);
        assertEquals(results.get(2).getTitle(), "title0");
        assertEquals(results.get(3).getQuantity(),5);
        assertEquals(results.get(3).getTitle(), "title1");
        assertEquals(results.get(4).getQuantity(),5);
        assertEquals(results.get(4).getTitle(), "title3");
    }
}
