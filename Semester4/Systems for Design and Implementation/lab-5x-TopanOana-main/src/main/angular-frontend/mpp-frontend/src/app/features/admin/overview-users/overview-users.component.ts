import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ApiService} from "../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatTableDataSource} from "@angular/material/table";
import {UserDTO, UserTable} from "./UserModel";

@Component({
  selector: 'app-overview-users',
  templateUrl: './overview-users.component.html',
  styleUrls: ['./overview-users.component.css'],
  providers: [MatSnackBar]
})
export class OverviewUsersComponent implements OnInit, AfterViewInit{

  adminCheck: boolean;
  pageIndex=0;
  pageSize=5;
  dataSource = new MatTableDataSource();
  pageFirst =true;
  pageLast=false;
  nrPages=0;
  totalUsers = 0;
  displayedColumns = ['id', 'username', 'email', 'location', 'bio', 'age', 'roles'];
  opened: boolean;





  constructor(private service:ApiService, private router:Router, private snackBar:MatSnackBar){
    this.adminCheck=false;
    this.opened=false;
  }



  ngAfterViewInit(): void {
    this.service.checkAdmin().subscribe((result:string)=>{
      this.adminCheck = true;
      this.getUsersPaged(this.pageIndex, this.pageSize);
    },(err)=>{
      this.snackBar.open(err['error']['message'],'close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    })
  }

  ngOnInit(): void {
  }


  goBackToHome() {
    this.router.navigateByUrl("");
  }

  goToFirstPage() {
    this.pageIndex=0;
    this.getUsersPaged(this.pageIndex,this.pageSize);
  }

  private getUsersPaged(pageIndex: number, pageSize: number) {
    this.service.getUsersForAdmin(pageIndex,pageSize).subscribe((data:UserTable)=>{
      this.dataSource.data = data['content'];
      this.totalUsers = data['totalElements'];
      this.nrPages = data['totalPages']
      if(pageIndex==0)
        this.pageFirst=true;
      else this.pageFirst=false;
      if(pageIndex==this.nrPages-1)
        this.pageLast=true;
      else this.pageLast=false;
    })
  }

  previousPage() {
    this.pageIndex-=1;
    this.getUsersPaged(this.pageIndex,this.pageSize);
  }

  nextPage() {
    this.pageIndex+=1;
    this.getUsersPaged(this.pageIndex,this.pageSize);
  }

  goToLastPage() {
    this.pageIndex=this.nrPages-1;
    this.getUsersPaged(this.pageIndex,this.pageSize);
  }

  goToUserDetails(userrow:UserDTO) {
    let userID = userrow.id;
    this.router.navigateByUrl(`admin/users/${userID}`);
  }

  changeItPlease() {
    this.pageIndex=0;
    this.getUsersPaged(this.pageIndex,this.pageSize);
  }
}
