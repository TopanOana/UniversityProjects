import {Component, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatSort, Sort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {ApiService} from "../../../../common/api.service";
import {Router} from "@angular/router";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {StoreDTO, StoreTable} from "../../../employees/components/overview-employees/Models/employees.models";

@Component({
  selector: 'app-overview-stores',
  templateUrl: './overview-stores.component.html',
  styleUrls: ['./overview-stores.component.css'],
  providers: [MatPaginator, MatSort]
})
export class OverviewStoresComponent {
  displayedColumns = ['id', 'storeName', 'address', 'contactNumber', 'openingHour', 'closingHour', 'nrBooks', 'nrEmployees', 'username']

  dataSource = new MatTableDataSource();
  totalStores: number;

  pageIndex=0;
  pageFirst=true;
  pageLast=false;
  pageSize=5;

  nrPages=0;

  column='';
  order='';


  @ViewChild(MatSort) sort: MatSort;
  // @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service: ApiService, private router: Router, private liveAnnouncer: LiveAnnouncer, /*paginator: MatPaginator,*/ sort : MatSort) {
    // this.paginator = paginator;
    this.sort = sort;
    this.totalStores = 0;
  }

  ngOnInit():void{
    // console.log("aiciiii");
  }

  ngAfterViewInit(){
    this.getStoresPaged(this.pageIndex, this.pageSize);
    if(this.service.token.length>0)
      this.loggedIn=true;
  }


  goBackToHome() {
    this.router.navigateByUrl("")
  }

  goToAddStore() {
    this.router.navigateByUrl("stores/add")
  }


  getStoresPaged(page:number, size:number){
    let column, order,input;
    if(this.column!='' && this.order!=''){
      column=this.column;
      order=this.order;
    }
    this.service.getStores(page, size, input, column, order).subscribe((data:StoreTable)=>{
      this.dataSource.data = data['content'];
      this.totalStores = data['totalElements'];
      this.nrPages = data['totalPages'];
      if(page>0)
        this.pageFirst=false;
      else this.pageFirst=true;
      if(page==this.nrPages-1)
        this.pageLast=true;
      else this.pageLast=false;
    })
  }
  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.column=sortState.active;
      this.order=sortState.direction;
      this.getStoresPaged(this.pageIndex, this.pageSize);
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.column='';
      this.order='';
      this.getStoresPaged(this.pageIndex, this.pageSize);
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  goToStoreDetails(storerow: StoreDTO) {
    let storeID = storerow.storeID;
    this.router.navigateByUrl(`stores/${storeID}`)

  }

  // nextPage(event:PageEvent){
  //   this.getStoresPaged(this.paginator.pageIndex, this.paginator.pageSize);
  // }
  loggedIn=false;

  goToFirstPage() {
    this.pageIndex=0;
    this.getStoresPaged(this.pageIndex,this.pageSize);
  }

  previousPage() {
    this.pageIndex-=1;
    this.getStoresPaged(this.pageIndex,this.pageSize);
  }

  nextPage() {
    this.pageIndex+=1;
    this.getStoresPaged(this.pageIndex,this.pageSize);
  }

  goToLastPage() {
    this.pageIndex=this.nrPages-1;
    this.getStoresPaged(this.pageIndex,this.pageSize);
  }

  changeItPlease() {
    this.pageIndex=0;
    this.getStoresPaged(this.pageIndex,this.pageSize);
  }

  goToUser(username: string) {
    this.router.navigateByUrl(`/userprofile/${username}`);
  }


}
