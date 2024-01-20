package oana.books.bookstbr.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import oana.books.bookstbr.BooksViewModel
import oana.books.bookstbr.addBook.AddBookScreen
import oana.books.bookstbr.books.BooksListScreen
import oana.books.bookstbr.updateBook.UpdateBookScreen

@Composable
fun BookNavigation(booksViewModel: BooksViewModel, context: Context) {
    val navController = rememberNavController()


    NavHost(navController = navController,
        startDestination = Screen.Books.route)
    {
        composable(
            route = Screen.Books.route
        ) {
            BooksListScreen(
                booksViewModel,
                context,
                onAddClick = {
                    Log.d("navigation", "in the navigation on add click")
                    navController.navigate(Screen.AddBook.route)
                },
                onUpdateClick = { selectedBook : Int ->
                    navController.navigate("${Screen.UpdateBook.route}/$selectedBook")
                }
            )
        }
        composable(
            route = "${Screen.UpdateBook.route}/{selectedBook}",
            arguments = listOf(navArgument("selectedBook") { type = NavType.IntType })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("selectedBook")?.let { book ->
                UpdateBookScreen(selectedBook = book, navController, booksViewModel, context)
            }
        }
        composable(
            route = Screen.AddBook.route
        ){
            AddBookScreen(navController, booksViewModel)
        }

    }
}