import unittest

import src.infrastructure.book_repo
from src.infrastructure.book_repo import BookRepository
from src.infrastructure.client_repo import ClientRepository
from src.infrastructure.rental_repo import RentalRepository
from src.infrastructure.undo_redo_repo import UndoRedoRepo
from src.services.book_service import BookService
from src.services.client_service import ClientService
from src.services.rental_service import RentalService
from datetime import date


class test_rental_repo_service(unittest.TestCase):
    def setUp(self) -> None:
        self._book_repo = BookRepository()
        self._undo_redo_repo = UndoRedoRepo()
        self._book_service = BookService(self._book_repo, self._undo_redo_repo, src.infrastructure.book_repo.BookRepository)
        self._book_service.initialise_list()
        self._client_repo = ClientRepository()
        self._client_service = ClientService(self._client_repo, self._undo_redo_repo, src.infrastructure.book_repo.BookRepository)
        self._client_service.initialise_list()
        self._rental_repo = RentalRepository()
        self._rental_service = RentalService(self._rental_repo, self._book_repo, self._client_repo, self._undo_redo_repo, src.infrastructure.rental_repo.RentalRepository)

    def test_rent_a_book(self):
        self._rental_service.rent_a_book(1, 1)
        date_current = date.today()
        assert str(self._rental_service._rental_repo[0]) == "Rental 0 Book id: 1 Client id: 1 Rented date : " + str(date_current) + " not returned yet."
        try:
            self._rental_service.rent_a_book(-1, 1)
        except ValueError as ve:
            assert str(ve) == "invalid book id"
        try:
            self._rental_service.rent_a_book(1, -1)
        except ValueError as ve:
            assert str(ve) == "invalid client id"
        try:
            self._rental_service.rent_a_book(1, 1)
        except ValueError as ve:
            assert str(ve) == "unavailable book"

    def test_return_a_book(self):
        self._rental_service.rent_a_book(1, 1)
        self._rental_service.return_a_book(1, 1)
        date_current = date.today()
        assert str(self._rental_service._rental_repo[0]) == "Rental 0 Book id: 1 Client id: 1 Rented date : " + str(date_current) + " Returned date: " + str(date_current)
        self._rental_service.rent_a_book(6, 2)
        try:
            self._rental_service.return_a_book(1, 1)
        except ValueError as ve:
            assert str(ve) == "book not currently rented"
        try:
            self._rental_service.return_a_book(-1, 1)
        except ValueError as ve:
            assert str(ve) == "invalid book id"
        try:
            self._rental_service.return_a_book(1, -1)
        except ValueError as ve:
            assert str(ve) == "invalid client id"
        try:
            self._rental_service.return_a_book(6, 3)
        except ValueError as ve:
            assert str(ve) == "book has not been rented by this client"

    def test_add_a_rental(self):
        self._rental_service.add_a_rental(1, 1, "2021-11-03", "2021-11-22")
        assert str(self._rental_repo[0]) == "Rental 0 Book id: 1 Client id: 1 Rented date : 2021-11-03 Returned date: 2021-11-22"
        try:
            self._rental_service.add_a_rental(-1, 1, "2021-11-03", "2021-11-22")
        except ValueError as ve:
            assert str(ve) == "invalid book id"
        try:
            self._rental_service.add_a_rental(1, -1, "2021-11-03", "2021-11-22")
        except ValueError as ve:
            assert str(ve) == "invalid client id"

    def test_show_all_available_books(self):
        new_list = self._rental_service.show_all_available_books()
        assert len(new_list) == 10
        self._rental_service.add_a_rental(1, 1, "2021-11-22", None)
        new_list = self._rental_service.show_all_available_books()
        assert len(new_list) == 9

    def test_delete_all_rentals_with_book_id(self):
        self._rental_service.add_a_rental(1, 1, "2021-11-22", None)
        self._rental_service.add_a_rental(1, 3, "2021-10-22", "2021-10-28")
        self._rental_service.add_a_rental(2, 5, "2021-11-22", None)
        assert len(self._rental_repo) == 3
        self._rental_service.delete_all_rentals_with_book_id(1)
        assert len(self._rental_repo) == 1
        try:
            self._rental_service.delete_all_rentals_with_book_id("a")
        except ValueError as ve:
            assert str(ve) == "invalid book id"
        try:
            self._rental_service.delete_all_rentals_with_book_id(100)
        except ValueError as ve:
            assert str(ve) == "invalid book id"
        try:
            self._rental_service.delete_all_rentals_with_book_id(-8)
        except ValueError as ve:
            assert str(ve) == "invalid book id"

    def test_delete_all_rentals_with_client_id(self):
        self._rental_service.add_a_rental(1, 1, "2021-11-22", None)
        self._rental_service.add_a_rental(2, 1, "2021-10-22", "2021-10-28")
        self._rental_service.add_a_rental(2, 5, "2021-11-22", None)
        assert len(self._rental_repo) == 3
        self._rental_service.delete_all_rentals_with_client_id(1)
        assert len(self._rental_repo) == 1
        try:
            self._rental_service.delete_all_rentals_with_client_id("a")
        except ValueError as ve:
            assert str(ve) == "invalid client id"
        try:
            self._rental_service.delete_all_rentals_with_client_id(100)
        except ValueError as ve:
            assert str(ve) == "invalid client id"
        try:
            self._rental_service.delete_all_rentals_with_client_id(-8)
        except ValueError as ve:
            assert str(ve) == "invalid client id"

    def test_initialise_list(self):
        assert len(self._rental_repo) == 0
        self._rental_service.initialise_list()
        assert len(self._rental_repo) == 9

    def test_number_of_times_book_rented(self):
        self._rental_service.initialise_list()
        assert self._rental_service.number_of_times_book_was_rented(1) == 2
        try:
            self._rental_service.number_of_times_book_was_rented(-2)
        except ValueError as ve:
            assert str(ve) == "invalid book id"

    def test_most_rented_books(self):
        self._rental_service.initialise_list()
        list_of_rented_books_numbered = self._rental_service.statistic_most_rented_books()
        assert len(list_of_rented_books_numbered) == len(self._book_repo)
        assert list_of_rented_books_numbered[0].number_of_rentals == 2
        assert str(list_of_rented_books_numbered[0].book) == "Book 1. Pride and Prejudice by Jane Austen"
        assert str(list_of_rented_books_numbered[9].book) == "Book 8. The Invisible Life of Addie LaRue by V.E. Schwab"

    def test_number_of_times_client_rented(self):
        self._rental_service.initialise_list()
        assert self._rental_service.number_of_times_client_was_active(3) == 6
        assert self._rental_service.number_of_times_client_was_active(8) == 8
        try:
            self._rental_service.number_of_times_client_was_active(-9)
        except ValueError as ve:
            assert str(ve) == "invalid client id"

    def test_most_active_clients(self):
        self._rental_service.initialise_list()
        list_of_clients_active = self._rental_service.statistic_most_active_clients()
        assert len(list_of_clients_active) == len(self._client_repo)
        assert str(list_of_clients_active[0].client) == "Client 9 Bianca"
        assert str(list_of_clients_active[9].client) == "Client 10 Alex"

    def test_most_rented_author(self):
        self._rental_service.initialise_list()
        list_of_authors_rented = self._rental_service.statistic_most_rented_authors()

        for i in range(0, len(list_of_authors_rented)):
            string = list_of_authors_rented[i].author + " " + str(list_of_authors_rented[i].number_of_rentals)
            print(string)

        assert list_of_authors_rented[0].author + " " + str(list_of_authors_rented[0].number_of_rentals) == "Jane Austen 2"
        #assert list_of_authors_rented[8].author + " " + str(list_of_authors_rented[8].number_of_rentals) == "V.E. Schwab 0"

    def test_check_book_availability(self):
        self._rental_service.initialise_list()
        assert self._rental_service.check_book_availability(1) is False
        try:
            self._rental_service.check_book_availability(-9)
        except ValueError as ve:
            assert str(ve) == "invalid book id"

    def test_check_client_book_rented(self):
        self._rental_service.initialise_list()
        assert self._rental_service.check_client_book_rented(1, 1) is True
        try:
            self._rental_service.check_client_book_rented(-1, 0)
        except ValueError as ve:
            assert str(ve) == "invalid book id"
        try:
            self._rental_service.check_client_book_rented(1, -9)
        except ValueError as ve:
            assert str(ve) == "invalid client id"

    def test_back_to_None_returned_date(self):
        self._rental_service.add_a_rental(1, 3, "2021-10-22", "2021-10-28")
        assert str(self._rental_repo[0].returned_date) == "2021-10-28"
        parameters = 1, 3, "2021-10-28"
        self._rental_repo.back_to_none_return_date(parameters)
        assert self._rental_repo[0].returned_date is None

    def tearDown(self) -> None:
        pass
