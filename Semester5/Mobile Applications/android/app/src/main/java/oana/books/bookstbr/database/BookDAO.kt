package oana.books.bookstbr.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import oana.books.bookstbr.books.model.Book

@Dao
interface BookDAO {
    @Query("SELECT * FROM book_table")
    fun getAllBooks(): Flow<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Query("DELETE FROM book_table WHERE id= :book_id")
    suspend fun delete(book_id: Int)

    @Update()
    suspend fun update(book: Book)

    @Query("SELECT * FROM BOOK_TABLE WHERE id = :book_id")
    suspend fun getBook(book_id: Int): Book?

    @Query("SELECT id FROM BOOK_TABLE WHERE title = :title")
    suspend fun getBookByTitle(title: String) : Int

    @Query("DELETE FROM book_table")
    suspend fun deleteAll()

}