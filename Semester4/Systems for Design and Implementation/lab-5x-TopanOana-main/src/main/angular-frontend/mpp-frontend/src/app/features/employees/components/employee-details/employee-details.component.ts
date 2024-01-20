import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ApiService} from "../../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";

import {Employee, StoreDTO, StoreTable, UpdateEmployeeDTO} from "../overview-employees/Models/employees.models";
import {FormControl} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css'],
  providers:[MatSnackBar]
})
export class EmployeeDetailsComponent implements AfterViewInit, OnInit{
  employeeID? : number;

  employee?: Employee;
  fullTime?: boolean;

  firstName?: string;
  lastName?: string;
  phoneNumber?: string;

  salary?: number;
  description?: string;
  formControl = new FormControl();

  store?: StoreDTO;
  stores?:StoreDTO[];
  loggedIn:boolean;

  constructor(private service:ApiService, private activatedRoute: ActivatedRoute, private router:Router, private snackBar:MatSnackBar) {
    this.loggedIn=false;
  }

  ngAfterViewInit(): void {
    if (this.service.token.length>0)
      this.loggedIn=true;
  }


  ngOnInit():void{
    this.activatedRoute.params.subscribe(params =>{
      this.employeeID = params['id']
      // console.log(this.employeeID)
      this.service.getEmployeeDetails(this.employeeID!).subscribe((employee: Employee)=>{
        this.employee = employee
        this.firstName = employee.firstName
        // console.log(this.firstName)
        this.lastName = employee.lastName
        this.phoneNumber = employee.phoneNumber
        this.salary = employee.salary
        this.fullTime = employee.fullTime
        this.description = employee.description;
        this.store = employee.store;
        this.formControl.setValue(this.store);
      })
    })
    // this.formControl.valueChanges.subscribe(value => {
    //   if(value.length>=2){
    //     this.service.getStores(0,5, value).subscribe((response:StoreTable)=>{
    //       this.stores = response['content'];
    //     })
    //   }
    // })
  }
  goBackToOverview() {
    this.router.navigateByUrl("employees")
  }

  updateEmployee() {
    // console.log(this.employee)
    // console.log(this.firstName)
    // console.log(this.lastName)
    // console.log(this.phoneNumber)
    // console.log(this.salary)
    // console.log(this.description)
    console.log("here0")
  if (this.firstName && this.lastName && this.phoneNumber && this.salary && this.fullTime!=undefined && this.description){
    console.log("here1")
    this.employee!.firstName = this.firstName
    this.employee!.lastName = this.lastName
    this.employee!.phoneNumber = this.phoneNumber
    this.employee!.salary = this.salary
    this.employee!.fullTime = this.fullTime
    this.employee!.description = this.description

    const employee:UpdateEmployeeDTO={
      id:this.employeeID!,
      firstName:this.firstName,
      lastName:this.lastName,
      phoneNumber:this.phoneNumber,
      salary:this.salary,
      fullTime:this.fullTime,
      description:this.description
    }
    console.log(employee)
    let storeID
    if(this.formControl.value){
      let store = this.formControl.value
      storeID = store.id;
    }
    else{
      storeID = this.store!.storeID;
    }
      this.service.updateEmployee(employee, this.employeeID!, storeID).subscribe((employee:Employee)=>{
        this.router.navigateByUrl("employees");
      }, (err)=>{
        console.log(err)
        this.snackBar.open(err['error']['message'],'close',{
          horizontalPosition:"center",
          verticalPosition:"top"
        })
      })
  }
  else{
    this.snackBar.open("complete all the fields!",'close',{
      horizontalPosition:"center",
      verticalPosition:"top"
    })

  }
  }

  deleteEmployee() {
    console.log(this.employeeID)
    this.service.removeEmployee(this.employeeID!).subscribe((result: Employee)=>
      {
        console.log("iesit din update")
        this.router.navigateByUrl('employees');
      },
      (err)=>console.log(err))
  }

  displayStoreName(store: StoreDTO){
    if (!store) return this.store!.storeName;
    return store.storeName;
  }

  callSearchFromBackend() {
    this.service.getStores(0,5, this.formControl.value).subscribe((response:StoreTable)=> {
      this.stores = response['content'];
    })
  }
}
