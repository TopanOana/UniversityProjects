"""
UI SECTION
"""
from src.infrastructure.book_repo import BookRepository
from src.infrastructure.client_repo import ClientRepository
from src.infrastructure.rental_repo import RentalRepository
from src.services.book_service import BookService
from src.services.client_service import ClientService
from src.services.rental_service import RentalService
from src.exceptions.exceptions import ValidError, UndoRedoRepoError, RepoError
from src.infrastructure.undo_redo_repo import UndoRedoRepo
from src.services.undo_redo_service import UndoRedoService


class UI:
    def __init__(self):
        """Undo/Redo service"""
        self._undo_redo_repo = UndoRedoRepo()
        self._undo_redo_service = UndoRedoService(self._undo_redo_repo)

        """Book repo/service initialisation"""
        self._book_repo = BookRepository()
        self._book_service = BookService(self._book_repo, self._undo_redo_repo)
        self._book_service.initialise_list()

        """Client repo/service initialiaion"""
        self._client_repo = ClientRepository()
        self._client_service = ClientService(self._client_repo, self._undo_redo_repo)
        self._client_service.initialise_list()

        """Rental repo/service initialisation"""
        self._rental_repo = RentalRepository()
        self._rental_service = RentalService(self._rental_repo, self._book_repo, self._client_repo, self._undo_redo_repo)
        self._rental_service.initialise_list()

    def print_main_menu(self):
        print("1. Manage clients and books")
        print("2. Rental options")
        print("3. Search for books or clients")
        print("4. Statistics")
        print("5. Undo/Redo Functionality")
        print("6. Exit")

    def print_first_menu(self):
        print("1. List all books")
        print("2. Add a new book")
        print("3. Remove a book")
        print("4. Update a book")
        print("5. List all clients")
        print("6. Add a new client")
        print("7. Remove a client")
        print("8. Update a client")
        print("9. Back to main menu")

    def choose_from_first_menu(self):
        self.print_first_menu()
        user_input = input("Choose an option: ").strip()
        if user_input == "1":
            self.list_books()
        elif user_input == "2":
            self.add_a_new_book()
        elif user_input == "3":
            self._remove_a_book()
        elif user_input == "4":
            self.update_book_data()
        elif user_input == "5":
            self.list_clients()
        elif user_input == "6":
            self.add_a_new_client()
        elif user_input == "7":
            self.remove_a_client()
        elif user_input == "8":
            self.update_a_client()
        elif user_input == "9":
            return
        else:
            print("unavailable option")

    def add_a_new_book(self):
        book_id = self._book_service.book_repo.last_book_id_used() + 1
        title = input("Title: ")
        author = input("Author: ")
        self._book_service.add_book_to_list(title, author)
        print("book added successfully")

    def list_books(self):
        list_of_books = self._book_repo.get_all_books()
        for book in list_of_books:
            print(str(book))

    def _remove_a_book(self):
        try:
            book_id = int(input("remove book with id: "))
        except ValueError:
            pass
        self._rental_service.delete_all_rentals_with_book_id(book_id)
        self._book_service.remove_book_from_list(book_id)
        print("book removed successfully")

    def update_book_data(self):
        try:
            book_id = int(input("update book with id: "))
        except ValueError as ve:
            raise ValueError(str(ve))
        title_new = input("new title: ")
        author_new = input("new author: ")
        self._book_service.update_book_from_list(book_id, title_new, author_new)
        print("book successfully updated")

    def list_clients(self):
        list_of_clients = self._client_repo.get_all_clients()
        for client in list_of_clients:
            print(str(client))

    def add_a_new_client(self):
        name = input("Name: ")
        self._client_service.add_client_to_list(name)
        print("client added successfully")

    def remove_a_client(self):
        try:
            client_id = int(input("remove client with id: "))
        except ValueError:
            pass
        self._rental_service.delete_all_rentals_with_client_id(client_id)
        self._client_service.remove_client_from_list(client_id)
        print("client removed successfully")

    def update_a_client(self):
        try:
            client_id = int(input("remove client with id: "))
        except ValueError:
            pass
        name_new = input("new name: ")
        self._client_service.update_client_from_list(client_id, name_new)
        print("client successfully updated")

    def print_second_menu(self):
        print("1. List all available books")
        print("2. Rent a book")
        print("3. Return a book")
        print("4. Show all rental data")
        print("5. Back to main menu")

    def choose_from_second_menu(self):
        self.print_second_menu()
        user_input = input("Choose an option: ").strip()
        if user_input == "1":
            self.list_all_available_books()
        elif user_input == "2":
            self.rent_a_book()
        elif user_input == "3":
            self.return_a_book()
        elif user_input == "4":
            self.show_rental_data()
        elif user_input == "5":
            return
        else:
            print("unavailable option")

    def list_all_available_books(self):
        list_of_available_books = self._rental_service.show_all_available_books()
        for book in list_of_available_books:
            print(str(book))

    def rent_a_book(self):
        try:
            book_id = int(input("Choose a book to rent: "))
            client_id = int(input("Which client is renting the book? "))
        except ValueError as ve:
            raise ve
        self._rental_service.rent_a_book(book_id, client_id)
        print("book rented successfully")

    def return_a_book(self):
        try:
            book_id = int(input("Choose a book to return: "))
            client_id = int(input("Which client is returning the book? "))
        except ValueError as ve:
            raise ve
        self._rental_service.return_a_book(book_id, client_id)
        print("book returned successfully")

    def show_rental_data(self):
        list_of_rentals = self._rental_repo.get_all_rentals()
        for rental in list_of_rentals:
            print(str(rental))

    def print_third_menu(self):
        print("1. Search for books")
        print("2. Search for clients")
        print("3. Back to main menu")

    def print_search_for_books_menu(self):
        print("1. Use id")
        print("2. Use title")
        print("3. Use author")
        print("4. Back to previous menu")

    def print_search_for_clients_menu(self):
        print("1. Use id")
        print("2. Use name")
        print("3. Back to previous menu")

    def choose_from_search_for_books_menu(self):
        self.print_search_for_books_menu()
        user_input = input("Choose an option: ").strip()
        if user_input == "1":
            self.search_for_books_by_id()
        elif user_input == "2":
            self.search_for_books_by_title()
        elif user_input == "3":
            self.search_for_books_by_author()
        elif user_input == "4":
            self.choose_from_third_menu()
        else:
            print("option unavailable")

    def choose_from_search_for_clients_menu(self):
        self.print_search_for_clients_menu()
        user_input = input("Choose an option: ").strip()
        if user_input == "1":
            self.search_for_clients_by_id()
        elif user_input == "2":
            self.search_for_clients_by_name()
        elif user_input == "3":
            self.choose_from_third_menu()
        else:
            print("option unavailable")

    def choose_from_third_menu(self):
        self.print_third_menu()
        user_input = input("Choose an option: ").strip()
        if user_input == "1":
            self.choose_from_search_for_books_menu()
        elif user_input == "2":
            self.choose_from_search_for_clients_menu()
        elif user_input == "3":
            return
        else:
            print("option unavailable")

    def search_for_books_by_id(self):
        partial_id = input("Search by id: ").strip()
        search_result = self._book_service.search_for_book_by_id(partial_id)
        if len(search_result) == 0:
            print("No books found")
        else:
            for book in search_result:
                print(str(book))

    def search_for_books_by_title(self):
        partial_title = input("Search by title: ").strip()
        search_result = self._book_service.search_for_book_by_title(partial_title)
        if len(search_result) == 0:
            print("No books found")
        else:
            for book in search_result:
                print(str(book))

    def search_for_books_by_author(self):
        partial_author = input("Search by author: ").strip()
        search_result = self._book_service.search_for_book_by_author(partial_author)
        if len(search_result) == 0:
            print("No books found")
        else:
            for book in search_result:
                print(str(book))

    def search_for_clients_by_id(self):
        partial_id = input("Search by id: ").strip()
        search_result = self._client_service.search_for_client_by_id(partial_id)
        if len(search_result) == 0:
            print("No clients found")
        else:
            for client in search_result:
                print(str(client))

    def search_for_clients_by_name(self):
        partial_name = input("Search by name: ").strip()
        search_result = self._client_service.search_for_client_by_name(partial_name)
        if len(search_result) == 0:
            print("No clients found")
        else:
            for client in search_result:
                print(str(client))

    def print_fourth_menu(self):
        print("1. Most rented books")
        print("2. Most active clients")
        print("3. Most rented author")
        print("4. Back to main menu")

    def choose_from_fourth_menu(self):
        self.print_fourth_menu()
        user_input = input("Choose an option: ").strip()
        if user_input == "1":
            self.statistic_most_rented_books()
        elif user_input == "2":
            self.statistic_most_active_clients()
        elif user_input == "3":
            self.statistic_most_rented_authors()
        elif user_input == "4":
            return
        else:
            print("option unavailable")

    def statistic_most_rented_books(self):
        most_rented_books = self._rental_service.statistic_most_rented_books()
        for book in most_rented_books:
            print(str(book.book))

    def statistic_most_active_clients(self):
        most_active_clients = self._rental_service.statistic_most_active_clients()
        for client in most_active_clients:
            print(str(client.client))

    def statistic_most_rented_authors(self):
        list_of_authors = self._rental_service.statistic_most_rented_authors()
        for i in range(0, len(list_of_authors)):
            print(list_of_authors[i].author)

    def print_fifth_menu(self):
        print("1. Undo last action")
        print("2. Redo previous action")
        print("3. Back to main menu")

    def choose_from_fifth_menu(self):
        self.print_fifth_menu()
        user_input = input("Choose an option: ").strip()
        if user_input == "1":
            self.undo_last_event()
        elif user_input == "2":
            self.redo_previous_event()
        elif user_input == "3":
            return
        else:
            print("option unavailable")

    def undo_last_event(self):
        self._undo_redo_service.undo()

    def redo_previous_event(self):
        self._undo_redo_service.redo()

    def start(self):
        while True:
            self.print_main_menu()
            user_option = input("Choose an option: ").strip()
            try:
                if user_option == "1":
                    self.choose_from_first_menu()
                elif user_option == "2":
                    self.choose_from_second_menu()
                elif user_option == "3":
                    self.choose_from_third_menu()
                elif user_option == "4":
                    self.choose_from_fourth_menu()
                elif user_option == "5":
                    self.choose_from_fifth_menu()
                elif user_option == "6":
                    return
                else:
                    print("option unavailable")
            except ValueError as ve:
                print(str(ve))
            except ValidError as ve:
                print(str(ve))
            except UndoRedoRepoError as re:
                print(str(re))
            except RepoError as re:
                print(str(re))
            print("")




ui = UI()
ui.start()
