import {AfterViewInit, Component, OnInit} from '@angular/core';
import {User, UserStat} from "./UserModels";
import {ApiService} from "../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit, AfterViewInit{
  user?:User;
  username?:string;
  email?:string;
  bio?:string;
  location?:string;
  age?:number;

  nrBooks?:number;
  nrStores?:number;
  nrEmployees?:number;
  nrStocks?:number;

  constructor(private service:ApiService, private activatedRoute: ActivatedRoute, private router:Router){
  }
  ngAfterViewInit(): void {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params=>{
      this.username = params['username']
      this.service.getUser(this.username!).subscribe((user:User)=>{
        this.user = user;
        this.email = user.email;
        this.bio = user.bio;
        this.location = user.location;
        this.age = user.age;
      })
      this.service.getUserStats(this.username!).subscribe((stat:UserStat)=>{
        this.nrBooks = stat.nrBooks;
        this.nrEmployees = stat.nrEmployees;
        this.nrStores = stat.nrStores;
        this.nrStocks = stat.nrStocks;
      })
    })
  }


}
