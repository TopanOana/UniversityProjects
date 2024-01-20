export interface BookStockStat{
  bookID: number;
  title: string;
  author: string;
  nrPages: number;
  rating: number;
  genre: string;
  quantity: number;

}


export interface StoreStockStat{
  storeID: number;
  storeName: string;
  address: string;
  contactNumber: string;
  openingHour: number;
  closingHour: number;
  quantity: number;

}

export interface StoreStockTable{
  content: StoreStockStat[];
  number:number;
  size:number;
  totalElements:number;
  totalPages:number;

}

export interface BookStockTable{
  content: BookStockStat[];
  number:number;
  size:number;
  totalElements:number;
  totalPages:number;
}
