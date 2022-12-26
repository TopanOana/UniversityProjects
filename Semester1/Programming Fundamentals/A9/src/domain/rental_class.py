"""
Rental class
"""
import datetime

class Rental:
    def __init__(self, rental_id, book_id, client_id, rented_date, returned_date=None):
        self.__rental_id = rental_id
        self._book_id = book_id
        self._client_id = client_id
        self._rented_date = datetime.date.fromisoformat(str(rented_date))
        if returned_date is None:
            self._returned_date = None
        else:
            self._returned_date = datetime.date.fromisoformat(str(returned_date))

    def __str__(self):
        result = "Rental " + str(self.rental_id) + " Book id: " + str(self.book_id) + " Client id: " + str(self.client_id) + " Rented date : " + str(self.rented_date)
        if self.returned_date is not None:
            result += " Returned date: " + str(self.returned_date)
        else:
            result += " not returned yet."
        return result

    def __len__(self):
        if self.returned_date is None:
            return (datetime.date.today()- self.rented_date).days + 1
        return (self.returned_date - self.rented_date).days + 1

    @property
    def rental_id(self):
        return self.__rental_id

    @property
    def book_id(self):
        return self._book_id

    @property
    def client_id(self):
        return self._client_id

    @property
    def rented_date(self):
        return self._rented_date

    @property
    def returned_date(self):
        return self._returned_date

    @returned_date.setter
    def returned_date(self, date):
        self._returned_date = date

