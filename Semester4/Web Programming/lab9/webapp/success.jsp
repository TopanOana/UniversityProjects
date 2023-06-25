<%@ page import="weblab.weblab9again.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: oanam
  Date: 5/21/2023
  Time: 1:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
    <script src="jquery-3.6.4.js"></script>
    <script language="JavaScript">
        function logout(){
            if(confirm("Are you sure you want to logout?")){
                $.get("LogoutController", function(response){
                    console.log("aici?")
                    window.location.href="index.html";

                })
            }
        }
        function verificaCastigatorul(){
            $.get("TicTacToeController",{
                winner:"true"
            }, function(response) {

                // alert(response)
                if (response.includes("winner") || response.includes("tie")){
                    alert(response)
                    // $("td").off("click");
                    logout();
                }
            })
        }
        function getTable(){
            $.get("TicTacToeController", function(response) {

                console.log("am ajuns???")
                console.log(response)

                for (i = 0; i < 3; i++){
                    for (j = 0; j < 3; j++) {

                        let id = "id" + (i * 3 + j);
                        let value = i * 3 + j;
                        console.log(response[value])
                        console.log($("#" + id))
                        $("#" + id).text(response[value]);
                    }
                }
                verificaCastigatorul();
            })
        }

        $(document).ready(function(){
            $("td").click(function(event){

                let id = event.target.id;
                console.log(id)
                // let number = id[2]
                console.log(event.target.id)
                let number = parseInt(id.match(/\d+/)[0]);
                let row = Math.floor(number/3);
                let column = number%3;
                let value = '<%= session.getAttribute("value") %>';
                console.log("row:" + row);
                console.log("column:"+ column);
                console.log("value:"+value);
                $.post("TicTacToeController", {
                    row:row, column:column,value:value
                }, function(response){
                    verificaCastigatorul()
                    // alert(response);
                    // if (response.includes("winner")){
                    //     $("td").off("click");
                    // }

                })


            });


            let citesteTabelul = function(){
                getTable();
            }
            setInterval(citesteTabelul,750);
        });
    </script>
    <style>
        table, td{
            border: 1px solid blueviolet;
            margin: 3px;
        }
        table{
            height:100%;
            width:100%;
        }
        td{
            width: 30px;
            height:30px;
            text-align: center;
        }
        .board{
            padding: 15px;
            width: 300px;
            height: 300px;

        }
    </style>
</head>
<body>
<%! User user; %>
<%  user = (User) session.getAttribute("user");
    if (user != null) {
        out.println("Welcome " + user.getUsername());
    }
    else{
        out.println("null user");
    }
%>

<button onclick="logout()">Logout</button>

<br>
<%
    out.println("<div class=\"board\">");
    out.println("<p>Board</p>");
    out.println("<table>");

    for(int i =0 ;i<3;i++){
        out.println("<tr>");
        for(int j=0;j<3;j++){
            out.println("<td id=\"id"+(i*3+j)+"\">&nbsp &nbsp</td>");
        }

        out.println("</tr>");
    }

    out.println("</table>");
    out.println("</div>");
%>

</body>
</html>
