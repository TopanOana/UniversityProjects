import 'package:books_tbr/model/BookService.dart';
import 'package:books_tbr/widgets/AddBookScreen.dart';
import 'package:flutter/material.dart';
import '../model/Book.dart';
import 'UpdateBookScreen.dart';

class Listing extends StatefulWidget {
  final BookService service;

  Listing(this.service ,{Key? key}) : super(key: key);

  @override
  _ListingState createState() => _ListingState();
}

class _ListingState extends State<Listing> {
  @override
  Widget build(BuildContext context) {
    var books = widget.service.books;

    return ListView.builder(
      itemCount: books.length,
      shrinkWrap: true,
      physics: const NeverScrollableScrollPhysics(),
      itemBuilder: (context, index) {
        Book book = books[index];
        return BookTile(
          book: book,
          onUpdateClick: (int id) async {
            var result = await Navigator.push(context,
            MaterialPageRoute(builder: (context) => UpdateBookScreen(book)));
            if (result != null){
              setState(() {
                widget.service.update(result);
              });
            }
          },
          deleteItemHandler: () {
            setState(() {
              widget.service.remove(book.id);
            });
          },
        );
      },
    );
  }
}

class DeleteBookDialog extends StatelessWidget {
  final String bookTitle;
  final VoidCallback onDelete;
  final VoidCallback onCancel;

  DeleteBookDialog({
    required this.bookTitle,
    required this.onDelete,
    required this.onCancel,
  });

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text("Are you sure you want to delete the book $bookTitle?"),
      content: Text("This change is permanent."),
      actions: [
        TextButton(
          onPressed: onDelete,
          child: Text("Delete book"),
        ),
        TextButton(
          onPressed: onCancel,
          child: Text("Don't delete book"),
        ),
      ],
    );
  }
}

// Call this function when you want to show the delete book dialog.
void _showDeleteBookDialog(BuildContext context, String bookTitle,
    VoidCallback ondelete, VoidCallback oncancel) {
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return DeleteBookDialog(
        bookTitle: bookTitle,
        onDelete: () {
          ondelete();
          Navigator.of(context).pop(); // Close the dialog after deletion.
        },
        onCancel: () {
          oncancel();
          Navigator.of(context).pop(); // Close the dialog without deleting.
        },
      );
    },
  );
}

class BookTile extends StatefulWidget {
  final Book book;
  final Function(int) onUpdateClick;
  final VoidCallback deleteItemHandler;

  BookTile({
    required this.book,
    required this.onUpdateClick,
    required this.deleteItemHandler,
  });

  @override
  _BookCardWidgetState createState() => _BookCardWidgetState();
}

class _BookCardWidgetState extends State<BookTile> {
  bool expanded = false;
  bool openDialog = false;

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 8),
      color: Theme
          .of(context)
          .primaryColor,
      child: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Row(
          children: [
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "${widget.book.title} by ${widget.book.author}",
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                  if (expanded)
                    Column(
                      children: [
                        Text("${widget.book.nrPages} pages"),
                        Text(widget.book.genre),
                        Text("status: ${widget.book.status}"),
                        Row(
                          children: [
                            ElevatedButton(
                              onPressed: () {
                                widget.onUpdateClick(widget.book.id);
                              },
                              child: const Icon(Icons.update),
                            ),
                            ElevatedButton(
                              onPressed: () {
                                setState(() {
                                  openDialog = true;
                                });
                                _showDeleteBookDialog(
                                    context, widget.book.title, () {
                                  setState(() {
                                    widget.deleteItemHandler();
                                    openDialog = false;
                                    expanded = false;
                                  });
                                }, () {
                                  setState(() {
                                    openDialog = false;
                                    expanded = false;
                                  });
                                });
                              },
                              child: const Icon(Icons.delete),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        expanded = !expanded;
                      });
                    },
                    child: Text(expanded ? "Show less" : "Show more"),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}




class ListViewScreen extends StatefulWidget{
  final BookService service;

  ListViewScreen(this.service, {Key? key});

  @override
  _ListViewState createState() => _ListViewState();
}

class _ListViewState extends State<ListViewScreen>{
  @override
  Widget build(BuildContext context) {
    const title = 'BooksTBR';
    BookService service = BookService();
    return Scaffold(
          appBar: AppBar(
            title: const Text(title),
          ),
          body: SingleChildScrollView(
              child: Column(
                children: [
                  ElevatedButton(onPressed: () async {
                    final result = await Navigator.push(context,
                        MaterialPageRoute(builder: (context)
                    =>
                    const AddBookScreen()
                    ));
                    if (result != null){
                      setState(() {
                        service.add(result);
                      });
                    }
                  }, child: const Icon(Icons.add)),
                  SingleChildScrollView(child: Listing(service))
                ],
              )
          ),
        );
  }
}
