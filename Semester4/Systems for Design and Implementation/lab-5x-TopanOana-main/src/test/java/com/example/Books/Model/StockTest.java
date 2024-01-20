//package com.example.Books.Model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class StockTest {
//    private Stock test;
//
//    @BeforeEach
//    void setUp() {
//        test = new Stock(1L,1L,1);
//        test.setId(1L);
//    }
//
//    @Test
//    void setId() {
//        assertEquals(test.getId(),1);
//        test.setId(2L);
//        assertEquals(test.getId(),2);
//    }
//
//    @Test
//    void getBookID() {
//        assertEquals(test.getBookID(),1);
//        assertNotEquals(test.getBookID(),2);
//    }
//
//    @Test
//    void setBookID() {
//        assertEquals(test.getBookID(),1);
//        assertNotEquals(test.getBookID(),2);
//        test.setBookID(2L);
//        assertEquals(test.getBookID(),2);
//        assertNotEquals(test.getBookID(),1);
//    }
//
//    @Test
//    void getStoreID() {
//        assertEquals(test.getStoreID(),1);
//        assertNotEquals(test.getStoreID(),2);
//    }
//
//    @Test
//    void setStoreID() {
//        assertEquals(test.getStoreID(),1);
//        assertNotEquals(test.getStoreID(),2);
//        test.setStoreID(2L);
//        assertEquals(test.getStoreID(),2);
//        assertNotEquals(test.getStoreID(),1);
//    }
//
//    @Test
//    void getQuantity() {
//        assertNotEquals(test.getQuantity(),2);
//        assertEquals(test.getQuantity(),1);
//
//    }
//
//    @Test
//    void setQuantity() {
//        assertNotEquals(test.getQuantity(),2);
//        assertEquals(test.getQuantity(),1);
//        test.setQuantity(2);
//        assertNotEquals(test.getQuantity(),1);
//        assertEquals(test.getQuantity(),2);
//    }
//
//
//    @Test
//    void emptyConstructor() {
//        test = new Stock();
//        assertNull(test.getId());
//        assertEquals(test.getQuantity(),0);
//        assertNull(test.getBookID());
//        assertNull(test.getStoreID());
//    }
//
//
//    @Test
//    void toStringTest() {
//        assertEquals(test.toString(),"Stock{id=1, bookID=1, storeID=1, quantity=1}");
//        assertNotEquals(test.toString(),"");
//
//    }
//}