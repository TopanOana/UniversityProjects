package oana.books.bookstbr.network

import oana.books.bookstbr.books.model.Book
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

const val BASE_URL = "http://172.30.114.166:8080"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BooksApiService {
    @GET("books")
    suspend fun getBooks(): List<Book>

    @DELETE("delete/{id}")
    suspend fun deleteBook(@Path("id") id: Int)

    @PUT("update/{id}")
    suspend fun updateBook(@Path("id") id: Int, @Body book: Book): Response<Book>

    @POST("add")
    suspend fun addBook(@Body book: Book): Response<Book>

}

object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}