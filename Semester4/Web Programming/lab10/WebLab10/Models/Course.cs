using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebLab10.Models
{
    public class Course
    {
        public int CourseID { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int ProfessorID { get; set; }

    }
}