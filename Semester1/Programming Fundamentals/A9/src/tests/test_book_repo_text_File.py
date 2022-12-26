import unittest

from src.domain.book_class import Book
from src.exceptions.exceptions import ValidError, RepoError
from src.infrastructure.book_repo import BookRepository, BookRepositoryTextFile


class test_book_repo_text_file(unittest.TestCase):
    def setUp(self) -> None:
        self.book_repo = BookRepositoryTextFile("test_book_repo.txt")
        self.book1 = Book(1, "Emma", "Jane Austen")
        self.book2 = Book(2, "The Great Gatsby", "F. Scott Fitzgerald")

    def test_read_all_books(self):
        all_books = self.book_repo.get_all_books()
        assert len(all_books) == 1

    def test_add_books(self):
        #self.book_repo.add_book(self.book1)
        assert len(self.book_repo) == 2
        self.book_repo.add_book(self.book2)
        assert len(self.book_repo) == 3

    def test_remove_books(self):
        self.book_repo.remove_book(1)
        assert len(self.book_repo) == 2
