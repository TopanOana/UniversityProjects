<?php
class DBUtils {
    private $host = 'localhost';
    private $db   = 'weblabs';
    private $user = 'oana';
    private $pass = 'oana';
    private $charset = 'utf8';

    private $pdo;
    private $error;

    public function __construct () {
        $dsn = "mysql:host=$this->host;dbname=$this->db;charset=$this->charset";
        $opt = array(PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES   => false);
        try {
            $this->pdo = new PDO($dsn, $this->user, $this->pass, $opt);
        } // Catch any errors
        catch(PDOException $e){
            $this->error = $e->getMessage();
            echo "Error connecting to DB: " . $this->error;
        }
    }

    public function selectStudentDetails($userID) {
        $stmt = $this->pdo->prepare("SELECT * FROM users WHERE userID=? AND groupID IS NOT NULL");
        $stmt->execute([$userID]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function selectStudentByUsernameAndPassword($username, $password){
        $stmt = $this->pdo->prepare("SELECT * FROM users WHERE username=? AND password=? AND groupID IS NOT NULL");
        $stmt->execute([$username, $password]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function selectProfessorByUsernameAndPassword($username, $password){
        $stmt = $this->pdo->prepare("SELECT * FROM users WHERE username=? AND password=? AND groupID IS NULL");
        $stmt->execute([$username, $password]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function selectProfessorDetails($userID) {
        $stmt = $this->pdo->prepare("SELECT * FROM users WHERE userID=? AND groupID IS NULL");
        $stmt->execute([$userID]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function selectStudentsGradesByGroupID($groupID) {
        $stmt = $this->pdo->prepare("SELECT users.name as studentName, courses.name as courseName, grades.mark as mark FROM users INNER JOIN grades on grades.studentID=users.userID 
                INNER  JOIN courses on courses.courseID=grades.courseID WHERE groupID=?");
        $stmt->execute([$groupID]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function selectGroupsByProfessor($professorID) {
        $stmt = $this->pdo->prepare("SELECT DISTINCT users.groupID FROM users INNER JOIN grades on grades.studentID=users.userID 
                INNER  JOIN courses on courses.courseID=grades.courseID WHERE courses.professorID=? ORDER BY users.groupID");
        $stmt->execute([$professorID]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }


    public function selectGradesByStudent($studentID){
        $stmt = $this->pdo->prepare("SELECT grades.mark, courses.name FROM grades INNER JOIN courses
            on grades.courseID=courses.courseID WHERE grades.studentID=?");
        $stmt->execute([$studentID]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function selectStudentByName($studentname){
        $stmt = $this->pdo->prepare("SELECT * FROM users WHERE name=? AND groupID IS NOT NULL");
        $stmt->execute([$studentname]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function selectCourseByName($coursename){
        $stmt = $this->pdo->prepare("SELECT * FROM courses WHERE name=?");
        $stmt->execute([$coursename]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function insertGrade($courseID, $studentID, $mark){
        $affected_rows = $this->pdo->prepare("INSERT INTO grades(studentID, courseID, mark) VALUES( :studentID, :courseID, :mark);");
        $affected_rows->execute([':studentID' => $studentID, ':courseID' => $courseID, ':mark' => $mark]);
        return $affected_rows;
    }

    public function findGrade($courseID, $studentID){
        $stmt = $this->pdo->prepare("SELECT * FROM grades WHERE studentID=:studentID AND courseID=:courseID");
        $stmt->execute([':studentID' => $studentID, ':courseID' => $courseID]);
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if (count($rows)>0){
            return $rows;
        }
        else
            return null;
    }

    public function updateGrade($courseID, $studentID, $mark){
        $stmt = $this->pdo->prepare("UPDATE grades SET mark=:mark WHERE studentID=:studentID AND courseID=:courseID");
        $stmt->execute([':studentID' => $studentID, ':courseID' => $courseID, ':mark'=>$mark]);
        return $stmt;
    }

}


?>