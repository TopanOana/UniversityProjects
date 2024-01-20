import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ApiService} from "../../../common/api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-sql-scripts',
  templateUrl: './sql-scripts.component.html',
  styleUrls: ['./sql-scripts.component.css'],
  providers: [MatSnackBar]
})
export class SqlScriptsComponent implements OnInit, AfterViewInit{
  checkAdmin: boolean;
  tables = ['STOCK','EMPLOYEE', 'STORE', 'BOOK'];
  table: string;



  constructor(private service: ApiService, private router: Router, private snackBar:MatSnackBar) {
    this.checkAdmin = false;
    this.table = '';
  }

  ngAfterViewInit(): void {
    this.service.checkAdmin().subscribe((result:string)=>{
      this.checkAdmin=true;
    },(err)=>{
      this.checkAdmin=false;
    })
  }

  ngOnInit(): void {
  }


  bulkDelete() {
    this.service.bulkDelete().subscribe((result)=>{
      this.snackBar.open("Bulk delete has been completed",'close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    }, (error)=>{
      this.snackBar.open(error['error']['message'],'close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    })
  }

  insertIntoTable() {
    if (this.table){
      this.service.insertIntoTable(this.table).subscribe((result)=>{
        this.snackBar.open('insertions were successful','close',{
          horizontalPosition:"center",
          verticalPosition:"top"
        })
      }, (error)=>{
        this.snackBar.open(error['error']['message'],'close',{
          horizontalPosition:"center",
          verticalPosition:"top"
        })
      })
    }else{
      this.snackBar.open('select a table','close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    }
  }
}
