import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import '../model/Book.dart';

class UpdateBookForm extends StatefulWidget {
  final Book book;

  const UpdateBookForm(this.book, {super.key});

  @override
  UpdateBookFormState createState() => UpdateBookFormState();
}

class UpdateBookFormState extends State<UpdateBookForm> {
  var statusesPossible = <String>['READ', 'IN_PROGRESS', 'UNREAD'];
  var titleState = '';
  var authorState = '';
  var nrPagesState = '';
  var statusState = '';
  var genreState = '';



  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {

    // titleState = widget.book.title;
    // authorState = widget.book.author;
    // nrPagesState = widget.book.nrPages.toString();
    // statusState = widget.book.status.value;
    // genreState = widget.book.genre;
    return Padding(
        padding: const EdgeInsets.all(20),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                initialValue: widget.book.title,

                decoration: const InputDecoration(
                    border: OutlineInputBorder(), labelText: 'title'),
                onChanged: (value) {
                  setState(() {
                    titleState = value;
                  });
                },
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'title cannot be empty';
                  }
                  return null;
                },
                autovalidateMode: AutovalidateMode.always,
              ),
              TextFormField(
                initialValue: widget.book.author,
                decoration: const InputDecoration(
                    border: OutlineInputBorder(), labelText: 'author'),
                onChanged: (value) {
                  setState(() {
                    authorState = value;
                  });
                },
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'author cannot be empty';
                  }
                  return null;
                },
                autovalidateMode: AutovalidateMode.always,
              ),
              TextFormField(
                initialValue: widget.book.nrPages.toString(),
                decoration: const InputDecoration(
                    border: OutlineInputBorder(), labelText: 'number of pages'),
                onChanged: (value) {
                  setState(() {
                    nrPagesState = value;
                  });
                },
                validator: (value) {
                  if (value == null || value.isEmpty ||
                      !RegExp("[1-9][0-9]*").hasMatch(value)) {
                    return 'number of pages must be a number greater than 0';
                  }
                  return null;
                },
                inputFormatters: <TextInputFormatter>[
                  FilteringTextInputFormatter.digitsOnly
                ],
                keyboardType: TextInputType.number,
                autovalidateMode: AutovalidateMode.always,
              ),
              DropdownButtonFormField<String>(
                items: statusesPossible.map<DropdownMenuItem<String>>((String value){
                  return DropdownMenuItem(
                      value : value,
                      child : Text(value)
                  );
                }).toList(),
                onChanged: (String? value) {
                  setState(() {
                    statusState = value!;
                  });
                },
                value: widget.book.status.name,
                elevation: 16,
                icon: const Icon(Icons.arrow_downward),
                validator: (value){
                  if (value == null || value.isEmpty){
                    return 'status cannot be empty';
                  }
                  else{
                    return null;
                  }
                },
                decoration: const InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'status'
                ),
              ),
              TextFormField(
                initialValue: widget.book.genre,
                decoration: const InputDecoration(
                    border: OutlineInputBorder(), labelText: 'genre'),
                onChanged: (value) {
                  setState(() {
                    genreState = value;
                  });
                },
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'genre cannot be empty';
                  }
                  return null;
                },
                autovalidateMode: AutovalidateMode.always,
              ),
              ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {

                      var auxTitle = titleState != '' ? titleState : widget.book.title;
                      var auxAuthor = authorState != '' ? authorState : widget.book.author;
                      var auxNrPages = nrPagesState != '' ? int.parse(nrPagesState) : widget.book.nrPages;
                      var auxStatus = statusState != '' ? Status.values.firstWhere((element) => element.value == statusState) : widget.book.status;
                      var auxGenre = genreState != '' ? genreState : widget.book.genre;

                      var altbook = Book(widget.book.id, auxTitle, auxAuthor, auxNrPages, auxStatus, auxGenre);

                      titleState = '';
                      authorState = '';
                      nrPagesState = '';
                      statusState = '';
                      genreState = '';
                      Navigator.pop(context, altbook);
                    }
                  },
                  child: const Text("Submit"))
            ],
          ),
        )
    );
  }
}


class UpdateBookScreen extends StatelessWidget {
  final Book book;
  const UpdateBookScreen(this.book,{super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Update book"),
        ),
        body: UpdateBookForm(book)
    );
  }
}
