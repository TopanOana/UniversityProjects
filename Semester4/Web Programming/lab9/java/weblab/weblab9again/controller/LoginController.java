package weblab.weblab9again.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import weblab.weblab9again.domain.User;
import weblab.weblab9again.domain.UserSingleton;
import weblab.weblab9again.model.DBManager;

import java.io.IOException;

public class LoginController extends HttpServlet {

    public LoginController(){super();}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        RequestDispatcher requestDispatcher = null;

        DBManager dbManager = new DBManager();
        User user = dbManager.authenticate(username,password);
        UserSingleton userSingleton = UserSingleton.getInstance();
        if (user!=null && userSingleton.getNrUsers()<2){
            requestDispatcher = request.getRequestDispatcher("/success.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if(userSingleton.getNrUsers()==0)
                session.setAttribute("value", "X");
            else
                session.setAttribute("value", "O");
            userSingleton.setNrUsers(userSingleton.getNrUsers()+1);
        }
        else{
            requestDispatcher = request.getRequestDispatcher("/error.jsp");
        }
        requestDispatcher.forward(request,response);
    }

}
