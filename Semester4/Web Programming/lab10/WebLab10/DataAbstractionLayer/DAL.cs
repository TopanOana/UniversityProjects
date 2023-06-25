using Microsoft.Ajax.Utilities;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text.RegularExpressions;
using System.Web;
using WebLab10.Models;

namespace WebLab10.DataAbstractionLayer
{
    public class DAL
    {
        string connectionString = "server=localhost;uid=oana;pwd=oana;database=weblabs;";
        public Student SelectStudentByUsernameAndPassword(string username, string password)
        {
            MySqlConnection connection;
            
            Student student = null;
            try
            {
                connection = new MySql.Data.MySqlClient.MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "select * from users where username='" + username + "' and password='" + password + "' and groupID is not null";
                MySqlDataReader mySqlDataReader  = command.ExecuteReader();

                mySqlDataReader.Read();
                student = new Student();
                student.UserID = mySqlDataReader.GetInt32("userID");
                student.Name = mySqlDataReader.GetString("name");
                student.Username = mySqlDataReader.GetString("username");
                student.Password = mySqlDataReader.GetString("password");
                student.GroupID = mySqlDataReader.GetString("groupID");
                mySqlDataReader.Close();
                connection.Close();
            }
            catch (Exception ex) {
                Console.WriteLine(ex.Message);
            }
            return student;
        }

        public Student SelectStudentDetails(int userID)
        {
            MySqlConnection connection;
           
            Student student = null;

            try{
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "select * from users where userID=" + userID + " and groupID is not null";
                MySqlDataReader mySqlDataReader = command.ExecuteReader();

                mySqlDataReader.Read();
                student = new Student();
                student.UserID = mySqlDataReader.GetInt32("userID");
                student.Name = mySqlDataReader.GetString("name");
                student.Username = mySqlDataReader.GetString("username");
                student.Password = mySqlDataReader.GetString("password");
                student.GroupID = mySqlDataReader.GetString("groupID");
                mySqlDataReader.Close();
                connection.Close();
            }
            catch(Exception ex) { Console.WriteLine(ex.Message); }
            return student;
        }

        public Professor SelectProfessorByUsernameAndPassword(string username, string password)
        {
            MySqlConnection connection;
           
            Professor professor = null;
            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "select * from users where username='" + username + "' and password='" + password + "' and groupID is null";
                MySqlDataReader mySqlDataReader = command.ExecuteReader();

                mySqlDataReader.Read();
                professor = new Professor();
                professor.UserID = mySqlDataReader.GetInt32("userID");
                professor.Name = mySqlDataReader.GetString("name");
                professor.Username = mySqlDataReader.GetString("username");
                professor.Password = mySqlDataReader.GetString("password");

                mySqlDataReader.Close();
                connection.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return professor;
        }

        public Professor SelectProfessorDetails(int userID)
        {
            MySqlConnection connection;
            Professor professor = null;
            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "select * from users where userID="+ userID+ "and groupID is null";
                MySqlDataReader mySqlDataReader = command.ExecuteReader();

                mySqlDataReader.Read();
                professor = new Professor();
                professor.UserID = mySqlDataReader.GetInt32("userID");
                professor.Name = mySqlDataReader.GetString("name");
                professor.Username = mySqlDataReader.GetString("username");
                professor.Password = mySqlDataReader.GetString("password");

                mySqlDataReader.Close();
                connection.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return professor;
        }


        public List<Grades> SelectStudentsGradesByGroupID(string groupID)
        {
            MySqlConnection connection;
            
            List<Grades> grades = new List<Grades>();

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "SELECT users.name as studentName, courses.name as courseName, grades.mark as mark FROM users INNER JOIN grades on grades.studentID=users.userID INNER JOIN courses on courses.courseID = grades.courseID WHERE groupID = " + groupID;
                MySqlDataReader mySqlDataReader = command.ExecuteReader();

                while (mySqlDataReader.Read())
                {
                    Grades grade = new Grades();
                    grade.StudentName = mySqlDataReader.GetString("studentName");
                    grade.CourseName = mySqlDataReader.GetString("courseName");
                    grade.Mark = mySqlDataReader.GetInt32("mark");
                    grades.Add(grade);
                }
                mySqlDataReader.Close();
                connection.Close();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return grades;
        }

