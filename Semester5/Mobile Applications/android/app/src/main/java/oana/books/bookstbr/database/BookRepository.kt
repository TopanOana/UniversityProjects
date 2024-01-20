package oana.books.bookstbr.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import oana.books.bookstbr.books.model.Book

class BookRepository(private val bookDAO: BookDAO) {

    @WorkerThread
    suspend fun allBooks(): Flow<List<Book>> {
        return bookDAO.getAllBooks()
    }

    @WorkerThread
    suspend fun insert(book: Book) {
        bookDAO.insert(book)
    }

    @WorkerThread
    suspend fun update(book: Book) {
        bookDAO.update(book)
    }

    @WorkerThread
    suspend fun delete(book_id: Int) {
        bookDAO.delete(book_id)
    }

    @WorkerThread
    suspend fun getBook(book_id: Int): Book? {
        return bookDAO.getBook(book_id)
    }

    @WorkerThread
    suspend fun getBookByTitle(title: String) : Int{
        return bookDAO.getBookByTitle(title)
    }

    @WorkerThread
    suspend fun deleteAll(){
        bookDAO.deleteAll()
    }
}