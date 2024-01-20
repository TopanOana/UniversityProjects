import {AfterViewInit, Component} from '@angular/core';
import {AddBookDTO, Book} from "../overview-books/Models/books.models";
import {ApiService} from "../../../../common/api.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css'],
  providers: [MatSnackBar]
})
export class AddBookComponent implements AfterViewInit{
  title?:string
  author?:string
  nrPages?: number;
  rating?: number;
  genre?: string;
  loggedIn:boolean;

  constructor(private service:ApiService, private router:Router, private snackBar:MatSnackBar){
    this.loggedIn=false;
  }

  ngAfterViewInit(): void {
    if(this.service.token.length>0)
      this.loggedIn=true;
  }



  addBook() {
    console.log("aici")
    console.log(this.title)
    console.log(this.author)
    console.log(this.nrPages)
    console.log(this.rating)
    console.log(this.genre)
    if(this.title && this.author && this.nrPages && this.rating && this.genre){
      console.log("intrat")
      const book : AddBookDTO ={
        title:this.title,
        author:this.author,
        nrPages:this.nrPages,
        rating:this.rating,
        genre:this.genre
      }
      this.service.addBook(book).subscribe((result: Book)=>
      {
        this.router.navigateByUrl('books');
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


  goBackToOverview() {
    this.router.navigateByUrl('books');
  }
}
