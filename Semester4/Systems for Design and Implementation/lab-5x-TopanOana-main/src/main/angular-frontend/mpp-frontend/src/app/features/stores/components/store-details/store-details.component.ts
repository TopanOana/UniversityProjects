import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StoreDTO} from "../../../employees/components/overview-employees/Models/employees.models";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {StockDTO, StockTable} from "../overview-stores/Models/store.models";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-store-details',
  templateUrl: './store-details.component.html',
  styleUrls: ['./store-details.component.css'],
  providers: [MatPaginator, MatSnackBar]
})
export class StoreDetailsComponent implements OnInit, AfterViewInit{

  storeName?: string;
  address?: string;
  contactNumber?: string;
  openingHour?: number;
  closingHour?: number;
  store?:StoreDTO;
  storeID?:number;
  dataSource = new MatTableDataSource();

  // @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns = ['id', 'title', 'quantity', 'buttons'];
  pageIndex=0;
  pageFirst=true;
  pageLast=false;
  pageSize=5;

  nrPages=0;
  totalStocks: number;

  loggedIn:boolean;

  constructor(private service:ApiService, private activatedRoute: ActivatedRoute, private router:Router, /*paginator:MatPaginator,*/ private snackBar:MatSnackBar) {
    // this.paginator=paginator;
    this.pageSize = 5;
    this.totalStocks = 0;
    this.loggedIn=false;
  }
  goBackToOverview() {
    this.router.navigateByUrl("/stores");
  }

  ngOnInit() : void{
    this.activatedRoute.params.subscribe(params=>{
      this.storeID = params['id']
      this.service.getStoreDetails(this.storeID!).subscribe((store:StoreDTO)=>{
        this.store=store;
        this.storeName=store.storeName;
        this.address=store.address;
        this.contactNumber=store.contactNumber;
        this.openingHour=store.openingHour;
        this.closingHour= store.closingHour;
      });
      // this.service.getStocksFromStore(this.storeID!).subscribe((stocks:StockDTO[])=>{
      //   this.dataSource.data=stocks;
      //   // console.log(stocks[0].book.title);
      // });
    })

  }

  ngAfterViewInit() {
    this.getStocksPaged(this.pageIndex,this.pageSize);
    if(this.service.token.length>0)
      this.loggedIn=true;
  }

  updateStore() {
    if (this.storeName && this.address && this.contactNumber && this.openingHour && this.closingHour){
      this.store!.storeName = this.storeName;
      this.store!.address = this.address;
      this.store!.contactNumber = this.contactNumber;
      this.store!.openingHour = this.openingHour;
      this.store!.closingHour = this.closingHour;
      this.service.updateStore(this.storeID!, this.store!).subscribe((result:StoreDTO)=>{
        this.router.navigateByUrl("stores");
      },
        (err)=>{
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

  deleteStore() {
    this.service.deleteStore(this.storeID!).subscribe((result:StoreDTO)=>{
      this.router.navigateByUrl("stores");
    }, (err)=>console.log(err))
  }

  goToAddStock() {
    this.router.navigateByUrl(`stores/${this.storeID}/add-stock`)
  }

  updateStock(id:number) {
    let stockID = id;
    console.log(stockID)
    this.router.navigateByUrl(`stores/${this.storeID}/update-stock/${stockID}`)
  }

  deleteStock(id:number) {
    this.service.deleteStockFromStore(this.storeID!, id).subscribe((result:StockDTO)=>{
      console.log("deleted it");
      this.ngOnInit();
    })
  }

  getStocksPaged(page:number, size:number){
    this.service.getStocksFromStore(this.storeID!,page,size).subscribe((result:StockTable)=>{
      this.dataSource.data = result['content'];
      this.totalStocks = result['totalElements'];
      this.nrPages = result['totalPages'];
      if(page>0)
        this.pageFirst=false;
      else this.pageFirst=true;
      if(page==this.nrPages-1)
        this.pageLast=true;
      else this.pageLast=false;
    })
  }

  // nextPage($event: PageEvent) {
  //   this.getStocksPaged(this.paginator.pageIndex,this.paginator.pageSize);
  // }
  goToFirstPage() {
    this.pageIndex=0;
    this.getStocksPaged(this.pageIndex, this.pageSize);
  }

  previousPage() {
    this.pageIndex-=1;
    this.getStocksPaged(this.pageIndex, this.pageSize);
  }

  nextPage() {
    this.pageIndex+=1;
    this.getStocksPaged(this.pageIndex, this.pageSize);
  }

  goToLastPage() {
    this.pageIndex=this.nrPages-1;
    this.getStocksPaged(this.pageIndex, this.pageSize);
  }

  changeItPlease() {
    this.pageIndex=0;
    this.getStocksPaged(this.pageIndex, this.pageSize);
  }
}
