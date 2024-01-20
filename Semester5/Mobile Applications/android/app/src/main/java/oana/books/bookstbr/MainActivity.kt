package oana.books.bookstbr

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import oana.books.bookstbr.navigation.BookNavigation
import oana.books.bookstbr.ui.theme.BooksTBRTheme


//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel :BooksViewModel by viewModels { BookViewModelFactory((application as BooksApplication).localRepository) }
        setContent {
            BooksTBRTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    BooksApp(viewModel, this)
                }
            }
        }
    }
}


@Composable
fun BooksApp(booksViewModel: BooksViewModel, context: Context){
    BookNavigation(booksViewModel, context)
}
