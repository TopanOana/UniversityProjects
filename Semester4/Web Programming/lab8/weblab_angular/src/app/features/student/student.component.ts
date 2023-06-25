import {AfterViewInit, Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {ServiceApi} from "../../common/service-api.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements AfterViewInit, OnInit {

  displayedColumns = ['nr', 'course', 'mark'];
  dataSource = new MatTableDataSource();
  studentID?: number;
  studentName?: string;

  constructor(private service: ServiceApi, private router: Router) {
  }

  ngOnInit() {
    this.service.getUserID().subscribe((val: number) => {
      this.studentID = val;
      console.log("aici student:" + this.studentID)
      this.service.getStudentGrades(this.studentID!).subscribe((data) => {
        this.dataSource.data = data;
      });
    });
    this.service.getName().subscribe((val: string) => {
      this.studentName = val;
    });
    console.log(this.studentID)

  }

  ngAfterViewInit() {

  }

  logout() {
    this.service.logout().subscribe((p) => {
      this.router.navigateByUrl('login');
    })
  }
}
