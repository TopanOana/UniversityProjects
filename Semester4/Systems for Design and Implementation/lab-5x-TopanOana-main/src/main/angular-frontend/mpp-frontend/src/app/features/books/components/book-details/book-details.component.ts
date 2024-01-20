import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Book} from "../overview-books/Models/books.models";
import {ApiService} from "../../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {StockTable} from "../../../stores/components/overview-stores/Models/store.models";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css'],
  providers: [MatPaginator, MatSnackBar]
})
export class BookDetailsComponent implements OnInit, AfterViewInit{
  bookID?: number;
  book?: Book;

  title?: string;
  author?: string;
  nrPages?: number;
  rating?: number;
  genre?: string;

  dataSource = new MatTableDataSource();
  // @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns = ['id', 'storeName', 'quantity'];
  pageIndex=0;
  pageFirst=true;
  pageLast=false;
  pageSize=5;
  loggedIn:boolean;

  nrPagesPag=0;
  totalStocks: number;
  constructor(private service:ApiService, private activatedRoute: ActivatedRoute, private router:Router,/* paginator:MatPaginator,*/ private snackBar:MatSnackBar) {
    // this.paginator = paginator;
    this.pageSize = 5;
    this.totalStocks =0;
    this.loggedIn=false;
  }

  ngOnInit():void{
    this.activatedRoute.params.subscribe(params =>{
      this.bookID = params['id']
      console.log(this.bookID)
      this.service.getBookDetails(this.bookID!).subscribe((book: Book)=>{
        this.book = book
        this.title = this.book.title
        this.author = this.book.author
        this.nrPages = this.book.nrPages
        this.rating = this.book.rating
        this.genre = this.book.genre
      })
    })
  }

  ngAfterViewInit() {
    this.getStocksPaged(this.pageIndex, this.pageSize)
    if (this.service.token.length>0)
      this.loggedIn=true;
  }

  getStocksPaged(page:number, size:number){
    this.service.getStocksFromBook(this.bookID!,page,size).subscribe((result:StockTable)=>{
      this.dataSource.data = result['content']
      this.totalStocks = result['totalElements']
      this.nrPagesPag = result['totalPages']
      if(page>0)
        this.pageFirst=false;
      else this.pageFirst=true;
      if(page==this.nrPagesPag-1)
        this.pageLast=true;
      else this.pageLast=false;
      console.log(this.nrPagesPag)
    })
  }
  updateBook() {
    console.log("update comp")
    console.log(this.book!.title)
    console.log(this.title)
    console.log(this.author)
    console.log(this.nrPages)
    if(this.title && this.author && this.nrPages && this.rating && this.genre){
      this.book!.title = this.title
      this.book!.author = this.author
      this.book!.nrPages = this.nrPages
      this.book!.rating = this.rating
      this.book!.genre = this.genre
      this.service.updateBook(this.book!, this.bookID!).subscribe((result: Book)=>
        {
          console.log("iesit din update")
          this.router.navigateByUrl('books');
        },
        (err)=>{
        console.log(err)
          this.snackBar.open(err['error']['message'],'close',{
            horizontalPosition:"center",
            verticalPosition:"top"
          })
        }
      )
    }
    else{
      this.snackBar.open("complete all the fields!",'close',{
        horizontalPosition:"center",
        verticalPosition:"top"
      })
    }

  }

  deleteBook() {
    console.log(this.bookID)
    this.service.removeBook(this.bookID!).subscribe((result: Book)=>
      {
        console.log("iesit din update")
        this.router.navigateByUrl('books');
      },
      (err)=>console.log(err))
  }
  goBackToOverview(){
    this.router.navigateByUrl("books")
  }

  // nextPage($event: PageEvent) {
  //   this.getStocksPaged(this.paginator.pageIndex, this.paginator.pageSize)
  // }
  goToFirstPage() {
    this.pageIndex=0;
    this.getStocksPaged(this.pageIndex,this.pageSize)
  }

  previousPage() {
    this.pageIndex-=1;
    this.getStocksPaged(this.pageIndex,this.pageSize)
  }

  nextPage() {
    this.pageIndex+=1;
    this.getStocksPaged(this.pageIndex,this.pageSize)
  }

  goToLastPage() {
    this.pageIndex=this.nrPagesPag-1;
    this.getStocksPaged(this.pageIndex,this.pageSize)
  }

  changeItPlease() {
    this.pageIndex=0;
    this.getStocksPaged(this.pageIndex,this.pageSize)
  }
}
