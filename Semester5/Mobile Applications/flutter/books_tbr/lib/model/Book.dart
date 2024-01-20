import 'package:scoped_model/scoped_model.dart';

enum Status {
  UNREAD('UNREAD'), IN_PROGRESS('IN_PROGRESS'), READ('READ');
  const Status(this.value);
  final String value;
}

class Book extends Model {
  int id;
  String title;
  String author;
  int nrPages;
  Status status;
  String genre;

  Book(this.id, this.title, this.author, this.nrPages, this.status, this.genre);
}
