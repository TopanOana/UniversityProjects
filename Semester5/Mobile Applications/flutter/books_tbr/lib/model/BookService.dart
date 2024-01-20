import 'package:books_tbr/model/BookRepository.dart';

import 'Book.dart';

class BookService {
  List<Book> books = BookRepository.getInstance().books;

  void remove(int bookId) {
    books.removeWhere((element) => element.id == bookId);
  }

  void add(Book book) {
    int maxID = books.map((e) => e.id).fold(
            0, (previous, current) => previous > current ? previous : current) +
        1;
    book.id = maxID;
    books.add(book);
  }

  void update(Book book) {
    Book? auxBook = books.where((element) => element.id == book.id).first;
    var index = books.indexOf(auxBook);
    books[index] = book;
  }

  Book get(int id){
    return books.where((element) => element.id == id).first;
  }
}
