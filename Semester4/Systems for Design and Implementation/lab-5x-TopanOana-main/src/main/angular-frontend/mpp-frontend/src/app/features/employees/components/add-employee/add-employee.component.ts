import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ApiService} from "../../../../common/api.service";
import {Router} from "@angular/router";
import {AddEmployeeDTO, Employee, StoreDTO, StoreTable} from "../overview-employees/Models/employees.models";
import {Observable} from "rxjs";
import {FormControl} from "@angular/forms";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css'],
  providers: [MatSnackBar]
})
export class AddEmployeeComponent implements OnInit, AfterViewInit {
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  salary?: number;
  fullTime?: boolean;

  // filteredStores?: Observable<StoreDTO[]>;
  stores?: StoreDTO[];

  store?: StoreDTO;
  formControl = new FormControl();
  loggedIn:boolean;

  constructor(private service:ApiService, private router:Router, private snackBar:MatSnackBar){
    this.loggedIn=false;
  }

  ngAfterViewInit(): void {
    if(this.service.token.length>0){
      this.loggedIn=true;
    }
  }

  ngOnInit(){
    // this.formControl.valueChanges.subscribe(value => {
    //   if(value.length>=2){
    //
    //   }
    // })
  }
  goBackToOverview() {
    this.router.navigateByUrl('employees');
  }

  addEmployee() {
    // console.log(this.firstName)
    // console.log(this.lastName)
    // console.log(this.phoneNumber)
    // console.log(this.salary)
    // console.log(this.fullTime)
    // console.log(this.formControl.value)
    // console.log(this.store?.id)
    if(this.firstName && this.lastName && this.phoneNumber && this.salary && this.fullTime){

      const employee:AddEmployeeDTO={
        firstName:this.firstName,
        lastName:this.lastName,
        phoneNumber:this.phoneNumber,
        salary:this.salary,
        fullTime:this.fullTime
      }
      if (this.formControl.value){
        let store = this.formControl.value
        let storeID = store.id;
        this.service.addEmployee(employee, storeID).subscribe((employee:Employee)=>{
            this.router.navigateByUrl("employees");
          },
          (err)=>{
          console.log(err)
            this.snackBar.open(err['error']['message'],'close',{
              horizontalPosition:"center",
              verticalPosition:"top"
            })
          })
      }

    }
    else{
      this.snackBar.open("complete all the fields!",'close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    }

  }

  filter(){
    // this.service.getStores(0,5,this.)
  }
  doTheCompletion() {

  }

  onSelectStore($event: MatAutocompleteSelectedEvent) {

  }

  displayStoreName(store: StoreDTO){
    if (!store) return '';
    return store.storeName;
  }

  callSearchFromBackend() {
    this.service.getStores(0,5, this.formControl.value).subscribe((response:StoreTable)=>{
      this.stores = response['content'];
    })
  }
}
