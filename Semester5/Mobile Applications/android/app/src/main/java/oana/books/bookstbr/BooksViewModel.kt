package oana.books.bookstbr

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import oana.books.bookstbr.books.model.Book
import oana.books.bookstbr.database.BookRepository
import oana.books.bookstbr.network.BASE_URL
import oana.books.bookstbr.network.BooksApi
import oana.books.bookstbr.network.BooksWebSocketListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket


class BooksViewModel(private val localRepository: BookRepository) : ViewModel() {

    private val booksApiService = BooksApi.retrofitService
    private val client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()
    private var webSocket: WebSocket? = null
    private val bookWebSocketListener: BooksWebSocketListener = BooksWebSocketListener(this)

    init {
        webSocket = client.newWebSocket(
            Request.Builder().url(BASE_URL + "/websocket").build(),
            bookWebSocketListener
        )
    }

    private var _books: Flow<List<Book>> = flow<List<Book>> {
        val f = localRepository.allBooks()
        emitAll(f)
    }.flowOn(Dispatchers.IO)
    val books: Flow<List<Book>> get() = _books

    var _hasInternet: Boolean = false
    val toAdd: ArrayList<Book> = arrayListOf()


    fun remove(bookID: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.delete(bookID)
            booksApiService.deleteBook(bookID)
        }


    fun add(book: Book) =
        viewModelScope.launch(Dispatchers.IO) {
//            if (_hasInternet) {
//                //call to api
//                val auxBook = booksApiService.addBook(book).body()
//                auxBook?.let { localRepository.insert(it) }
//            } else {
//                //add to the list of objects to add
//                localRepository.insert(book)
//                toAdd.add(book)
//            }
            try {
                val auxBook = booksApiService.addBook(book).body()
                auxBook?.let { localRepository.insert(it) }
            } catch (ex: Exception) {
                localRepository.insert(book)
                toAdd.add(book)
            }

        }


    fun update(book: Book) =
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.update(book)
            booksApiService.updateBook(book.id, book)
        }


    fun get(id: Int): Book? {
        return runBlocking {
            localRepository.getBook(id)
        }
    }

    fun changeInternetStatus(status: Boolean) {
        _hasInternet = status
        if (_hasInternet) {
            viewModelScope.launch(Dispatchers.IO) {
                toAdd.forEach { book ->
                    run {
                        ///TODO de facut mai bine aici ca nu ii tocmai kosher
                        val id = localRepository.getBookByTitle(book.title)
                        localRepository.delete(id)
                        book.id = 0
                        add(book)
                    }
                }
                toAdd.clear()
            }

        }
    }

    fun initBooksFromServer() {
        Log.d("HERE I AM BRO", "please")
        try {
            var books: List<Book> = emptyList()
            viewModelScope.launch(Dispatchers.IO) {
                books = booksApiService.getBooks()
                localRepository.deleteAll()
                books.forEach {
                    localRepository.insert(it)
                }
            }
        } catch (ex: Exception) {
            Log.e("BADBABDBABD", "init books from server error")
        }
    }

    fun synchronizedSynching(){
        toAdd.forEach {
            viewModelScope.launch(Dispatchers.IO){
                booksApiService.addBook(it)
            }
        }
    }

    fun reconnect() {
        viewModelScope.launch(Dispatchers.IO) {
            client.connectionPool.evictAll()
            webSocket = client.newWebSocket(
                Request.Builder().url(BASE_URL + "/websocket").build(),
                bookWebSocketListener
            )
        }
    }
}


class BookViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {

            return BooksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

