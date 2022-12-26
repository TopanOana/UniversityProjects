import datetime
import unittest

import src.infrastructure.book_repo
from src.exceptions.exceptions import UndoRedoRepoError
from src.infrastructure.book_repo import BookRepository
from src.infrastructure.client_repo import ClientRepository
from src.infrastructure.rental_repo import RentalRepository
from src.infrastructure.undo_redo_repo import UndoRedoRepo
from src.services.book_service import BookService
from src.services.client_service import ClientService
from src.services.rental_service import RentalService
from src.services.undo_redo_service import UndoRedoService


class test_undo_redo_functionality(unittest.TestCase):
    def setUp(self) -> None:
        self._undo_redo_repo = UndoRedoRepo()
        self._undo_redo_service = UndoRedoService(self._undo_redo_repo)

        self._book_repo = BookRepository()
        self._book_service = BookService(self._book_repo, self._undo_redo_repo, src.infrastructure.book_repo.BookRepository)
        self._book_service.initialise_list()

        self._client_repo = ClientRepository()
        self._client_service = ClientService(self._client_repo, self._undo_redo_repo, src.infrastructure.client_repo.ClientRepository)
        self._client_service.initialise_list()

        self._rental_repo = RentalRepository()
        self._rental_service = RentalService(self._rental_repo, self._book_repo, self._client_repo,
                                             self._undo_redo_repo, src.infrastructure.rental_repo.RentalRepository)
        self._rental_service.initialise_list()

    def test_cannot_undo_further(self):
        try:
            self._undo_redo_service.undo()
        except UndoRedoRepoError as re:
            assert str(re) == "Cannot undo further"

    def test_cannot_redo_further(self):
        try:
            self._undo_redo_service.redo()
        except UndoRedoRepoError as re:
            assert str(re) == "Cannot redo further"

    def test_undo_redo_add_book(self):
        self._book_service.add_book_to_list("Test title", "test author")
        assert len(self._book_repo) == 11
        self._undo_redo_service.undo()
        assert len(self._book_repo) == 10
        self._undo_redo_service.redo()
        assert len(self._book_repo) == 11

    def test_undo_redo_remove_book(self):
        self._book_service.remove_book_from_list(1)
        assert len(self._book_repo) == 9
        self._undo_redo_service.undo()
        assert len(self._book_repo) == 10
        self._undo_redo_service.redo()
        assert len(self._book_repo) == 9

    def test_undo_redo_update_book(self):
        self._book_service.update_book_from_list(1, "Emma", "Jane Austen")
        assert str(self._book_repo[1]) == "Book 1. Emma by Jane Austen"
        self._undo_redo_service.undo()
        assert str(self._book_repo[1]) == "Book 1. Pride and Prejudice by Jane Austen"
        self._undo_redo_service.redo()
        assert str(self._book_repo[1]) == "Book 1. Emma by Jane Austen"

    def test_undo_redo_add_client(self):
        self._client_service.add_client_to_list("Dan")
        assert len(self._client_repo) == 11
        self._undo_redo_service.undo()
        assert len(self._client_repo) == 10
        self._undo_redo_service.redo()
        assert len(self._client_repo) == 11

    def test_undo_redo_remove_client(self):
        self._client_service.remove_client_from_list(1)
        assert len(self._client_repo) == 9
        self._undo_redo_service.undo()
        assert len(self._client_repo) == 10
        self._undo_redo_service.redo()
        assert len(self._client_repo) == 9

    def test_undo_redo_update_client(self):
        self._client_service.update_client_from_list(1, "Dan")
        assert str(self._client_repo[1]) == "Client 1 Dan"
        self._undo_redo_service.undo()
        assert str(self._client_repo[1]) == "Client 1 Oana"
        self._undo_redo_service.redo()
        assert str(self._client_repo[1]) == "Client 1 Dan"

    def test_undo_redo_rent_a_book(self):
        assert len(self._rental_repo) == 9
        self._rental_service.rent_a_book(5, 1)
        assert len(self._rental_repo) == 10
        self._undo_redo_service.undo()
        assert len(self._rental_repo) == 9
        self._undo_redo_service.redo()
        assert len(self._rental_repo) == 10

    def test_undo_redo_return_a_book(self):
        self._rental_service.return_a_book(1, 1)
        assert str(self._rental_repo[0].returned_date) == str(datetime.date.today())
        self._undo_redo_service.undo()
        assert self._rental_repo[0].returned_date is None
        self._undo_redo_service.redo()
        assert str(self._rental_repo[0].returned_date) == str(datetime.date.today())

    def test_undo_redo_delete_all_rentals_with_book_id(self):
        assert len(self._rental_repo) == 9
        self._rental_service.delete_all_rentals_with_book_id(1)
        assert len(self._rental_repo) == 7
        self._undo_redo_service.undo()
        assert len(self._rental_repo) == 9
        self._undo_redo_service.redo()
        assert len(self._rental_repo) == 7

    def test_undo_redo_delete_all_rentals_with_client_id(self):
        assert len(self._rental_repo) == 9
        self._rental_service.delete_all_rentals_with_client_id(8)
        assert len(self._rental_repo) == 7
        self._undo_redo_service.undo()
        assert len(self._rental_repo) == 9
        self._undo_redo_service.redo()
        assert len(self._rental_repo) == 7
