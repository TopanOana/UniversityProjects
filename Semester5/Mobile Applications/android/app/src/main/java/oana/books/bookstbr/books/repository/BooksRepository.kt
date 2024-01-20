package oana.books.bookstbr.books.repository

import androidx.compose.runtime.mutableStateListOf
import oana.books.bookstbr.books.model.Book
import oana.books.bookstbr.books.model.Status

class BooksRepository(private val books: MutableList<Book> = getAllBooks())  {

    fun getBooks() : MutableList<Book>{
        return books
    }

    companion object{
        private var instance : BooksRepository ?= null
        fun getInstance() : BooksRepository{
            if(instance==null)
                instance = BooksRepository()

            return instance as BooksRepository
        }
    }



}

private fun getAllBooks() = mutableStateListOf<Book>(
    Book(id = 1, title = "Pride and Prejudice", author = "Jane Austen", nrPages = 300, status = Status.READ, genre = "Classic"),
    Book(id = 2, title = "Emma", author = "Jane Austen", nrPages = 300, status = Status.READ, genre = "Classic"),
    Book(id = 3, title = "Sense and Sensibility", author = "Jane Austen", nrPages = 300, status = Status.UNREAD, genre = "Classic"),
    Book(id = 4, title = "Persuasion", author = "Jane Austen", nrPages = 300, status = Status.UNREAD, genre = "Classic"),
    Book(id = 5, title = "Northanger Abbey", author = "Jane Austen", nrPages = 300, status = Status.IN_PROGRESS, genre = "Classic"),
    Book(id = 6, title = "Jane Eyre", author = "Charlotte Bronte", nrPages = 300, status = Status.READ, genre = "Classic"),
    Book(id = 7, title = "The Great Gatsby", author = "F. Scott Fitzgerald", nrPages = 300, status = Status.IN_PROGRESS, genre = "Classic"),
    Book(id = 8, title = "Wuthering Heights", author = "Emily Bronte", nrPages = 300, status = Status.UNREAD, genre = "Classic"),
    Book(id = 9, title = "Dracula", author = "Bram Stoker", nrPages = 300, status = Status.UNREAD, genre = "Classic"),
    Book(id = 10, title = "For Whom the Bell Tolls", author = "Ernest Hemingway", nrPages = 300, status = Status.IN_PROGRESS, genre = "Classic")
)
