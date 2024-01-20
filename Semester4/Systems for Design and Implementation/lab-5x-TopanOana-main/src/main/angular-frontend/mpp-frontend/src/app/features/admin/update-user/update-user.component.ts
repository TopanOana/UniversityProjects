import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ApiService} from "../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit, AfterViewInit{
  username ="";
  userID= 0;
  roles="";
  options=['USER', 'MODERATOR','ADMIN'];
  checkAdmin: boolean;

  constructor(private service: ApiService, private router: Router, private activatedRoute: ActivatedRoute){
    this.checkAdmin=false;
  }

  ngOnInit(): void {
    this.service.checkAdmin().subscribe((str:string)=>{
      this.checkAdmin=true;
    })
    this.activatedRoute.params.subscribe(params=>{
      this.userID = params['id'];
      this.service.getUserById(this.userID).subscribe((user)=>{
        this.username = user.username;
        this.roles = user.roles;
      })
    })
  }

  ngAfterViewInit(): void {
  }


  updateUser() {
    console.log(this.roles);
    if(this.username && this.roles){

      this.service.updateUser(this.userID, this.roles).subscribe((user)=>{
        this.router.navigateByUrl("admin/users")
      });
    }
  }

  goBackToUsers() {
    this.router.navigateByUrl("admin/users");
  }


}
