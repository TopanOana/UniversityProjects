<?php

require_once 'DatabaseUtils.php';
require_once 'Student.php';
require_once 'Professor.php';

class Model {
    private $db;

    public function __construct() {
        $this->db = new DBUtils ();
    }

    public function getStudentByUsernameAndPassword($username, $password){
        $result_set = $this->db->selectStudentByUsernameAndPassword($username,$password);
        if ($result_set == null)
            return null;
        $student = new Student($result_set[0]['userID'], $result_set[0]['name'], $result_set[0]['groupID'], $result_set[0]['username'], $result_set[0]['password']);
        return $student;
    }

    public function getStudentGrades($studentID){
        $result_set = $this->db->selectGradesByStudent($studentID);
        if ($result_set == null)
            return null;
        return $result_set;
    }

    public function getStudentDetails($userID){
        $result_set = $this->db->selectStudentDetails($userID);
        if ($result_set == null)
            return null;
        $student = new Student($result_set[0]['userID'], $result_set[0]['name'], $result_set[0]['groupID'], $result_set[0]['username'], $result_set[0]['password']);
        return $student;
    }

    public function getProfessorByUsernameAndPassword($username, $password){
        $result_set = $this->db->selectProfessorByUsernameAndPassword($username,$password);
        if ($result_set == null)
            return null;
        $professor = new Professor($result_set[0]['userID'], $result_set[0]['name'], $result_set[0]['username'], $result_set[0]['password']);
        return $professor;
    }

    public function getProfessorDetails($userID){
        $result_set = $this->db->selectProfessorDetails($userID);
        if ($result_set == null)
            return null;
        $professor = new Professor($result_set[0]['userID'], $result_set[0]['name'], $result_set[0]['username'], $result_set[0]['password']);
        return $professor;
    }

    public function getStudentsGradesWithGroupID($groupID){
        $result_set = $this->db->selectStudentsGradesByGroupID($groupID);
        if ($result_set == null)
            return null;
        return $result_set;
    }

    public function addGrade($studentname, $coursename, $mark){
        $student_result = $this->db->selectStudentByName($studentname);
        if ($student_result == null)
            return null;
        $studentID = $student_result[0]['userID'];
        $course_result = $this->db->selectCourseByName($coursename);
        if ($course_result == null)
            return null;
        $courseID = $course_result[0]['courseID'];
        $check_exist = $this->db->findGrade($courseID, $studentID);
        if (is_null($check_exist))
            return $this->db->insertGrade($courseID, $studentID, $mark);
        else
            return $this->db->updateGrade($courseID, $studentID, $mark);
    }

    public function getGroupsForProfessor($professorID){
        $result_set = $this->db->selectGroupsByProfessor($professorID);
        if ($result_set == null)
            return null;
        return $result_set;
    }




}

?>
