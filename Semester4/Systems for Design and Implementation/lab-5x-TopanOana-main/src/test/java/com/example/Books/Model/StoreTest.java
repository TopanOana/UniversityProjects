package com.example.Books.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    private Store test;
    @BeforeEach
    void setUp() {
        test = new Store("sn","ad","cn",10,12);
        test.setId(1L);
    }

    @Test
    void getStoreName() {
        assertEquals(test.getStoreName(),"sn");
        assertNotEquals(test.getStoreName(),"sn1");
    }

    @Test
    void setStoreName() {
        assertEquals(test.getStoreName(),"sn");
        assertNotEquals(test.getStoreName(),"sn1");
        test.setStoreName("sn1");
        assertEquals(test.getStoreName(),"sn1");
        assertNotEquals(test.getStoreName(),"sn");
    }

    @Test
    void getAddress() {
        assertEquals(test.getAddress(),"ad");
        assertNotEquals(test.getAddress(),"ad1");
    }

    @Test
    void setAddress() {
        assertEquals(test.getAddress(),"ad");
        assertNotEquals(test.getAddress(),"ad1");
        test.setAddress("ad1");
        assertEquals(test.getAddress(),"ad1");
        assertNotEquals(test.getAddress(),"ad");
    }

    @Test
    void getContactNumber() {
        assertEquals(test.getContactNumber(),"cn");
        assertNotEquals(test.getContactNumber(),"cn1");
    }

    @Test
    void setContactNumber() {
        assertEquals(test.getContactNumber(),"cn");
        assertNotEquals(test.getContactNumber(),"cn1");
        test.setContactNumber("cn1");
        assertEquals(test.getContactNumber(),"cn1");
        assertNotEquals(test.getContactNumber(),"cn");
    }

    @Test
    void getOpeningHour() {
        assertEquals(test.getOpeningHour(),10);
        assertNotEquals(test.getOpeningHour(),12);
    }

    @Test
    void setOpeningHour() {
        assertEquals(test.getOpeningHour(),10);
        assertNotEquals(test.getOpeningHour(),12);
        test.setOpeningHour(12);
        assertEquals(test.getOpeningHour(),12);
        assertNotEquals(test.getOpeningHour(),10);
    }

    @Test
    void getClosingHour() {
        assertEquals(test.getClosingHour(),12);
        assertNotEquals(test.getClosingHour(),13);
    }

    @Test
    void setClosingHour() {
        assertEquals(test.getClosingHour(),12);
        assertNotEquals(test.getClosingHour(),13);
        test.setClosingHour(13);
        assertEquals(test.getClosingHour(),13);
        assertNotEquals(test.getClosingHour(),12);
    }


    @Test
    void emptyConstructor() {
        test = new Store();
        assertNull(test.getAddress());
        assertNull(test.getStoreName());
        assertNull(test.getContactNumber());
        assertEquals(test.getClosingHour(),0);
        assertEquals(test.getOpeningHour(),0);
    }

    @Test
    void toStringTest() {
        assertEquals(test.toString(),"Store{id=1, storeName='sn', address='ad', contactNumber='cn', openingHour=10, closingHour=12}");
        assertNotEquals(test.toString(),"");
    }
}