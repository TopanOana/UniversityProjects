import {Component, ViewChild, AfterViewInit, OnInit} from '@angular/core';
import {Book, BookTable} from "./Models/books.models";
import {ApiService} from "../../../../common/api.service";
import {Router} from "@angular/router";
import {MatSort, Sort} from '@angular/material/sort';
import {MatTableDataSource} from "@angular/material/table";
import {LiveAnnouncer} from '@angular/cdk/a11y';
import {MatPaginator, PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-overview-books',
  templateUrl: './overview-books.component.html',
  styleUrls: ['./overview-books.component.css'],
  providers: [MatPaginator, MatSort]
})
export class OverviewBooksComponent implements AfterViewInit, OnInit {
  books: Book[] = []
  displayedColumns = ['id', 'title', 'author', 'nrPages', 'rating', 'genre', 'inStores', 'username']

  rating_gt: number = 0;
  dataSource = new MatTableDataSource();
  totalBooks: number;
  pageSize = 5;
  pageIndex = 0;
  pageFirst =true;
  pageLast=false;
  nrPages=0;
  column ='';
  order='';
  loggedIn :boolean;

  @ViewChild(MatSort) sort: MatSort;
  // @ViewChild(MatPaginator) paginator: MatPaginator;


  constructor(private service: ApiService, private router: Router, private liveAnnouncer: LiveAnnouncer,/* paginator: MatPaginator,*/ sort : MatSort) {
    // this.paginator = paginator;
    this.sort = sort;
    this.totalBooks = 0;
    this.loggedIn=false;
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.getBooksPaged(this.pageIndex, this.pageSize);
    if(this.service.token.length>0)
      this.loggedIn=true;
  }

  private getBooksPaged(page: number, size: number) {
    let rating,column,order;
    if (this.rating_gt > 0)
      rating = this.rating_gt;
    if(this.column!='' && this.order!=''){
      column=this.column;
      order=this.order;
    }
    this.service.getBooks(page, size, rating, column, order).subscribe((bookTable: BookTable) => {
      this.dataSource.data = bookTable['content'];
      this.totalBooks = bookTable['totalElements'];
      this.nrPages = bookTable['totalPages'];
      if(page==0)
        this.pageFirst=true;
      else this.pageFirst=false;
      if(page==this.nrPages-1)
        this.pageLast=true;
      else this.pageLast=false;
    })
  }

  // nextPage(event: PageEvent) {
  //   this.getBooksPaged(this.pageIndex, this.pageSize);
  // }


  clearFilter() {
    this.rating_gt = 0;
    this.goToFilterBook();
  }

  goToFilterBook() {
    // this.paginator.firstPage();
    this.pageIndex=0;
    this.getBooksPaged(this.pageIndex, this.pageSize);
  }

  goToBookDetails(bookrow: Book) {
    console.log(bookrow)
    let bookID = bookrow.bookID
    // let rtes = bookrow.bookID;
    // console.log(rtes)
    this.router.navigateByUrl(`books/${bookID}`)
  }

  goToAddBook() {
    this.router.navigateByUrl(`books/add`)
  }

  goBackToHome() {
    this.router.navigateByUrl("")
  }

  announceSortChange(sortState: Sort) {
    // console.log("got to sort");
    // console.log(sortState.active)
    // console.log(sortState.direction)
    if (sortState.direction) {
      this.column = sortState.active;
      this.order = sortState.direction;
      this.getBooksPaged(this.pageIndex,this.pageSize);
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.column='';
      this.order='';
      this.getBooksPaged(this.pageIndex,this.pageSize);
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  goToFirstPage() {
      this.pageIndex=0;
      this.getBooksPaged(this.pageIndex,this.pageSize);
  }

  previousPage() {
    this.pageIndex-=1;
    this.getBooksPaged(this.pageIndex,this.pageSize);
  }

  nextPage() {
    this.pageIndex+=1;
    this.getBooksPaged(this.pageIndex,this.pageSize);
  }


  goToLastPage() {
    this.pageIndex=this.nrPages-1;
    this.getBooksPaged(this.pageIndex,this.pageSize);
  }

  changeItPlease() {
    this.pageIndex=0;
    this.getBooksPaged(this.pageIndex,this.pageSize);
  }

  toUserProfile(username: string ) {
      this.router.navigateByUrl(`userprofile/${username}`)
  }
}
