import unittest

from src.domain.book_class import Book
from src.exceptions.exceptions import ValidError, RepoError
from src.infrastructure.book_repo import BookRepository
from src.infrastructure.undo_redo_repo import UndoRedoRepo
from src.services.book_service import BookService


class test_book_domain_validation(unittest.TestCase):
    def setUp(self) -> None:
        self.book_repo = BookRepository()
        self.undo_redo_repo = UndoRedoRepo()
        self.book_service = BookService(self.book_repo, self.undo_redo_repo)
        self.book1 = Book(1, "Pride and Prejudice", "Jane Austen")
        self.book2 = Book(2, "Emma", "Jane Austen")

    def test_add_book_repo(self):
        self.book_repo.add_book(self.book1)
        assert len(self.book_repo) == 1
        book_invalid_id = Book(-1, "Gatsby", "Scott")
        try:
            self.book_repo.add_book(book_invalid_id)
        except RepoError as re:
            assert str(re) == "Invalid id"
        book_id_already_used = Book(1, "Harry Potter", "Rowling")
        try:
            self.book_repo.add_book(book_id_already_used)
        except RepoError as re:
            assert str(re) == "Id already in use"

    def test_add_book_to_list_service(self):
        self.book_service.add_book_to_list(self.book1.title, self.book1.author)
        assert len(self.book_service.book_repo) == 1

    def test_remove_book_from_list(self):
        self.book_service.add_book_to_list(self.book1.title, self.book1.author)
        self.book_service.add_book_to_list(self.book2.title, self.book2.author)
        assert len(self.book_service.book_repo) == 2
        self.book_service.remove_book_from_list(1)
        assert len(self.book_service.book_repo) == 1
        try:
            self.book_service.remove_book_from_list(29)
        except RepoError as re:
            assert str(re) == "Book not found"
        try:
            self.book_service.remove_book_from_list("a")
        except RepoError as ve:
            assert str(ve) == "Invalid id"
        try:
            self.book_service.remove_book_from_list(-2)
        except RepoError as ve:
            assert str(ve) == "Invalid id"

    def test_update_book_from_list(self):
        self.book_service.add_book_to_list(self.book1.title, self.book1.author)
        self.book_service.add_book_to_list(self.book2.title, self.book2.author)
        assert str(self.book_service.book_repo[0]) == "Book 0. Pride and Prejudice by Jane Austen"
        self.book_service.update_book_from_list(0, "Jane Eyre", "Charlotte Bronte")
        assert str(self.book_service.book_repo[0]) == "Book 0. Jane Eyre by Charlotte Bronte"

        try:
            self.book_service.update_book_from_list(9, "mansfield park", "jane")
        except RepoError as ve:
            assert str(ve) == "Book not found"
        try:
            self.book_service.update_book_from_list(-8, "mansfield park", "jane")
        except RepoError as ve:
            assert str(ve) == "Invalid id"
        try:
            self.book_service.update_book_from_list("a", "mansfield park", "jane")
        except RepoError as ve:
            assert str(ve) == "Invalid id"

    def test_search_for_book_by_id(self):
        self.book_service.add_book_to_list(self.book1.title, self.book1.author)
        self.book_service.add_book_to_list(self.book2.title, self.book2.author)
        self.book_service.add_book_to_list("Ninth House", "Leigh Bardugo")
        result = self.book_service.search_for_book_by_id("1")
        assert len(result) == 1
        result = self.book_service.search_for_book_by_id("09")
        assert len(result) == 0

    def test_search_for_book_by_title(self):
        self.book_service.add_book_to_list(self.book1.title, self.book1.author)
        self.book_service.add_book_to_list(self.book2.title, self.book2.author)
        self.book_service.add_book_to_list("Pride and prejudice and zombies", "author")
        result = self.book_service.search_for_book_by_title("PRI")
        assert len(result) == 2
        result = self.book_service.search_for_book_by_title("Ninth")
        assert len(result) == 0

    def test_search_for_book_by_author(self):
        self.book_service.add_book_to_list(self.book1.title, self.book1.author)
        self.book_service.add_book_to_list(self.book2.title, self.book2.author)
        self.book_service.add_book_to_list("some title", "ernest hemingway")
        result = self.book_service.search_for_book_by_author("JANE")
        assert len(result) == 2
        result = self.book_service.search_for_book_by_author("ern")
        assert len(result) == 1

    def tearDown(self) -> None:
        pass