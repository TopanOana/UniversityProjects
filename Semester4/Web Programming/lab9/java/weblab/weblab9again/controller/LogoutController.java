package weblab.weblab9again.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import weblab.weblab9again.domain.UserSingleton;

import java.io.IOException;

public class LogoutController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        UserSingleton.getInstance().setNrUsers(UserSingleton.getInstance().getNrUsers()-1);
//        request.getRequestDispatcher("login.html");
    }
}
