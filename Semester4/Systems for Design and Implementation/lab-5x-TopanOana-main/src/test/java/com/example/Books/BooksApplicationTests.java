package com.example.Books;

import com.example.Books.Service.BookService;
import com.example.Books.Service.EmployeeService;
import com.example.Books.Service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BooksApplicationTests {
	@Autowired
	BookService bookService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	StoreService storeService;


	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateBook(){

	}

}
