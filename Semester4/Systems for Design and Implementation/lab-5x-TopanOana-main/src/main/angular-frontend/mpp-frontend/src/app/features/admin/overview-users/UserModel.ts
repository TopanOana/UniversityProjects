export interface UserDTO{
  id:number;
  username:string;
  roles:string;
  email:string;
  bio:string;
  location:string;
  age:number;
}

export interface UserTable{
  content: UserDTO[];
  number:number;
  size:number;
  totalElements:number;
  totalPages:number;
}
