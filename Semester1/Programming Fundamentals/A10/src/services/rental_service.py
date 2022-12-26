from src.domain.rental_class import Rental
from datetime import date

from src.domain.undo_classes import UndoEvent, ComplexUndoEvent
from src.infrastructure.rental_repo import RentalRepository
from src.validation.rental_validator import RentalValidator


class Author_Rented:
    def __init__(self, author, number_of_rentals):
        self.__author = author
        self.__number_of_rentals = number_of_rentals

    def __eq__(self, other):
        return self.author == other.author and self.number_of_rentals == other.number_of_rentals

    @property
    def author(self):
        return self.__author

    @property
    def number_of_rentals(self):
        return self.__number_of_rentals


class Book_Rented:
    def __init__(self, book, number_of_rentals):
        self.__book = book
        self.__number_of_rentals = number_of_rentals

    def __str__(self):
        return str(self.__book) + " " + str(self.__number_of_rentals)

    @property
    def book(self):
        return self.__book

    @property
    def number_of_rentals(self):
        return self.__number_of_rentals


class Client_Active:
    def __init__(self, client, number_of_rentals):
        self.__client = client
        self.__number_of_rentals = number_of_rentals

    @property
    def client(self):
        return self.__client

    @property
    def number_of_rentals(self):
        return self.__number_of_rentals


