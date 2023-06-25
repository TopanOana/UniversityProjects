package weblab.weblab9again.model;

import weblab.weblab9again.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBManager {
    private Statement statement;

    public DBManager(){
        connect();
    };


    public void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lab8", "oana", "oana");
            statement = connection.createStatement();
        }
        catch(Exception ex){
            System.out.println("error connecting: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password){
        ResultSet resultSet;
        User user = null;
        try{
            resultSet = statement.executeQuery("SELECT * FROM `users` WHERE username='" + username +"' and password='"+password+"'");
            if(resultSet.next()){
                user = new User(resultSet.getInt("userID"), resultSet.getString("username"), resultSet.getString("password"));
            }
            resultSet.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return  user;
    }

}
