import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {ApiService} from "../../../../common/api.service";
import {Router} from "@angular/router";
import {StoreStockStat, StoreStockTable} from "../book-stock-statistic/Model/stat.model";

@Component({
  selector: 'app-store-stock-statistic',
  templateUrl: './store-stock-statistic.component.html',
  styleUrls: ['./store-stock-statistic.component.css'],
  providers: [MatPaginator]
})
export class StoreStockStatisticComponent implements OnInit, AfterViewInit {
  displayedColumns = ['id', 'storeName', 'address', 'contactNumber', 'openingHour', 'closingHour', 'quantity']

  dataSource = new MatTableDataSource();
  totalStores: number;
  pageIndex=0;
  pageFirst=true;
  pageLast=false;
  pageSize=5;

  nrPages=0;

  // @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service: ApiService, private router: Router /*, paginator: MatPaginator*/) {
    // this.paginator = paginator;
    this.totalStores = 0;
  }

  getAllStores(page:number, size:number){
    this.service.getStoreStockStat(page, size).subscribe((result:StoreStockTable)=>{
      this.dataSource.data = result['content']
      this.totalStores = result['totalElements'];
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
    this.getAllStores(this.pageIndex, this.pageSize);
  }






  goBackToHome() {
    this.router.navigateByUrl("");
  }

  // nextPage($event: PageEvent) {
  //   this.getAllStores(this.paginator.pageIndex, this.paginator.pageSize);
  // }
  goToFirstPage() {
    this.pageIndex=0;
    this.getAllStores(this.pageIndex, this.pageSize);
  }

  previousPage() {
    this.pageIndex-=1;
    this.getAllStores(this.pageIndex, this.pageSize);
  }

  nextPage() {
    this.pageIndex+=1;
    this.getAllStores(this.pageIndex, this.pageSize);
  }

  goToLastPage() {
    this.pageIndex=this.nrPages-1;
    this.getAllStores(this.pageIndex, this.pageSize);
  }

  changeItPlease() {
    this.pageIndex=0;
    this.getAllStores(this.pageIndex, this.pageSize);
  }
}
