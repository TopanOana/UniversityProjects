import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from "./app.component";
import {StudentComponent} from "./features/student/student.component";
import {ProfessorComponent} from "./features/professor/professor.component";
import {LoginComponent} from "./features/login/login.component";
import {HomeComponent} from "./common/home/home.component";

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "student",
    component: StudentComponent
  },
  {
    path: "professor",
    component: ProfessorComponent
  },
  {
    path: "login",
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
