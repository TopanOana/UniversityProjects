import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatSort, Sort} from "@angular/material/sort";
import {ApiService} from "../../../../common/api.service";
import {Router} from "@angular/router";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {Employee, EmployeeTable} from "./Models/employees.models";
import {MatPaginator, PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-overview-employees',
  templateUrl: './overview-employees.component.html',
  styleUrls: ['./overview-employees.component.css'],
  providers: [MatPaginator, MatSort]
})
export class OverviewEmployeesComponent implements AfterViewInit, OnInit {

  displayedColumns = ['id', 'firstName', 'lastName', 'phoneNumber', 'salary', 'fullTime', 'username']

  dataSource = new MatTableDataSource();
  totalEmployees: number;

  pageIndex=0;
  pageFirst=true;
  pageLast=false;
  pageSize=5;

  nrPages=0;

  column='';
  order='';
  loggedIn:boolean;


  @ViewChild(MatSort) sort: MatSort;
  // @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service: ApiService, private router: Router, private liveAnnouncer: LiveAnnouncer, /*: MatPaginator,*/ sort : MatSort) {
    // this.paginator = paginator;
    this.sort = sort;
    this.totalEmployees = 0;
    this.loggedIn=false;
  }

  ngOnInit():void{
    // console.log("aiciiii");
  }

  ngAfterViewInit() {
    this.getEmployeesPaged(this.pageIndex, this.pageSize);
    if(this.service.token.length>0)
      this.loggedIn=true;
  }

  getEmployeesPaged(page:number, size:number){
    let column, order;
    if(this.column!='' && this.order!=''){
      column=this.column;
      order=this.order;
    }
    this.service.getEmployees(page, size,column,order).subscribe((data:EmployeeTable)=>{
      this.dataSource.data = data['content'];
      this.totalEmployees = data['totalElements'];
      this.nrPages = data['totalPages'];
      if(page>0)
        this.pageFirst=false;
      else this.pageFirst=true;
      if(page==this.nrPages-1)
        this.pageLast=true;
      else this.pageLast=false;

    })
  }

  goBackToHome() {
    this.router.navigateByUrl("")
  }


  goToAddEmployee() {
    this.router.navigateByUrl(`employees/add`)
  }

  goToEmployeeDetails(employeerow: Employee){
    // console.log(employeerow.id)
    let employeeID = employeerow.id
    this.router.navigateByUrl(`employees/${employeeID}`)
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.column = sortState.active;
      this.order = sortState.direction;
      this.getEmployeesPaged(this.pageIndex, this.pageSize);
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.column='';
      this.order='';
      this.getEmployeesPaged(this.pageIndex, this.pageSize);
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  // nextPage(event: PageEvent) {
  //   this.getEmployeesPaged(this.paginator.pageIndex, this.paginator.pageSize);
  // }

  previousPage() {
    if (this.pageIndex!=0){
        this.pageIndex-=1;
        this.getEmployeesPaged(this.pageIndex,this.pageSize);
    }
  }

  nextPage() {
    if (this.pageIndex!=this.nrPages-1){
      this.pageIndex+=1;
      this.getEmployeesPaged(this.pageIndex,this.pageSize);
    }
  }

  goToFirstPage() {
    this.pageIndex=0;
    this.getEmployeesPaged(this.pageIndex,this.pageSize);
  }

  goToLastPage() {
    this.pageIndex=this.nrPages-1;
    this.getEmployeesPaged(this.pageIndex,this.pageSize);
  }

  changeItPlease() {
    console.log(this.pageSize);
    this.pageIndex=0;
    this.getEmployeesPaged(this.pageIndex,this.pageSize);
  }

  goToUser(username: string ) {
    this.router.navigateByUrl(`/userprofile/${username}`)
  }
}
