<?php
    session_start();
    error_log(session_id());



    function loginStudent($userID, $name){
//        echo $userID . $name;
        $_SESSION['userID'] = $userID;
        $_SESSION['name'] = $name;
//        error_log($userID);
        echo
        return $_SESSION['name'];
    }

    function loginProfessor($userID, $name){

        $_SESSION['userID'] = $userID;
        $_SESSION['name'] = $name;
        echo $name;
        return $name;

    }

    function getUserID(){
//        session_start();
        echo $_SESSION['userID'];
        $value = $_SESSION['userID'];
        return $value;
    }

    function getName(){
//        session_start();
        echo $_SESSION['name'];
        $value = $_SESSION['name'];
        return $value;
    }

    function logout(){
        if(isset($_SESSION['userID']))
            unset($_SESSION['userID']);
        if(isset($_SESSION['name']))
            unset($_SESSION['name']);
        session_destroy();
    }

    function callOne(){
        if(isset($_GET["action"]) && $_GET["action"] == "getName") {
            return getName();
        }
        if(isset($_GET["action"]) && $_GET["action"] == "getUserID") {
            return getUserID();
        }
        if(isset($_GET["action"]) && $_GET["action"] == "loginStudent") {
            loginStudent($_GET['userID'], $_GET['name']);
        }
        if(isset($_GET["action"]) && $_GET["action"] == "loginProfessor") {
            loginProfessor($_GET['userID'], $_GET['name']);
        }
        if(isset($_GET["action"]) && $_GET["action"] == "logout") {
            logout();
        }

    }

    callOne();

?>

