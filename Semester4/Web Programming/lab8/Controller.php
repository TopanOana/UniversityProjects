<?php
session_start();

require_once 'model.php';
require_once 'view.php';

class Controller
{
    private $view;
    private $model;

    public function __construct(){

        $this->model = new Model();
        $this->view = new View();
    }

    public function service() {
        if (isset($_GET['action']) && !empty($_GET['action'])) {
            if ($_GET['action'] == "getStudentByUsernameAndPassword") {
                $this->{$_GET['action']}($_GET['username'], $_GET['password']);
            }
            else if ($_GET['action'] == "getProfessorByUsernameAndPassword") {
                $this->{$_GET['action']}($_GET['username'], $_GET['password']);
            }
            else if ($_GET['action'] == "getStudentGrades") {
                $this->{$_GET['action']}($_GET['studentID']);
            }
            else if ($_GET['action'] == "getStudentDetails") {
                $this->{$_GET['action']}($_GET['studentID']);
            }
            else if ($_GET['action'] == "getProfessorDetails") {
                $this->{$_GET['action']}($_GET['professorID']);
            }
            else if ($_GET['action'] == "getStudentsGradesWithGroupID") {
                $this->{$_GET['action']}($_GET['groupID']);
            }
            else if ($_GET['action'] == "getGroupsForProfessor") {
                $this->{$_GET['action']}($_GET['professorID']);
            }
            else if ($_GET['action'] == "addGrade") {
                $this->{$_GET['action']}($_GET['studentname'], $_GET['coursename'], $_GET['mark']);
            }
            else {
                $this->{$_GET['action']}();
            }
        }
    }


    public function getStudentByUsernameAndPassword($username, $password){
        $student = $this->model->getStudentByUsernameAndPassword($username, $password);
        return $this->view->output($student);
    }

    public function getProfessorByUsernameAndPassword($username, $password){
        $professor = $this->model->getProfessorByUsernameAndPassword($username, $password);
        return $this->view->output($professor);
    }

    public function getStudentGrades($studentID){
        $grades = $this->model->getStudentGrades($studentID);
        return $this->view->output($grades);
    }

    public function getStudentDetails($studentID){
        $student = $this->model->getStudentDetails($studentID);
        return $this->view->output($student);
    }

    public function getProfessorDetails($professorID){
        $professor = $this->model->getProfessorDetails($professorID);
        return $this->view->output($professor);
    }

    public function getStudentsGradesWithGroupID($groupID){
        $grades = $this->model->getStudentsGradesWithGroupID($groupID);
        return $this->view->output($grades);
    }

    public function getGroupsForProfessor($professorID){
        $groups = $this->model->getGroupsForProfessor($professorID);
        return $this->view->output($groups);
    }

    public function addGrade($studentname, $coursename, $mark){
        $row = $this->model->addGrade($studentname, $coursename, $mark);
        return $this->view->output($row);
    }
//
//
//    function loginStudent($userID, $name){
//        echo $userID . $name;
//        $_SESSION['userID'] = $userID;
//        $_SESSION['name'] = $name;
//    }
//
//    function loginProfessor($userID, $name){
//
//        $_SESSION['userID'] = $userID;
//        $_SESSION['name'] = $name;
//    }
//
//    function getUserID(){
//        return json_encode($_SESSION['userID']);
//    }
//
//    function getName(){
//        echo $_SESSION['name'];
//        return json_encode($_SESSION['name']);
//    }


}

$controller = new Controller();

$controller->service();

?>