class RentalService:
    def __init__(self, rental_repo, book_repo, client_repo, undo_redo_repo, repo_access):
        self._rental_repo = rental_repo
        self._book_repo = book_repo
        self._client_repo = client_repo
        self._undo_redo_repo = undo_redo_repo
        self.repo_access = repo_access

    def check_book_availability(self, book_id):
        """
        Checks whether a book is available by going through all rentals and verifying that if the book was rented, it
        was also returned
        :param book_id:
        :return:
        """
        if self._book_repo.unique_book_id(book_id):
            raise ValueError("invalid book id")
        return self._rental_repo.check_availability_book(book_id)

    def check_client_book_rented(self, book_id, client_id):
        """
        Checks whether a specific client has rented a specific book and the return date is None
        :param book_id:
        :param client_id:
        :return:
        """
        if self._book_repo.unique_book_id(book_id):
            raise ValueError("invalid book id")
        elif self._client_repo.unique_client_id(client_id):
            raise ValueError("invalid client id")
        return self._rental_repo.check_book_rented_by_client(book_id, client_id)

    def show_all_available_books(self):
        """
        Returns a list of all available books
        :return:
        """
        available_books = []
        list_of_books = self._book_repo.get_all_books()
        for book in list_of_books:
            if self.check_book_availability(book.id):
                available_books.append(book)
        return available_books

    def rent_a_book(self, book_id, client_id):
        """
        Adds a rental to the rental list where the client rented a book on the current date
        :param book_id:
        :param client_id:
        :return:
        """
        if self._book_repo.unique_book_id(book_id):
            raise ValueError("invalid book id")
        elif self._client_repo.unique_client_id(client_id):
            raise ValueError("invalid client id")
        else:
            if not self.check_book_availability(book_id):
                raise ValueError("unavailable book")
            else:
                rental_id = self._rental_repo.last_rental_id_used() + 1
                rented_date = date.today()
                rental_new = Rental(rental_id, book_id, client_id, rented_date)
                validator = RentalValidator()
                validator.validate_rental(rental_new)
                self._rental_repo.rent_a_book(rental_new)
                """for the undo/redo functionality"""
                undo_event = UndoEvent(self.repo_access.delete_rental, rental_new, self.repo_access.add_rental, rental_new, self._rental_repo)
                self._undo_redo_repo.add_event_to_list(undo_event)

    def return_a_book(self, book_id, client_id):
        """
        Updates a rental with the return date -today if it exists/is valid.
        :param book_id:
        :param client_id:
        :return:
        """
        if self._book_repo.unique_book_id(book_id):
            raise ValueError("invalid book id")
        elif self._client_repo.unique_client_id(client_id):
            raise ValueError("invalid client id")
        else:
            if self.check_book_availability(book_id):
                raise ValueError("book not currently rented")
            elif not self.check_client_book_rented(book_id, client_id):
                raise ValueError("book has not been rented by this client")
            else:
                returned_date = date.today()
                parameters = book_id, client_id, returned_date
                self._rental_repo.return_a_book(parameters)
                """for the undo/redo functionality"""
                undo_event = UndoEvent(self.repo_access.back_to_none_return_date, parameters, self.repo_access.return_a_book, parameters, self._rental_repo)
                self._undo_redo_repo.add_event_to_list(undo_event)

    def add_a_rental(self, book_id, client_id, rent_date, return_date):
        """
        Adds a rental with the specified rental and return date
        ~ used for initialising the rental repo
        :param book_id:
        :param client_id:
        :param rent_date:
        :param return_date:
        :return:
        """
        if self._book_repo.unique_book_id(book_id):
            raise ValueError("invalid book id")
        elif self._client_repo.unique_client_id(client_id):
            raise ValueError("invalid client id")
        else:
            new_rental_id = self._rental_repo.last_rental_id_used() + 1
            new_rental = Rental(new_rental_id, book_id, client_id, rent_date, return_date)
            self._rental_repo.add_rental(new_rental)

    def filter_rentals_by_book_id(self, book_id):
        """
        Returns a list of all the rentals with a specific book_id
        :param book_id:
        :return:
        """
        list_of_rentals_with_book_id = []
        for rental in self._rental_repo:
            if rental.book_id == book_id:
                list_of_rentals_with_book_id.append(rental)
        return list_of_rentals_with_book_id

    def delete_all_rentals_with_book_id(self, book_id):
        """
        Deletes all rentals with a specific book id
        :param book_id:
        :return:
        """
        if self._book_repo.unique_book_id(book_id) or self._book_repo.last_book_id_used() < book_id:
            raise ValueError("invalid book id")
        else:
            complex_undo_event = ComplexUndoEvent()
            rentals_to_delete = self.filter_rentals_by_book_id(book_id)
            for rental in rentals_to_delete:
                """for the undo/redo functionality"""
                undo_event = UndoEvent(RentalRepository.add_rental, rental, RentalRepository.delete_rental, rental, self._rental_repo)
                complex_undo_event.add_undo_redo_to_list(undo_event)
                """actual deletion"""
                self._rental_repo.delete_rental(rental)
            self._undo_redo_repo.add_event_to_list(complex_undo_event)

    def filter_rentals_by_client_id(self, client_id):
        """
        Returns a list of all the rentals with a specific client_id
        :param client_id:
        :return:
        """
        list_of_rentals_with_client_id = []
        for rental in self._rental_repo:
            if rental.client_id == client_id:
                list_of_rentals_with_client_id.append(rental)
        return list_of_rentals_with_client_id

    def delete_all_rentals_with_client_id(self, client_id):
        """
        Deletes all rentals with a specific client id
        :param client_id:
        :return:
        """
        if self._client_repo.unique_client_id(client_id) or self._client_repo.last_client_id_used() < client_id:
            raise ValueError("invalid client id")
        else:
            complex_undo_event = ComplexUndoEvent()
            rentals_to_delete = self.filter_rentals_by_client_id(client_id)
            for rental in rentals_to_delete:
                """for the undo/redo functionality"""
                undo_event = UndoEvent(self.repo_access.add_rental, rental, self.repo_access.delete_rental, rental,
                                       self._rental_repo)
                complex_undo_event.add_undo_redo_to_list(undo_event)
                """actual deletion"""
                self._rental_repo.delete_rental(rental)
            self._undo_redo_repo.add_event_to_list(complex_undo_event)

    def number_of_times_book_was_rented(self, book_id):
        """
        Returns the number of times a book was rented
        :return:
        """
        if self._book_repo.unique_book_id(book_id):
            raise ValueError("invalid book id")
        else:
            number_of_times = 0
            list_of_rentals = self._rental_repo.get_all_rentals()
            for rental in list_of_rentals:
                if rental.book_id == book_id:
                    number_of_times += 1
            return number_of_times

    def statistic_most_rented_books(self):
        """
        Moves the books around in the repo so that they are in the most-least rented order
        :return:
        """
        list_of_books = self._book_repo.get_all_books()
        list_of_books_number_of_rentals = list()
        for book in list_of_books:
            list_of_books_number_of_rentals.append(Book_Rented(book, self.number_of_times_book_was_rented(book.id)))
        list_of_books_number_of_rentals.sort(key=lambda x: x.number_of_rentals, reverse=True)
        return list_of_books_number_of_rentals

    def number_of_times_client_was_active(self, client_id):
        """
        Returns the number of days a client was active
        :param client_id:
        :return:
        """
        if self._client_repo.unique_client_id(client_id):
            raise ValueError("invalid client id")
        else:
            number_of_times = 0
            list_of_rentals = self._rental_repo.get_all_rentals()
            for rental in list_of_rentals:
                if rental.client_id == client_id:
                    number_of_times += len(rental)
            return number_of_times

    def statistic_most_active_clients(self):
        """
        Moves clients around in the repo so that they are in the most-least active order
        :return:
        """
        list_of_clients = self._client_repo.get_all_clients()
        list_of_clients_active = list()
        for client in list_of_clients:
            list_of_clients_active.append(Client_Active(client, self.number_of_times_client_was_active(client.id)))
        list_of_clients_active.sort(key=lambda x: x.number_of_rentals, reverse=True)
        return list_of_clients_active

    def number_of_times_author_was_rented(self, author):
        """
        Returns the amount of times a book by a certain author was rented
        :param author:
        :return:
        """
        number_of_times = 0
        number_of_books = len(self._book_repo)
        list_of_books = self._book_repo.get_all_books()
        for book in list_of_books:
            if book.author == author:
                number_of_times += self.number_of_times_book_was_rented(book.id)
        return number_of_times

    def statistic_most_rented_authors(self):
        """
        Returns a list of the authors by the number of times their books have been rented
        ordered from most-least rented
        :return:
        """
        list_of_authors = []
        list_of_books = self._book_repo.get_all_books()

        for i in range(0, len(list_of_books)):
            number_of_rentals = self.number_of_times_author_was_rented(list_of_books[i].author)
            author_rental = Author_Rented(list_of_books[i].author, number_of_rentals)
            list_of_authors.append(author_rental)

        list_of_authors.sort(key=lambda x: x.number_of_rentals, reverse=True)
        list_of_authors_final = []
        for author in list_of_authors:
            if author not in list_of_authors_final:
                list_of_authors_final.append(author)
        return list_of_authors_final

    def initialise_list(self):
        """ADDING RENTALS TO THE LIST"""
        self.add_a_rental(1, 1, "2021-11-20", None)
        self.add_a_rental(2, 3, "2021-11-10", "2021-11-15")
        self.add_a_rental(5, 8, "2021-11-09", "2021-11-10")
        self.add_a_rental(4, 9, "2021-11-05", "2021-11-10")
        self.add_a_rental(3, 5, "2021-11-17", None)
        self.add_a_rental(9, 9, "2021-11-15", None)
        self.add_a_rental(10, 7, "2021-11-10", None)
        self.add_a_rental(6, 8, "2021-11-09", "2021-11-14")
        self.add_a_rental(1, 6, "2021-10-09", "2021-10-25")
