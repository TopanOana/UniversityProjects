package oana.books.bookstbr.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import oana.books.bookstbr.books.model.Book
import oana.books.bookstbr.books.model.Status

@Database(entities = [Book::class], version = 1, exportSchema = false)
public abstract class BookRoomDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDAO


    private class BookDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    var bookDAO = database.bookDao()

                    var list = listOf(
                        Book(
                            id = 1,
                            title = "Pride and Prejudice",
                            author = "Jane Austen",
                            nrPages = 300,
                            status = Status.READ,
                            genre = "Classic"
                        ),
                        Book(
                            id = 2,
                            title = "Emma",
                            author = "Jane Austen",
                            nrPages = 300,
                            status = Status.READ,
                            genre = "Classic"
                        ),
                        Book(
                            id = 3,
                            title = "Sense and Sensibility",
                            author = "Jane Austen",
                            nrPages = 300,
                            status = Status.UNREAD,
                            genre = "Classic"
                        ),
                        Book(
                            id = 4,
                            title = "Persuasion",
                            author = "Jane Austen",
                            nrPages = 300,
                            status = Status.UNREAD,
                            genre = "Classic"
                        ),
                        Book(
                            id = 5,
                            title = "Northanger Abbey",
                            author = "Jane Austen",
                            nrPages = 300,
                            status = Status.IN_PROGRESS,
                            genre = "Classic"
                        ),
                        Book(
                            id = 6,
                            title = "Jane Eyre",
                            author = "Charlotte Bronte",
                            nrPages = 300,
                            status = Status.READ,
                            genre = "Classic"
                        ),
                        Book(
                            id = 7,
                            title = "The Great Gatsby",
                            author = "F. Scott Fitzgerald",
                            nrPages = 300,
                            status = Status.IN_PROGRESS,
                            genre = "Classic"
                        ),
                        Book(
                            id = 8,
                            title = "Wuthering Heights",
                            author = "Emily Bronte",
                            nrPages = 300,
                            status = Status.UNREAD,
                            genre = "Classic"
                        ),
                        Book(
                            id = 9,
                            title = "Dracula",
                            author = "Bram Stoker",
                            nrPages = 300,
                            status = Status.UNREAD,
                            genre = "Classic"
                        ),
                        Book(
                            id = 10,
                            title = "For Whom the Bell Tolls",
                            author = "Ernest Hemingway",
                            nrPages = 300,
                            status = Status.IN_PROGRESS,
                            genre = "Classic"
                        )
                    )

                    list.forEach {
                        bookDAO.insert(it)
                    }

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: BookRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): BookRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookRoomDatabase::class.java,
                    "book_database"
                )
                    .addCallback(BookDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}