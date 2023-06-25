import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ServiceApi} from "../../common/service-api.service";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-professor',
  templateUrl: './professor.component.html',
  styleUrls: ['./professor.component.css'],
  providers: [MatPaginator, MatSnackBar]
})
export class ProfessorComponent implements OnInit, AfterViewInit {

  professorName?: string;
  professorID?: number;
  groups: any;
  group: any;
  dataSource = new MatTableDataSource();
  displayedColumns = ['nr', 'student', 'course', 'mark'];
  length = 0;

  studentname = '';
  coursename = '';
  mark = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }


  constructor(private service: ServiceApi, private router: Router, paginator: MatPaginator, private snackbar: MatSnackBar) {
    this.paginator = paginator;

  }

  ngOnInit(): void {

    this.service.getUserID().subscribe((val) => {
      this.professorID = val;
      this.service.getGroupsForProfessor(this.professorID!).subscribe((groups) => {
        this.groups = groups;
        console.log(groups)
      })
    })
    this.service.getName().subscribe((val) => {
      this.professorName = val;
    })
  }


  logout() {
    this.service.logout().subscribe((p) => {
      this.router.navigateByUrl('login');
    })
  }

  showGradesTable(gr: any) {
    console.log(this.group);
    console.log(gr);
    this.service.getStudentsGradesWithGroupID(gr.groupID).subscribe((data) => {
      this.dataSource.data = data;
      this.length = data.length;

    })
  }

  addGrade() {
    if (this.studentname && this.coursename && this.mark) {
      this.service.addGrade(this.studentname, this.coursename, this.mark).subscribe((val) => {
        this.snackbar.open("grade added to the database", 'close', {
          verticalPosition: 'top',
          horizontalPosition: 'center'
        })
      }, error => {
        this.snackbar.open("an error has occurred", 'close', {
          verticalPosition: 'top',
          horizontalPosition: 'center'
        })
      })
    } else {
      this.snackbar.open('complete all the fields for adding a grade', 'close', {
        verticalPosition: 'top',
        horizontalPosition: 'center'

      })
    }
  }
}
