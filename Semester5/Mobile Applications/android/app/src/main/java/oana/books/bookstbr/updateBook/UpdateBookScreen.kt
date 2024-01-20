package oana.books.bookstbr.updateBook

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oana.books.bookstbr.BooksViewModel
import oana.books.bookstbr.ToastNoInternet
import oana.books.bookstbr.books.model.Book
import oana.books.bookstbr.books.model.Status
import oana.books.bookstbr.checkForInternet
import oana.books.bookstbr.navigation.Screen
import oana.books.bookstbr.ui.theme.BooksTBRTheme
import oana.books.bookstbr.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBookForm(
    selectedBook: Int,
    navController: NavController,
    booksViewModel: BooksViewModel,
    context: Context
) {
    val book = booksViewModel.get(selectedBook)!!

    var openDialogNoInternet by rememberSaveable { mutableStateOf(false) }

    var titleState by rememberSaveable { mutableStateOf(book.title) }
    var authorState by rememberSaveable { mutableStateOf(book.author) }
    var nrPagesState by rememberSaveable { mutableStateOf(book.nrPages.toString()) }
    var statusState by rememberSaveable { mutableStateOf(book.status.name) }
    var genreState by rememberSaveable { mutableStateOf(book.genre) }
    var expandedDropDown by remember { mutableStateOf(false) }

    val statusesPossible = listOf(Status.READ, Status.IN_PROGRESS, Status.UNREAD)
    val icon = if (expandedDropDown)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Row() {
            OutlinedTextField(
                value = titleState,
                onValueChange = { newTitle -> titleState = newTitle },
                label = { Text(text = "Title") },
                supportingText = {
                    if (titleState.isEmpty())
                        Text("title cannot be empty", color = Color.Red)
                },
                isError = titleState.isEmpty()
            )

        }
        Row() {
            OutlinedTextField(
                value = authorState,
                onValueChange = { newAuthor -> authorState = newAuthor },
                label = { Text(text = "Author") },
                supportingText = {
                    if (authorState.isEmpty())
                        Text("author cannot be empty", color = Color.Red)
                },
                isError = authorState.isEmpty()
            )
        }
        Row() {
            OutlinedTextField(
                value = nrPagesState.toString(),
                onValueChange = { newNrPages -> nrPagesState = newNrPages },
                label = { Text(text = "Number of Pages") },
                supportingText = {
                    if (nrPagesState.isEmpty() && !nrPagesState.matches(Regex("[1-9][0-9]*")))
                        Text("number of pages must be a number greater than 0")
                },
                isError = nrPagesState.isEmpty() || !nrPagesState.matches(Regex("[1-9][0-9]*")),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }

        Row() {
            ExposedDropdownMenuBox(
                expanded = expandedDropDown,
                onExpandedChange = { expandedDropDown = it }) {
                OutlinedTextField(
                    value = statusState,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        TrailingIcon(expanded = expandedDropDown)
                    },
                    modifier = Modifier.menuAnchor(),
                    label = { Text("Status") },
                    supportingText = {
                        if (statusState.isEmpty())
                            Text("status cannot be empty", color = Color.Red)
                    },
                    isError = statusState.isEmpty()
                )

                ExposedDropdownMenu(
                    expanded = expandedDropDown,
                    onDismissRequest = { expandedDropDown = false }) {
                    statusesPossible.forEach {
                        DropdownMenuItem(text = { Text(it.name) },
                            onClick = {
                                statusState = it.name
                                expandedDropDown = false
                            })
                    }
                }
            }
        }
        Row() {
            OutlinedTextField(
                value = genreState,
                onValueChange = { newGenre -> genreState = newGenre },
                label = { Text(text = "Genre") },
                supportingText = {
                    if (genreState.isEmpty())
                        Text("genre cannot be empty", color = Color.Red)
                },
                isError = genreState.isEmpty()
            )
        }
        Button(
            onClick = {
                if (checkForInternet(context)) {
                    booksViewModel.update(
                        Book(
                            id = book.id,
                            title = titleState.toString(),
                            author = authorState,
                            nrPages = nrPagesState.toInt(),
                            status = Status.valueOf(statusState),
                            genre = genreState
                        )
                    )
                    navController.navigate(Screen.Books.route)
                } else
                    openDialogNoInternet = true

            },
            enabled =
            !(titleState.isEmpty() || authorState.isEmpty() || nrPagesState.isEmpty() || !nrPagesState.matches(
                Regex("[1-9][0-9]*")
            ) || statusState.isEmpty() || genreState.isEmpty())
        ) {
            Icon(Icons.Rounded.Save, contentDescription = null)
        }
    }
    if (openDialogNoInternet)
        ToastNoInternet(onDismiss = { openDialogNoInternet = false })

}


//@Preview
@Composable
fun UpdateBookScreen(
    selectedBook: Int,
    navController: NavController,
    booksViewModel: BooksViewModel,
    context: Context
) {
    BooksTBRTheme {
        Surface(
            color = MaterialTheme.colorScheme.inversePrimary
        ) {
            Column {
                Row(modifier = Modifier.wrapContentSize(align = Alignment.Center)) {
                    Text(
                        text = "Update book",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .wrapContentSize(align = Alignment.Center)
                            .padding(15.dp)
                    )
                }
                UpdateBookForm(selectedBook, navController, booksViewModel, context)
            }
        }
    }

}