from src.services.services import Service
from src.services.history import History
import copy
from src.domain.domain import Book


class UI:
    """
    UI Class
    """

    def __init__(self):
        self.__menu = "1. Add a book" + "\n" + "2. List all books" + "\n" + "3. Filter books without a word" + "\n" + "4. Undo last operation" + "\n" + "5. Exit" + "\n "

    def _print_menu(self):
        print(self.__menu)

    def _add_a_book(self, services):
        isbn = input("isbn: ")
        title = input("title: ")
        author = input("author: ")
        services._add_book_to_list(isbn.strip(), author.strip(), title.strip())

    def _list_all_books(self, services):
        list_to_print = services._all_books_list()
        for b in list_to_print:
            print(str(b))

    def _filter_books_with_a_word(self, services):
        word = input("Delete all books that start with this word: ")
        services._filter_books_with_word(word)

    def _undo_last_operation(self, history):
        history._remove_last_list()

    def start(self):
        services = Service()
        history = History()
        history._add_a_list(services.list)
        while True:
            self._print_menu()
            user_option = input("Choose an option: ")
            try:
                if user_option == "1":
                    self._add_a_book(services)
                    history._add_a_list(services.list)
                elif user_option == "2":
                    self._list_all_books(services)
                elif user_option == "3":
                    self._filter_books_with_a_word(services)
                    history._add_a_list(services.list)
                elif user_option == "4":
                    self._undo_last_operation(history)
                    services.list = copy.deepcopy(history._history_of_lists[-1])
                elif user_option == "5":
                    return
                else:
                    raise ValueError("invalid command")

            except ValueError as ve:
                print(str(ve))


"""ui = UI()
ui.start()"""
