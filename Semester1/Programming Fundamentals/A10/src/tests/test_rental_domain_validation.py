import unittest

from src.domain.rental_class import Rental
from src.validation.rental_validator import RentalValidator
from src.exceptions.exceptions import ValidError
from datetime import date


class test_rental_domain_validation(unittest.TestCase):
    def setUp(self) -> None:
        self._rental1 = Rental(1, 1, 1, date.today())
        self._current_date = date.today()
        self._validator = RentalValidator()
        self._rental2 = Rental(1, 1, 1, "2021-11-03", date.today())
        self._rental_invalid = Rental(-1, 1, 1, date.today())

    def test_creation(self):
        assert self._rental2.id == 1
        assert self._rental2.book_id == 1
        assert self._rental2.client_id == 1
        assert self._rental2.rented_date == date.fromisoformat("2021-11-03")
        assert self._rental2.returned_date == date.today()

    def test_string(self):
        assert str(self._rental1) == "Rental 1 Book id: 1 Client id: 1 Rented date : " + str(self._current_date) + " not returned yet."
        assert str(self._rental2) == "Rental 1 Book id: 1 Client id: 1 Rented date : 2021-11-03 Returned date: " + str(self._current_date)

    def test_validation(self):
        assert self._validator.validate_rental(self._rental1) is None
        try:
            self._validator.validate_rental(self._rental_invalid)
        except ValidError as ve:
            assert str(ve) == "invalid rental id\n"

    def test_length(self):
        assert len(self._rental1) == 1
        assert len(self._rental2) == (self._current_date - self._rental2.rented_date).days + 1

    def tearDown(self) -> None:
        pass