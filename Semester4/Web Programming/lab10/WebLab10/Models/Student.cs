using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebLab10.Models
{
    public class Student
    {
        public int UserID { get; set; }
        public string Name { get; set; }
        public string Username{ get; set; }
        public string Password { get; set; }
        public string GroupID{ get; set; }
    }
}