package weblab.weblab9again.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import weblab.weblab9again.domain.Table;
import weblab.weblab9again.domain.User;
import weblab.weblab9again.domain.UserSingleton;

import java.io.IOException;

public class TicTacToeController extends HttpServlet {


    private Table table = new Table();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if (request.getParameter("winner")==null){
            response.setContentType("text/html");
            char[][] aux = table.getTable();
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<3;i++){
                for (int j=0;j<3;j++)
                    stringBuilder.append(aux[i][j]);
            }
            response.getWriter().println(stringBuilder.toString());
        }
        else{
            response.setContentType("text/html");
            char winner = table.checkForWinner();
            if (winner!=' '){
                response.getWriter().println("winner is: " + winner);
                if(UserSingleton.getInstance().getNrUsers()<=1){
                    table.clearTable();
                }
//                table.clearTable();
            }
            else{
                boolean checkTie = table.checkForTie();
                if(checkTie){
                    response.getWriter().println("it ends in a tie");
                    if(UserSingleton.getInstance().getNrUsers()<=1){
                        table.clearTable();
                    }
//                    table.clearTable();
                }
                else{
                    response.getWriter().println("the game is afoot");
                }
            }
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int row = Integer.parseInt(request.getParameter("row"));
        int column = Integer.parseInt(request.getParameter("column"));
        char value = request.getParameter("value").charAt(0);

        try{
            if(UserSingleton.getInstance().getCurrentUser()==0 && value=='X'){
                table.putInPlace(row, column, value);
                UserSingleton.getInstance().setCurrentUser(1);
            }
            else {
                if (UserSingleton.getInstance().getCurrentUser() == 1 && value == 'O') {
                    table.putInPlace(row, column, value);
                    UserSingleton.getInstance().setCurrentUser(0);
                } else {
                    response.setContentType("text/html");
                    response.getWriter().println("it is not your turn.");
                    response.getWriter().flush();
                }
            }
            response.setContentType("text/html");
//            char winner = table.checkForWinner();
//            if(winner!=' '){
//                response.getWriter().println("winner is: " + winner);
//                response.getWriter().flush();
//            }
//            else{
//                if (table.checkForTie())
//                    response.getWriter().println("match is tied");
//                else response.getWriter().println("match goes on");
//                response.getWriter().flush();
//            }
        }catch(Exception ex){
            response.setContentType("text/html");
            response.getWriter().println("spot already taken");
            response.getWriter().flush();
        }
    }


}
