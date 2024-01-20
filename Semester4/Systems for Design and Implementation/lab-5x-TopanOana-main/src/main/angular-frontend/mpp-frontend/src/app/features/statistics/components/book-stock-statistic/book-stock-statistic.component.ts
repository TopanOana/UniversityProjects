import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {ApiService} from "../../../../common/api.service";
import {Router} from "@angular/router";
import {BookStockStat, BookStockTable} from "./Model/stat.model";

@Component({
  selector: 'app-book-stock-statistic',
  templateUrl: './book-stock-statistic.component.html',
  styleUrls: ['./book-stock-statistic.component.css'],
  providers: [MatPaginator]
})
export class BookStockStatisticComponent implements OnInit, AfterViewInit{
  displayedColumns = ['id', 'title', 'author', 'nrPages', 'rating', 'genre', 'quantity']
  dataSource= new MatTableDataSource();

  totalBooks: number;
  pageIndex=0;
  pageFirst=true;
  pageLast=false;
  pageSize=5;

  nrPages=0;

  // @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service: ApiService, private router: Router/*, paginator: MatPaginator*/) {
    // this.paginator = paginator;
    this.totalBooks = 0;
  }

  getAllBooks(page:number, size:number){
    this.service.getBookStockStat(page, size).subscribe((result:BookStockTable)=>{
      this.dataSource.data = result['content'];
      this.totalBooks = result['totalElements'];
      this.nrPages = result['totalPages'];
      if(page>0)
        this.pageFirst=false;
      else this.pageFirst=true;
      if(page==this.nrPages-1)
        this.pageLast=true;
      else this.pageLast=false;
    })
  }
  ngOnInit(): void {

  }
  ngAfterViewInit() {
    this.getAllBooks(this.pageIndex, this.pageSize)
  }


  goBackToHome() {
    this.router.navigateByUrl("");
  }

  // nextPage($event: PageEvent) {
  //   this.getAllBooks(this.paginator.pageIndex, this.paginator.pageSize);
  // }
  goToFirstPage() {
    this.pageIndex=0;
    this.getAllBooks(this.pageIndex,this.pageSize);
  }

  previousPage() {
    this.pageIndex-=1;
    this.getAllBooks(this.pageIndex,this.pageSize);
  }

  nextPage() {
    this.pageIndex+=1;
    this.getAllBooks(this.pageIndex,this.pageSize);
  }

  goToLastPage() {
    this.pageIndex=this.nrPages-1;
    this.getAllBooks(this.pageIndex,this.pageSize);
  }

  changeItPlease() {
    this.pageIndex=0;
    this.getAllBooks(this.pageIndex,this.pageSize);
  }
}
