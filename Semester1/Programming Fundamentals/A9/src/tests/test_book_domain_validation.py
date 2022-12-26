import unittest

from src.domain.book_class import Book
from src.exceptions.exceptions import ValidError
from src.validation.book_validator import BookValidator


class test_book_domain_validation(unittest.TestCase):
    def setUp(self) -> None:
        self._book_id = 1
        self._title = "Emma"
        self._author = "Jane Austen"
        self._test_book = Book(self._book_id, self._title, self._author)
        self._validator = BookValidator()
        self._book_id_invalid = -1
        self._test_book_bad = Book(self._book_id_invalid, self._title, self._author)

    def test_creation(self):
        assert self._test_book.book_id == self._book_id
        assert self._test_book.title == self._title
        assert self._test_book.author == self._author

    def test_string(self):
        assert str(self._test_book) == "Book 1. Emma by Jane Austen"

    def test_validation(self):
        assert self._validator.validate_book(self._test_book) is None

        try:
            self._validator.validate_book(self._test_book_bad)
        except ValidError as ve:
            assert str(ve) == "invalid book id\n"

    def tearDown(self) -> None:
        pass