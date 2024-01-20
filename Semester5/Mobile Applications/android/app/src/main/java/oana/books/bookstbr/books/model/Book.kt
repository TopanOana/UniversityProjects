package oana.books.bookstbr.books.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "nrPages") val nrPages: Int,
    @ColumnInfo(name = "status") val status: Status,
    @ColumnInfo(name = "genre") val genre: String
)
