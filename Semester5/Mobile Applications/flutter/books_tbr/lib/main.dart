import 'package:books_tbr/model/BookService.dart';
import 'package:books_tbr/widgets/ListViewScreen.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
      title: "BooksTBR",
      home: ListViewScreen(BookService()),
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      )));
}
