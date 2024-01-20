import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AddBookDTO, Book, BookTable} from "../features/books/components/overview-books/Models/books.models";
import {
  Employee,
  StoreDTO,
  EmployeeTable,
  AddEmployeeDTO, StoreTable, UpdateEmployeeDTO
} from "../features/employees/components/overview-employees/Models/employees.models";
import {
  AddStockDTO,
  AddStoreDTO,
  StockDTO, StockTable,
  UpdateStockDTO
} from "../features/stores/components/overview-stores/Models/store.models";
import {
  BookStockStat,
  BookStockTable,
  StoreStockStat, StoreStockTable
} from "../features/statistics/components/book-stock-statistic/Model/stat.model";
import {LoginRequest} from "../features/auth/login/LoginRequest";
import {RegisterDTO} from "../features/auth/register/RegisterDTO";
import {User, UserStat} from "../features/users/user-profile/UserModels";
import {UserDTO, UserTable} from "../features/admin/overview-users/UserModel";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  // baseURL='https://chicken-soup-for-the-books.chickenkiller.com/supaLaPlic/api';
  // baseURL='http://localhost:8080/api'
  baseURL = 'https://13.51.252.181/supaLaPlic/api';

  token="";

  constructor(private http: HttpClient) { }

  getBooks(page:number, size:number, rating_gt?:number, column?:string, order?:string, input?:string): Observable<BookTable>{
    if(rating_gt) {
      return this.http.get(`${this.baseURL}/books?rating_gt=${rating_gt}&page=${page}&size=${size}`) as Observable<BookTable>;
    }
    else
      if(column && order)
        return this.http.get(`${this.baseURL}/books?page=${page}&size=${size}&column=${column}&order=${order}`) as Observable<BookTable>
      else
        if(input)
          return this.http.get(`${this.baseURL}/books?page=${page}&size=${size}&input=${input}`) as Observable<BookTable>
        else return this.http.get(`${this.baseURL}/books?page=${page}&size=${size}`) as Observable<BookTable>
  }

  getBookDetails(bookID: number): Observable<Book>{
    return this.http.get(`${this.baseURL}/books/${bookID}`) as Observable<Book>
  }

  updateBook(book:Book, bookID:number): Observable<Book>{
    console.log("update serv")
    return this.http.put(`${this.baseURL}/books/${bookID}`, book, {headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<Book>
  }

  removeBook(bookID:number): Observable<Book>{
    console.log("remove book service"+bookID)
    return this.http.delete(`${this.baseURL}/books/${bookID}`, {headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<Book>
  }

  addBook(book: AddBookDTO): Observable<Book>{
    return this.http.post(`${this.baseURL}/books`, book, {headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<Book>
  }



  getEmployees(page:number, size:number, column?:string, order?:string): Observable<EmployeeTable>{
    if (column && order)
      return this.http.get(`${this.baseURL}/employees?page=${page}&size=${size}&column=${column}&order=${order}`) as Observable<EmployeeTable>
    return this.http.get(`${this.baseURL}/employees?page=${page}&size=${size}`) as Observable<EmployeeTable>
  }

  getEmployeeDetails(employeeID: number): Observable<Employee>{
    return this.http.get(`${this.baseURL}/employees/${employeeID}`) as Observable<Employee>
  }

  updateEmployee(employee: UpdateEmployeeDTO, employeeID: number, storeID: number): Observable<Employee>{
    return this.http.put(`${this.baseURL}/employees/${employeeID}?storeID=${storeID}`, employee,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<Employee>
  }

  removeEmployee(employee:number): Observable<Employee>{

    return this.http.delete(`${this.baseURL}/employees/${employee}`,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<Employee>
  }

  getStores(page:number, size:number, input?:string, column?:string, order?:string): Observable<StoreTable>{
    if (input){
      return this.http.get(`${this.baseURL}/stores?input=${input}&page=${page}&size=${size}`) as Observable<StoreTable>
    }
    if(column && order)
        return this.http.get(`${this.baseURL}/stores?page=${page}&size=${size}&column=${column}&order=${order}`) as Observable<StoreTable>

    return this.http.get(`${this.baseURL}/stores?page=${page}&size=${size}`) as Observable<StoreTable>
  }

  addEmployee(employee: AddEmployeeDTO, storeID:number): Observable<Employee>{
    return this.http.post(`${this.baseURL}/employees?storeID=${storeID}`, employee,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<Employee>
  }

  addStore(store: AddStoreDTO): Observable<StoreDTO>{
    return this.http.post(`${this.baseURL}/stores`, store,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<StoreDTO>
  }

  getStoreDetails(storeID:number): Observable<StoreDTO>{
    return this.http.get(`${this.baseURL}/stores/${storeID}`) as Observable<StoreDTO>
  }

  updateStore(storeID:number, store:StoreDTO) : Observable<StoreDTO>{
    return this.http.put(`${this.baseURL}/stores/${storeID}`, store,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<StoreDTO>
  }

  deleteStore(storeID:number): Observable<StoreDTO>{
    return this.http.delete(`${this.baseURL}/stores/${storeID}`,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<StoreDTO>
  }


  getBookStockStat(page: number, size:number): Observable<BookStockTable>{
    return this.http.get(`${this.baseURL}/stats/books?page=${page}&size=${size}`) as Observable<BookStockTable>
  }

  getStoreStockStat(page: number, size:number):Observable<StoreStockTable>{
    return this.http.get(`${this.baseURL}/stats/stores?page=${page}&size=${size}`) as Observable<StoreStockTable>
  }

  getStocksFromStore(storeID:number, page:number, size:number):Observable<StockTable>{
    return this.http.get(`${this.baseURL}/stores/${storeID}/stock?page=${page}&size=${size}`) as Observable<StockTable>
  }
  deleteStockFromStore(storeID:number, stockID:number):Observable<StockDTO>{
    return this.http.delete(`${this.baseURL}/stores/${storeID}/stock?stockID=${stockID}`,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<StockDTO>;
  }

  addStockToStore(storeID:number, stock:AddStockDTO):Observable<StockDTO>{
    return this.http.post(`${this.baseURL}/stores/${storeID}/stock`, stock,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<StockDTO>
  }

  getStockByID(stockID:number):Observable<StockDTO>{
    return this.http.get(`${this.baseURL}/stocks/${stockID}`) as Observable<StockDTO>
  }

  updateStockFromStore(storeID:number, stockID:number, stock:UpdateStockDTO):Observable<StockDTO>{
    return this.http.put(`${this.baseURL}/stores/${storeID}/stock/${stockID}`, stock,{headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<StockDTO>
  }

  getStocksFromBook(bookID:number, page:number, size:number):Observable<StockTable>{
    return this.http.get(`${this.baseURL}/books/${bookID}/stock?page=${page}&size=${size}`) as Observable<StockTable>
  }

  loginUser(loginRequest:LoginRequest):Observable<any>{
    return this.http.post(`${this.baseURL}/users/authenticate`, loginRequest, {responseType:'text'}) as Observable<string>;
  }

  registerUser(registerRequest:RegisterDTO):Observable<any>{
    return this.http.post(`${this.baseURL}/users/register`, registerRequest, {responseType:'text'}) as Observable<string>;
  }

  verifyUser(registerRequest:RegisterDTO, code:string): Observable<any>{
    return this.http.put(`${this.baseURL}/users/register/verify/${code}`, registerRequest, {responseType:'text'}) as Observable<string>;
  }

  getUser(username:string): Observable<User>{
    return this.http.get(`${this.baseURL}/users/profile/${username}`) as Observable<User>;
  }

  getUserStats(username:string):Observable<UserStat>{
    return this.http.get(`${this.baseURL}/users/stats/${username}`) as Observable<UserStat>;
  }

  checkAdmin():Observable<string>{
    return this.http.get(`${this.baseURL}/admin/checkRole`, {responseType:'text', headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<string>;
  }

  getUsersForAdmin(page:number, size:number):Observable<UserTable>{
    return this.http.get(`${this.baseURL}/users/admin/getAll?page=${page}&size=${size}`, {headers:{'Authorization' : `Bearer ${this.token}`}}) as Observable<UserTable>;
  }

  getUserById(id:number):Observable<UserDTO>{
    return this.http.get(`${this.baseURL}/users/${id}`) as Observable<UserDTO>;
  }

  updateUser(id:number, roles:string):Observable<UserDTO>{
    return this.http.put(`${this.baseURL}/users/admin/update?id=${id}&roles=${roles}`, null,{headers:{'Authorization' : `Bearer ${this.token}`}} ) as Observable<UserDTO>
  }

  bulkDelete():Observable<string>{
    return this.http.delete(`${this.baseURL}/users/admin/bulkDelete`, {headers:{'Authorization' : `Bearer ${this.token}`}, responseType:'text'}) as Observable<string>
  }

  insertIntoTable(tableName:string):Observable<string>{
    return this.http.get(`${this.baseURL}/users/admin/insert?table=${tableName}`, {headers:{'Authorization' : `Bearer ${this.token}`}, responseType:'text'}) as Observable<string>
  }

}
