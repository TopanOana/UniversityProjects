import { Component } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {ApiService} from "../../../common/api.service";
import {Router} from "@angular/router";
import {LoginRequest} from "./LoginRequest";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MatSnackBar]
})
export class LoginComponent {
  username="";
  password="";

  constructor(private service:ApiService, private router:Router, private snackBar:MatSnackBar){

  }

  login() {
    if(this.username && this.password){
      const loginRequest : LoginRequest ={
        username:this.username,
        password:this.password
      }
      this.service.loginUser(loginRequest).subscribe((result:string)=>{
        this.service.token = result;
        this.router.navigateByUrl("");
      }, (err)=>{
        console.log(err)
        this.snackBar.open("username or password is not correct", 'close',{
          horizontalPosition:"center",
          verticalPosition:"top"
        })
      })
    }
    else{
      this.snackBar.open("enter your username and password",'close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    }



  }
}
