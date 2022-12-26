from src.domain.book_class import Book
from src.domain.undo_classes import UndoEvent
from src.infrastructure.book_repo import BookRepository
from src.exceptions.exceptions import ValidError
from src.validation.book_validator import BookValidator


class BookService:
    def __init__(self, book_repo, undo_redo_repo):
        self.book_repo = book_repo
        self.undo_redo_repo = undo_redo_repo

    def add_book_to_list(self, title, author):
        """
        Adds a book to the list of books
        also validates the book
        :param book_id:
        :param title:
        :param author:
        :return:
        """
        book_id = self.book_repo.last_book_id_used() + 1
        book = Book(book_id, title, author)
        validator = BookValidator()
        validator.validate_book(book)
        self.book_repo.add_book(book)
        """for the undo/redo functionality"""
        undo_event = UndoEvent(BookRepository.remove_book, book_id, BookRepository.add_book, book, self.book_repo)
        self.undo_redo_repo.add_event_to_list(undo_event)

    def remove_book_from_list(self, book_id):
        """
        Removes a book from the list of books
        also checks whether the book exists (checks whether the id is unique)
        :param book_id:
        :return:
        """
        """for the undo/redo functionality"""
        undo_event = UndoEvent(BookRepository.add_book, self.book_repo[book_id], BookRepository.remove_book,
                               book_id, self.book_repo)
        self.undo_redo_repo.add_event_to_list(undo_event)
        """actual deletion"""
        self.book_repo.remove_book(book_id)

    def update_book_from_list(self, book_id, title_new, author_new):
        """
        Updates a book with a new title and author
        checks whether the book id is in the list of books
        :param book_id:
        :param title_new:
        :param author_new:
        :return:
        """
        redo_parameters = book_id, title_new, author_new
        if self.book_repo.unique_book_id(book_id) is False:
            """for the undo/redo functionality"""
            undo_parameters = book_id, self.book_repo[book_id].title, self.book_repo[book_id].author

            undo_event = UndoEvent(BookRepository.update_book, undo_parameters, BookRepository.update_book, redo_parameters,
                                   self.book_repo)
            self.undo_redo_repo.add_event_to_list(undo_event)
            """actual update"""
        self.book_repo.update_book(redo_parameters)

    def search_for_book_by_id(self, partial_book_id):
        """
        Returns a list of all the books that have the partial_book_id in their book_id
        :param partial_book_id:
        :return:
        """
        list_result_of_search = []
        list_of_books = self.book_repo.get_all_books()
        for book in list_of_books:
            if partial_book_id in str(book.book_id):
                list_result_of_search.append(book)
        return list_result_of_search

    def search_for_book_by_title(self, partial_title):
        """
        Returns a list of all the books that have the partial_title in their title
        :param partial_book_id:
        :return:
        """
        list_result_of_search = []
        partial_title = partial_title.lower()
        list_of_books = self.book_repo.get_all_books()
        for book in list_of_books:
            if partial_title in book.title.lower():
                list_result_of_search.append(book)
        return list_result_of_search

    def search_for_book_by_author(self, partial_author):
        """
        Returns a list of all the books that have the partial_author in their author
        :param partial_author:
        :return:
        """
        list_result_of_search = []
        partial_author = partial_author.lower()
        list_of_books = self.book_repo.get_all_books()
        for book in list_of_books:
            if partial_author in book.author.lower():
                list_result_of_search.append(book)
        return list_result_of_search

    def initialise_list(self):
        """ADDING BOOKS TO THE LIST OF BOOKS"""
        self.book_repo.add_book(Book(1, "Pride and Prejudice", "Jane Austen"))
        self.book_repo.add_book(Book(2, "The Picture of Dorian Gray", "Oscar Wilde"))
        self.book_repo.add_book(Book(3, "Great Expectations", "Charles Dickens"))
        self.book_repo.add_book(Book(4, "Fahrenheit 451", "Ray Bradbury"))
        self.book_repo.add_book(Book(5, "The Great Gatsby", "F. Scott Fitzgerald"))
        self.book_repo.add_book(Book(6, "A Christmas Carol", "Charles Dickens"))
        self.book_repo.add_book(Book(7, "Ship of Magic", "Robin Hobb"))
        self.book_repo.add_book(Book(8, "The Invisible Life of Addie LaRue", "V.E. Schwab"))
        self.book_repo.add_book(Book(9, "Turtles All the Way Down", "John Green"))
        self.book_repo.add_book(Book(10, "Ninth House", "Leigh Bardugo"))
