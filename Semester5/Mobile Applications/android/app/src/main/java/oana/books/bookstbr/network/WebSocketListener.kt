package oana.books.bookstbr.network

import android.util.Log
import oana.books.bookstbr.BooksViewModel
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class BooksWebSocketListener(private val viewModel: BooksViewModel) : WebSocketListener() {
    private var doASyncBoi:Boolean = false
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(BooksWebSocketListener::class.java.name, "onClosed");
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(this.javaClass.name, "DEAD")
        doASyncBoi = true
        viewModel.reconnect()
        super.onFailure(webSocket, t, response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        viewModel.initBooksFromServer()
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        viewModel.initBooksFromServer()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(this.javaClass.name, "WE ARE OPEN FOR BUSINESS PEOPLE")
        if (doASyncBoi){
            viewModel.synchronizedSynching()
        }
        viewModel.initBooksFromServer()


    }
}