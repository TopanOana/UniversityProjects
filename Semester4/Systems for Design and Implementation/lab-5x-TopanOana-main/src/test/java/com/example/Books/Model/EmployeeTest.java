package com.example.Books.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private Employee test;

    @BeforeEach
    void setUp() {
        test = new Employee("fn","ln","11",1,true);
        test.setId(1L);
    }

//    @Test
//    void getStore() {
//        assert
//    }
//
//    @Test
//    void setStore() {
//    }

    @Test
    void setId() {
        assertEquals(test.getId(),1);
        test.setId(2L);
        assertNotEquals(test.getId(),1);
        assertEquals(test.getId(),2);
        test.setId(1L);
        assertEquals(test.getId(),1);
    }

    @Test
    void getFirstName() {
        assertEquals(test.getFirstName(),"fn");
        assertNotEquals(test.getFirstName(),"fn1");
    }

    @Test
    void setFirstName() {
        assertEquals(test.getFirstName(),"fn");
        assertNotEquals(test.getFirstName(),"fn1");
        test.setFirstName("fn1");
        assertEquals(test.getFirstName(),"fn1");
        assertNotEquals(test.getFirstName(),"fn");
    }

    @Test
    void getLastName() {
        assertEquals(test.getLastName(),"ln");
        assertNotEquals(test.getLastName(),"ln1");
    }

    @Test
    void setLastName() {
        assertEquals(test.getLastName(),"ln");
        assertNotEquals(test.getLastName(),"ln1");
        test.setLastName("ln1");
        assertEquals(test.getLastName(),"ln1");
        assertNotEquals(test.getLastName(),"ln");
    }

    @Test
    void getPhoneNumber() {
        assertEquals(test.getPhoneNumber(),"11");
        assertNotEquals(test.getPhoneNumber(),"22");
    }

    @Test
    void setPhoneNumber() {
        assertEquals(test.getPhoneNumber(),"11");
        assertNotEquals(test.getPhoneNumber(),"22");
        test.setPhoneNumber("22");
        assertEquals(test.getPhoneNumber(),"22");
        assertNotEquals(test.getPhoneNumber(),"11");
    }

    @Test
    void getSalary() {
        assertEquals(test.getSalary(),1);
        assertNotEquals(test.getSalary(),10);
    }

    @Test
    void setSalary() {
        assertEquals(test.getSalary(),1);
        assertNotEquals(test.getSalary(),10);
        test.setSalary(10);
        assertEquals(test.getSalary(),10);
        assertNotEquals(test.getSalary(),1);
    }

    @Test
    void isFullTime() {
        assertTrue(test.isFullTime());

    }

    @Test
    void setFullTime() {
        assertTrue(test.isFullTime());
        test.setFullTime(false);
        assertFalse(test.isFullTime());
    }

    @Test
    void constructorEmpty() {
        test = new Employee();
        assertNull(test.getId());
        assertNull(test.getFirstName());
        assertNull(test.getLastName());
        assertNull(test.getPhoneNumber());
        assertEquals(test.getSalary(),0);
    }

    @Test
    void toStringTest() {
        assertEquals(test.toString(),"Employee{id=1, firstName='fn', lastName='ln', phoneNumber='11', salary=1, fullTime=true}");
        assertNotEquals(test.toString(),"");
    }
}