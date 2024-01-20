package oana.books.bookstbr

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import oana.books.bookstbr.database.BookRepository
import oana.books.bookstbr.database.BookRoomDatabase



class BooksApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { BookRoomDatabase.getDatabase(this, applicationScope) }
    val localRepository by lazy { BookRepository(database.bookDao()) }


}