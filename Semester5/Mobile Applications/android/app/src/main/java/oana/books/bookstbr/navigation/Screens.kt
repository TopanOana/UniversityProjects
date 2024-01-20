package oana.books.bookstbr.navigation

sealed class Screen(val route: String) {
    object Books : Screen(route = "bookslist")
    object AddBook : Screen(route = "addbook")
    object UpdateBook : Screen(route = "updateBook")

}