        public List<string> SelectGroupsByProfessor(int professorID)
        {
            MySqlConnection connection;
            List<string> groups = new List<string>();
            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "SELECT DISTINCT users.groupID FROM users INNER JOIN grades on grades.studentID=users.userID INNER  JOIN courses on courses.courseID=grades.courseID WHERE courses.professorID=" + professorID + " ORDER BY users.groupID";
                MySqlDataReader mySqlDataReader = command.ExecuteReader();

                while (mySqlDataReader.Read())
                {
                    string group = mySqlDataReader.GetString("groupID");
                    groups.Add(group);
                }
                mySqlDataReader.Close();
                connection.Close();

            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return groups;
        }

        public List<Grades> SelectGradesByStudents(int studentID)
        {
            MySqlConnection connection;
            List<Grades> grades = new List<Grades>();

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "select users.name as studentName, courses.name as courseName, grades.mark as mark FROM users INNER JOIN grades on grades.studentID=users.userID INNER JOIN courses on courses.courseID = grades.courseID WHERE studentID = " + studentID;
                MySqlDataReader mySqlDataReader = command.ExecuteReader();

                while (mySqlDataReader.Read())
                {
                    Grades grade = new Grades();
                    grade.StudentName = mySqlDataReader.GetString("studentName");
                    grade.CourseName = mySqlDataReader.GetString("courseName");
                    grade.Mark = mySqlDataReader.GetInt32("mark");
                    grades.Add(grade);
                }
                mySqlDataReader.Close();
                connection.Close();
            }
            catch(Exception ex) { Console.WriteLine(ex.Message);}
            return grades;           

        }

        public Student SelectStudentByName(string studentName)
        {
            MySqlConnection connection;
            Student student = null;
            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "SELECT * FROM users WHERE name='" + studentName + "' AND groupID IS NOT NULL";

                MySqlDataReader mySqlDataReader = command.ExecuteReader();
                mySqlDataReader.Read();
                student = new Student();
                student.UserID = mySqlDataReader.GetInt32("userID");
                student.Name = mySqlDataReader.GetString("name");
                student.Username = mySqlDataReader.GetString("username");
                student.Password = mySqlDataReader.GetString("password");
                student.GroupID = mySqlDataReader.GetString("groupID");
                mySqlDataReader.Close();
                connection.Close();

            }
            catch (Exception ex) { Console.WriteLine(ex.Message); }
            return student;
        }

        public Course SelectCourseByName(string courseName)
        {
            MySqlConnection connection;
            Course course = null;
            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "SELECT * FROM courses WHERE name='" + courseName + "'";
                MySqlDataReader mySqlDataReader = command.ExecuteReader();
                course = new Course();
                mySqlDataReader.Read();
                course.CourseID = mySqlDataReader.GetInt32("courseID");
                course.Name = mySqlDataReader.GetString("name");
                course.Description = mySqlDataReader.GetString("description");
                course.ProfessorID = mySqlDataReader.GetInt32("professorID");
                mySqlDataReader.Close();
                connection.Close();
            }
            catch (Exception ex) { Console.WriteLine(ex.Message); }
            return course;
        }

        public bool InsertGrade(int courseID, int studentID, int mark)
        {
            MySqlConnection connection;
            bool value = false;
            try
            {
                connection = new MySqlConnection(); 
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "INSERT INTO grades(studentID, courseID, mark) VALUES(" + studentID + "," + courseID + "," + mark + ")";

                int rowsAffected = command.ExecuteNonQuery();
                if (rowsAffected > 0)
                {
                    value = true;
                }
                connection.Close();
            }
            catch (Exception ex) { Console.WriteLine(ex.Message);}
            return value;
        }

        public Grades FindGrade(int studentID, int courseID)
        {
            MySqlConnection connection;
            Grades grade = null;
            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "Select * from grades where studentID=" + studentID + " and courseID=" + courseID;
                MySqlDataReader mySqlDataReader = command.ExecuteReader();
                mySqlDataReader.Read();
                grade = new Grades();
                grade.Mark = mySqlDataReader.GetInt32("mark");
                mySqlDataReader.Close();
                connection.Close();
            }
            catch (Exception ex) { Console.WriteLine(ex.Message); }
            return grade;
        }

        public bool UpdateGrade(int courseID, int studentID, int mark)
        {
            MySqlConnection connection;
            bool val = false; ;
            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();
                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "UPDATE grades SET mark=" + mark + " WHERE" +
                    " studentID=" + studentID + " and courseID=" + courseID;
                int rowsAffected = command.ExecuteNonQuery();
                if(rowsAffected > 0)
                {
                    val = true;
                }
                connection.Close();

            }catch (Exception ex) { Console.WriteLine(ex.Message); }
            return val;
        }




    }
}