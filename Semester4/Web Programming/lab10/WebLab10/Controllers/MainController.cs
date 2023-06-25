using WebLab10.DataAbstractionLayer;
using WebLab10.Models;
using System.Web.Mvc;
using System.Net;
using System.Collections.Generic;

namespace WebLab10.Controllers
{
    public class MainController : Controller
    {
        private DAL dataAbstractionLayer = new DAL();

        // GET: Main
        public ActionResult Index()
        {
            return View("LoginView");
        }

        [HttpGet]
        public ActionResult LoginStudent(string username, string password)
        {
            Student studentSelected = dataAbstractionLayer.SelectStudentByUsernameAndPassword(username, password);
            if(studentSelected.Name != null)
            {
                Session["name"] = studentSelected.Name;
                Session["userID"] = studentSelected.UserID;
                //return new HttpStatusCodeResult(HttpStatusCode.OK);
                return RedirectToAction("StudentPage");
            }
            else
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest,"credentials are not correct");
            }
        }

        [HttpGet]
        public ActionResult LoginProfessor(string username, string password){
            Professor professor = dataAbstractionLayer.SelectProfessorByUsernameAndPassword(username,password);
            if(professor != null)
            {
                Session["name"] = professor.Name;
                Session["userID"] = professor.UserID;
                return new HttpStatusCodeResult(HttpStatusCode.OK);
            }
            else
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest, "credentials are not correct");
            }
        }

        [HttpGet]
        public int GetUserID()
        {
            return (int)Session["userID"];
        }

        [HttpGet]
        public string GetName()
        {
            return (string)Session["name"];
        }

        [HttpGet]
        public ActionResult Logout()
        {
            Session["name"] = "";
            Session["userID"] = "";
            return Redirect("Index");

        }

        public ActionResult StudentPage()
        {
            return View("StudentView");
        }

        [HttpGet]
        public ActionResult GetStudentGrades(int studentID)
        {
            List<Grades> grades = dataAbstractionLayer.SelectGradesByStudents(studentID);
            return Json(grades,JsonRequestBehavior.AllowGet);
        }

        public ActionResult ProfessorPage()
        {
            return View("ProfessorView");
        }

        [HttpGet]
        public ActionResult GetGroupsForProfessor(int professorID)
        {
            List<string> groups = dataAbstractionLayer.SelectGroupsByProfessor(professorID);
            return Json(groups, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public ActionResult GetStudentsGradesWithGroupID(string groupID)
        {
            List<Grades> grades = dataAbstractionLayer.SelectStudentsGradesByGroupID(groupID);
            return Json(grades, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult AddGrade(string studentname, string coursename, int mark)
        {
            Student student = dataAbstractionLayer.SelectStudentByName(studentname);
            if(student == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest, "student does not exist");
            }
            int studentID = student.UserID;

            Course course = dataAbstractionLayer.SelectCourseByName(coursename);
            if(course == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest, "course does not exist");
            }
            int courseID = course.CourseID;

            Grades check = dataAbstractionLayer.FindGrade(studentID, courseID);
            if (check == null)
            {
                bool val = dataAbstractionLayer.InsertGrade(courseID, studentID, mark);
                return Json(val, JsonRequestBehavior.AllowGet);
            }
            else
            {
                bool val = dataAbstractionLayer.UpdateGrade(courseID, studentID, mark);
                return Json(val, JsonRequestBehavior.AllowGet);
            }

        }





    }
}