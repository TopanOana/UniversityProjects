import 'package:books_tbr/model/BookService.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../model/Book.dart';

class AddBookForm extends StatefulWidget {
  const AddBookForm({super.key});

  @override
  AddBookFormState createState() => AddBookFormState();
}

class AddBookFormState extends State<AddBookForm> {
  var statusesPossible = <String>['READ', 'IN_PROGRESS', 'UNREAD'];
  var titleState = '';
  var authorState = '';
  var nrPagesState = '';
  var statusState = 'READ';
  var genreState = '';



  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
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
                value: statusState,
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

                      var book = Book(0, titleState, authorState, int.parse(nrPagesState), Status.values.firstWhere((element) => element.value == statusState), genreState);

                      titleState = '';
                      authorState = '';
                      nrPagesState = '';
                      statusState = '';
                      genreState = '';
                      Navigator.pop(context, book);
                    }
                  },
                  child: const Text("Submit"))
            ],
          ),
        )
    );
  }
}

class AddBookScreen extends StatelessWidget {
  const AddBookScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Add a book"),
        ),
        body: const AddBookForm()
    );
  }
}
