import {Book} from "../../../../books/components/overview-books/Models/books.models";
import {Observable} from "rxjs";

export interface Employee{
  id: number;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  salary: number;
  fullTime: boolean;
  description: string;
  username:string;
  store:StoreDTO;
}

export interface UpdateEmployeeDTO{
  id: number;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  salary: number;
  fullTime: boolean;
  description: string;
}

export interface AddEmployeeDTO{
  firstName: string;
  lastName: string;
  phoneNumber: string;
  salary: number;
  fullTime: boolean;
}


export interface StoreDTO{
  storeID:number;
  storeName: string;
  address:string;
  contactNumber: string;
  openingHour: number;
  closingHour: number;
  nrBooks: number;
  nrEmployees: number;

}

export interface EmployeeTable{
  content: Employee[];
  number:number;
  size:number;
  totalElements:number;
  totalPages:number;

}

export interface StoreTable{
  content: StoreDTO[]
  number:number;
  size:number;
  totalElements:number;
  totalPages:number;

}
