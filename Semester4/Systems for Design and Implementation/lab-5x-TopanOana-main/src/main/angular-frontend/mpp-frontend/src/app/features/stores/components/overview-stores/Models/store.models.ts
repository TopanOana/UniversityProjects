import {Book} from "../../../../books/components/overview-books/Models/books.models";
import {StoreDTO} from "../../../../employees/components/overview-employees/Models/employees.models";

export interface AddStoreDTO{
  storeName: string;
  contactNumber:string;
  address:string;
  openingHour: number;
  closingHour: number;

}

export interface StockDTO{
  id: number;
  book: Book;
  store: StoreDTO;
  quantity:number;

}

export interface AddStockDTO{
  book:Book;
  quantity:number;

}

export interface UpdateStockDTO{
  id: number;
  book:Book;
  quantity: number;
}

export interface StockTable{
  content: StockDTO[];
  number:number;
  size:number;
  totalElements:number;
  totalPages:number;


}
