package com.example.Books.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class AdminService {
    @Autowired
    Environment environment;

    public void bulkDelete() throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(
                    environment.getRequiredProperty("spring.datasource.url"),
                    environment.getRequiredProperty("spring.datasource.username"),
                    environment.getRequiredProperty("spring.datasource.password")
            );
            List<String> statements = Arrays.asList(
//                    "SET REFERENTIAL_INTEGRITY FALSE;",
                    "SET session_replication_role = replica;",
                    "TRUNCATE TABLE STOCK;",
                    "TRUNCATE TABLE EMPLOYEE;",
                    "TRUNCATE TABLE STORE;",
                    "TRUNCATE TABLE BOOK;",
                    "SET session_replication_role = DEFAULT;"
//                    "SET REFERENTIAL_INTEGRITY TRUE;"
            );
            statements.forEach(stmt -> {
                try{
                    Statement statement = connection.createStatement();
                    statement.execute(stmt);
                }
                catch(SQLException e){
                    throw new RuntimeException(e);
                }
            });
            connection.close();
        }
        catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public void populateBookDB(){
        try{
            Connection connection = DriverManager.getConnection(
                    environment.getRequiredProperty("spring.datasource.url"),
                    environment.getRequiredProperty("spring.datasource.username"),
                    environment.getRequiredProperty("spring.datasource.password")
            );
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("sql_scripts/newInsertBooks.sql")));
//            Scanner scanner = new Scanner(new BufferedReader(new FileReader("sql_scripts/populateBooks.sql")));
            scanner.useDelimiter(";");

            while(scanner.hasNext()){
                String stmt = scanner.next();
                System.out.println(stmt);
                Statement statement = connection.createStatement();
                statement.execute(stmt);
            }
            connection.close();

        }catch(SQLException | FileNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }

    public void populateStoreDB(){
        try{
            Connection connection = DriverManager.getConnection(
                    environment.getRequiredProperty("spring.datasource.url"),
                    environment.getRequiredProperty("spring.datasource.username"),
                    environment.getRequiredProperty("spring.datasource.password")
            );
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("sql_scripts/populateStores.sql")));
            scanner.useDelimiter(";");

            while(scanner.hasNext()){
                String stmt = scanner.next();
                System.out.println(stmt);
                Statement statement = connection.createStatement();
                statement.execute(stmt);
            }
            connection.close();

        }catch(SQLException | FileNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }

    public void populateEmployeeDB(){
        try{
            Connection connection = DriverManager.getConnection(
                    environment.getRequiredProperty("spring.datasource.url"),
                    environment.getRequiredProperty("spring.datasource.username"),
                    environment.getRequiredProperty("spring.datasource.password")
            );
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("sql_scripts/populateEmployees.sql")));
            scanner.useDelimiter(";");

            while(scanner.hasNext()){
                String stmt = scanner.next();
                System.out.println(stmt);
                Statement statement = connection.createStatement();
                statement.execute(stmt);
            }
            connection.close();

        }catch(SQLException | FileNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }

    public void populateStockDB(){
        try{
            Connection connection = DriverManager.getConnection(
                    environment.getRequiredProperty("spring.datasource.url"),
                    environment.getRequiredProperty("spring.datasource.username"),
                    environment.getRequiredProperty("spring.datasource.password")
            );
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("sql_scripts/populateStocks.sql")));
            scanner.useDelimiter(";");

            while(scanner.hasNext()){
                String stmt = scanner.next();
                System.out.println(stmt);
                Statement statement = connection.createStatement();
                statement.execute(stmt);
            }
            connection.close();

        }catch(SQLException | FileNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }
}
