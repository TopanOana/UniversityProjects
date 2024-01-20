package com.example.Books.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book test;

    @BeforeEach
    void setUp() {
        test = new Book("title", "author", 1,1.0,"genre");
        Long id = 1L;
        test.setId(id);
    }

    @Test
    void emptyConstructor() {
        test = new Book();
        assertNull(test.getId());
        assertNull(test.getTitle());
        assertNull(test.getAuthor());
        assertNull(test.getGenre());
        assertEquals(test.getRating(),0.0);
        assertEquals(test.getNrPages(),0);

    }

    @Test
    void getId() {
        assert(test.getId()==1);
        assert(test.getId()!=2);
    }

    @Test
    void getTitle() {
        assert(test.getTitle().equals("title"));
        assert(!test.getTitle().equals("t"));
    }

    @Test
    void getAuthor() {
        assert(test.getAuthor().equals("author"));
        assert(!test.getAuthor().equals("a"));
    }

    @Test
    void getNrPages() {
        assertEquals(test.getNrPages(),1);
        assertNotEquals(test.getNrPages(),3);
    }

    @Test
    void getRating() {
        assertEquals(test.getRating(),1.0);
        assertNotEquals(test.getRating(),3.0);
    }

    @Test
    void setTitle() {
        assert(test.getTitle().equals("title"));
        test.setTitle("title1");
        assert(!test.getTitle().equals("title"));
        assert(test.getTitle().equals("title1"));
    }

    @Test
    void setAuthor() {
        assert(test.getAuthor().equals("author"));
        test.setAuthor("author1");
        assert(!test.getAuthor().equals("author"));
        assert(test.getAuthor().equals("author1"));
    }

    @Test
    void setNrPages() {
        assertEquals(test.getNrPages(),1);
        test.setNrPages(10);
        assertNotEquals(test.getNrPages(), 1);
        assertEquals(test.getNrPages(),10);
    }

    @Test
    void setRating() {
        assertEquals(test.getRating(),1.0);
        test.setRating(3.0);
        assertNotEquals(test.getRating(),1.0);
        assertEquals(test.getRating(),3.0);
    }

    @Test
    void setId() {
        assertEquals(test.getId(),1);
        test.setId(2L);
        assertNotEquals(test.getId(),1);
        assertEquals(test.getId(),2);
    }
    @Test
    void getGenre() {
        assert(test.getGenre().equals("genre"));
        assert(!test.getGenre().equals("g"));
    }

    @Test
    void setGenre() {
        assert(test.getGenre().equals("genre"));
        test.setGenre("genre1");
        assert(!test.getGenre().equals("genre"));
        assert(test.getGenre().equals("genre1"));

    }

    @Test
    void toStringTest() {
        assertEquals(test.toString(),"Book{id=1, title='title', author='author', nrPages=1, rating=1.0, genre='genre'}");
        assertNotEquals(test.toString(),"");
    }
}