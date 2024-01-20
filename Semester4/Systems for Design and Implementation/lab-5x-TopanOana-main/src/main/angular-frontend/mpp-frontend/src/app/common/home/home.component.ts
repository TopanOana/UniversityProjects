import {AfterViewInit, Component} from '@angular/core';
import {ApiService} from "../api.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements AfterViewInit{
  adminCheck: boolean;

  constructor(private service: ApiService, private router: Router){
    this.adminCheck=false;
  }

  ngAfterViewInit(): void {
    this.service.checkAdmin().subscribe((result:string)=>{
      this.adminCheck=true;
    },(error)=>{
      this.adminCheck=false;
    })
  }


  goToAdminUsers() {
    this.router.navigateByUrl("admin/users")
  }

  goToAdminSQLScripts() {
    this.router.navigateByUrl("admin/sql_scripts");
  }
}
