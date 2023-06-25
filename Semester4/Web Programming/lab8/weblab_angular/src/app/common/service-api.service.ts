import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServiceApi {
  baseURL = 'http://localhost/Weblab7';

  constructor(private http: HttpClient) {
  }

  getStudentByUsernameAndPassword(username: string, password: string): Observable<any> {
    return this.http.get(`${this.baseURL}/Controller.php?action=getStudentByUsernameAndPassword&username=${username}&password=${password}`);
  }

  loginStudent(userID: number, name: string): Observable<any> {
    return this.http.get(`${this.baseURL}/loginFunctions.php?action=loginStudent&userID=${userID}&name=${name}`, {
      responseType: 'text',
      withCredentials: true
    });
  }

  getProfessorByUsernameAndPassword(username: string, password: string): Observable<any> {
    return this.http.get(`${this.baseURL}/Controller.php?action=getProfessorByUsernameAndPassword&username=${username}&password=${password}`);
  }

  loginProfessor(userID: number, name: string): Observable<any> {
    return this.http.get(`${this.baseURL}/loginFunctions.php?action=loginProfessor&userID=${userID}&name=${name}`, {
      responseType: 'text',
      withCredentials: true
    });
  }

  getUserID(): Observable<any> {
    return this.http.get(`${this.baseURL}/loginFunctions.php?action=getUserID`, {
      responseType: 'text',
      withCredentials: true
    });
  }

  getName(): Observable<any> {
    return this.http.get(`${this.baseURL}/loginFunctions.php?action=getName`, {
      responseType: 'text',
      withCredentials: true
    });
  }

  getStudentGrades(studentID: number): Observable<any> {
    return this.http.get(`${this.baseURL}/Controller.php?action=getStudentGrades&studentID=${studentID}`);
  }

  logout(): Observable<any> {
    return this.http.get(`${this.baseURL}/loginFunctions.php?action=logout`, {
      responseType: 'text',
      withCredentials: true
    });
  }


  getGroupsForProfessor(professorID: number): Observable<any> {
    return this.http.get(`${this.baseURL}/Controller.php?action=getGroupsForProfessor&professorID=${professorID}`)
  }

  getStudentsGradesWithGroupID(groupID: string): Observable<any> {
    return this.http.get(`${this.baseURL}/Controller.php?action=getStudentsGradesWithGroupID&groupID=${groupID}`)
  }

  addGrade(studentname: string, coursename: string, mark: number): Observable<any> {
    return this.http.get(`${this.baseURL}/Controller.php?action=addGrade&studentname=${studentname}&coursename=${coursename}&mark=${mark}`)
  }


}
