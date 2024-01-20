package oana.books.bookstbr.books

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import oana.books.bookstbr.BooksViewModel
import oana.books.bookstbr.R
import oana.books.bookstbr.ToastNoInternet
import oana.books.bookstbr.books.model.Book
import oana.books.bookstbr.checkForInternet
import oana.books.bookstbr.ui.theme.BooksTBRTheme


@Composable
fun BookCard(
    book: Book,
    onUpdateClick: (Int) -> Unit,
    deleteItemHandler: () -> Unit,
    context: Context
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var openDialogDelete by remember { mutableStateOf(false) }
    var openDialogNoInternet by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp, label = ""
    )
    Surface(
        color = MaterialTheme.colorScheme.inversePrimary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(book.title + " by " + book.author, fontWeight = FontWeight.Bold)
                if (expanded) {
                    Column() {
                        Text(book.nrPages.toString() + " pages")
                        Text(book.genre)
                        Text("status: " + book.status.name)
                        Row() {
                            Button(onClick = {
                                onUpdateClick(book.id)
                            }) {
                                Icon(
                                    Icons.Rounded.Edit,
                                    contentDescription = stringResource(R.string.update_book)
                                )
                            }
                            Button(onClick =
                            {
                                if (checkForInternet(context)) {
                                    openDialogDelete = true

                                } else
                                    openDialogNoInternet = true
                            }

                            ) {
                                Icon(
                                    Icons.Rounded.Delete,
                                    contentDescription = stringResource(R.string.delete_book)
                                )
                            }

                        }
                    }
                }
                ElevatedButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Show less" else "Show more")
                }

            }
        }
        if (openDialogNoInternet) {
            ToastNoInternet(
                onDismiss = { openDialogNoInternet = false }
            )
        }
        if (openDialogDelete) {
            ToastOnDelete(
                bookTitle = book.title,
                deleteItemHandler,
                onDismiss = { openDialogDelete = false })
        }

    }
}

@Composable
fun Listing(
    booksViewModel: BooksViewModel,
    onUpdateClick: (Int) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    val items = booksViewModel.books.collectAsState(initial = emptyList())
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = items.value, key = { book -> book.id }) { book ->
            BookCard(book = book, onUpdateClick = onUpdateClick, deleteItemHandler = {
                booksViewModel.remove(book.id)
                Log.d("delete handler", "wowow")
            }, context)
        }
    }
}


@Composable
fun ToastOnDelete(bookTitle: String, deleteItemHandler: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { deleteItemHandler() }) {
                Text("Delete book")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Don't delete book")
            }
        },
        title = {
            Text("Are you sure you want to delete the book $bookTitle? ")
        },
        text = {
            Text("This change is permanent.")
        }
    )
}


//@Preview(showBackground = true)
@Composable
fun BooksListScreen(
    booksViewModel: BooksViewModel,
    context: Context,
    modifier: Modifier = Modifier,
    onAddClick: (String) -> Unit,
    onUpdateClick: (Int) -> Unit
) {

    BooksTBRTheme {
        Column() {
            Button(onClick = {
                Log.d("list screen", "in the add book button on click")
                onAddClick("")

            }) {
                Icon(Icons.Rounded.Add, contentDescription = stringResource(id = R.string.add_book))
            }
            Listing(booksViewModel, onUpdateClick, context)
        }

    }
}
