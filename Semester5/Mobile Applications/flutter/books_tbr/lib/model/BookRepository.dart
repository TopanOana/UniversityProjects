import 'Book.dart';

class BookRepository{
  late List<Book> books ;

  BookRepository(){
    books = functionToPopulateBooks();
  }

  static BookRepository? instance ;

  static BookRepository getInstance(){
    if (instance == null) {
      instance = BookRepository();
    }
    return instance! ;
  }
}

List<Book> functionToPopulateBooks(){
    List<Book> allBooks = [
      Book(1, "Pride and Prejudice", "Jane Austen", 300, Status.READ, "Classic"),
      Book(2, "Emma", "Jane Austen", 300, Status.READ, "Classic"),
      Book(3, "Sense and Sensibility", "Jane Austen",  300, Status.UNREAD, "Classic"),
      Book(4,  "Persuasion", "Jane Austen", 300, Status.UNREAD, "Classic"),
      Book(5, "Northanger Abbey", "Jane Austen", 300, Status.IN_PROGRESS, "Classic"),
      Book(6, "Jane Eyre",  "Charlotte Bronte", 300,  Status.READ, "Classic"),
      Book(7, "The Great Gatsby", "F. Scott Fitzgerald", 300, Status.IN_PROGRESS, "Classic"),
      Book(8, "Wuthering Heights", "Emily Bronte", 300, Status.UNREAD, "Classic"),
      Book(9, "Dracula", "Bram Stoker", 300, Status.UNREAD, "Classic"),
      Book(10, "For Whom the Bell Tolls", "Ernest Hemingway", 300, Status.IN_PROGRESS, "Classic")
    ];
    return allBooks;
}