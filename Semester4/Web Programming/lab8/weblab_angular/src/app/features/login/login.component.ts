import {Component} from '@angular/core';
import {ServiceApi} from "../../common/service-api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MatSnackBar]
})
export class LoginComponent {

  username?: string;
  password?: string;


  constructor(private service: ServiceApi, private router: Router, private snackbar: MatSnackBar) {
  }

  login() {
    console.log("in login function")
    console.log(this.username)
    console.log(this.password)
    if (this.username != null && this.password != null) {
      //check if it is a student
      this.service.getStudentByUsernameAndPassword(this.username, this.password).subscribe((value) => {
        if (value) {
          this.service.loginStudent(value['id'], value['name']).subscribe((p) => {
            this.router.navigateByUrl("student");
          });

        } else {
          this.service.getProfessorByUsernameAndPassword(this.username!, this.password!).subscribe((value) => {
            if (value) {
              this.service.loginProfessor(value['id'], value['name']).subscribe((val: string) => {
                console.log(val);
                console.log(this.router)
                this.router.navigateByUrl("professor");

              });


            } else {
              this.snackbar.open("user doesn't exist", 'close', {
                horizontalPosition: "center",
                verticalPosition: "top"
              })
            }
          })
        }
      })
    } else {
      this.snackbar.open("complete the fields", 'close', {
        horizontalPosition: "center",
        verticalPosition: "top"
      })
    }
  }

}
