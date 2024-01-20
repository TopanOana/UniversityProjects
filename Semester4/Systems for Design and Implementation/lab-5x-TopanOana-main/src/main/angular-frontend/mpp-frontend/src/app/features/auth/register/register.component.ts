import { Component } from '@angular/core';
import {ApiService} from "../../../common/api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {RegisterDTO} from "./RegisterDTO";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [MatSnackBar]
})
export class RegisterComponent {
  username="";
  password="";
  email="";
  bio="";
  location="";
  age=0;
  confirmPassword="";

  constructor(private service:ApiService, private router:Router, private snackBar:MatSnackBar){
  }

  register() {
    if(this.username && this.password && this.confirmPassword &&
        this.email && this.bio && this.location && this.age){
        if(this.password == this.confirmPassword){
            if (this.password.length>8 && this.password.length<20){
              if(this.age>0){
                const register : RegisterDTO={
                  username: this.username,
                  password: this.password,
                  email: this.email,
                  bio: this.bio,
                  location: this.location,
                  age: this.age
                }
                this.service.registerUser(register).subscribe((result:string)=>{
                  this.service.verifyUser(register,result).subscribe((finalstr:string)=>{
                    if(finalstr=="User verified"){
                      this.router.navigateByUrl("login");
                    }
                    else{
                      this.snackBar.open("user not verified",'close',{
                        horizontalPosition:"center",
                        verticalPosition:"top"
                      })
                    }
                  })
                }, (err)=>{
                  this.snackBar.open(err['error']['message'],'close',{
                    horizontalPosition:"center",
                    verticalPosition:"top"
                  })
                })
              }else{
                this.snackBar.open("age must be a positive number",'close',{
                  horizontalPosition:"center",
                  verticalPosition:"top"
                })
              }
            }
            else{
              this.snackBar.open("password must be between 8 and 20 characters",'close',{
                horizontalPosition:"center",
                verticalPosition:"top"
              })
            }
        }
        else{
          this.snackBar.open("password and confirmation aren't the same",'close',{
            horizontalPosition:"center",
            verticalPosition:"top"
          })
        }
    }else{
      this.snackBar.open("enter all fields!",'close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    }
  }
}
