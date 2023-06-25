<%@ page import="weblab.weblab9again.domain.UserSingleton" %><%--
  Created by IntelliJ IDEA.
  User: oanam
  Date: 5/21/2023
  Time: 1:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Failure</title>
</head>
<body>
Login failed. :(
<br>
<%
    int nrUsers = UserSingleton.getInstance().getNrUsers();
    if(nrUsers>=2){
        out.println("There are too many users logged in, please wait");
    }
    else{
        out.println("Your credentials are not valid");
    }
%>
</body>
</html>
