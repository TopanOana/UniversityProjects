import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Book, BookTable} from "../../../books/components/overview-books/Models/books.models";
import {FormControl} from "@angular/forms";
import {ApiService} from "../../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AddStockDTO, StockDTO} from "../../../stores/components/overview-stores/Models/store.models";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-add-stock',
  templateUrl: './add-stock.component.html',
  styleUrls: ['./add-stock.component.css'],
  providers: [MatSnackBar]
})
export class AddStockComponent implements OnInit, AfterViewInit{
  quantity?: number;
  storeID?: number;
  book?:Book;
  books?: Book[];
  formControl = new FormControl();
  loggedIn:boolean;

  constructor(private service:ApiService, private router:Router, private activatedRoute: ActivatedRoute, private snackBar:MatSnackBar) {
    this.loggedIn=false;
  }


  ngOnInit() {
    this.activatedRoute.params.subscribe(params=>{
      this.storeID = params['id'];
      // this.formControl.valueChanges.subscribe(value => {
      //   if(value.length>=2){
      //     let rat,col,order;
      //     this.service.getBooks(0,5,rat,col,order,value).subscribe((response:BookTable)=>{
      //       this.books = response['content'];
      //     })
      //   }
      // })
    })
  }

  ngAfterViewInit(): void {
    if(this.service.token.length>0)
      this.loggedIn=true;
  }



  displayBookTitle(book:Book){
    if (!book) return '';
    return book.title;
  }

  goBackToDetails() {
    this.router.navigateByUrl(`stores/${this.storeID}`);
  }

  addStock() {
    if(this.storeID && this.quantity){
      if (this.formControl.value){
        let book = this.formControl.value;
        const stock :AddStockDTO={
          book:book,
          quantity:this.quantity
        }
        this.service.addStockToStore(this.storeID, stock).subscribe((result:StockDTO)=>{
          this.router.navigateByUrl(`stores/${this.storeID}`)
        }, (err)=>{
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

  callSearchFromBackend() {
    if(this.formControl.value.length>2){
      let rat,col,order;
      console.log(this.formControl.value);
      let input = this.formControl.value;
      this.service.getBooks(0,5,rat,col,order,input).subscribe((response:BookTable)=>{
        this.books = response['content'];
      })
    }

  }
}
