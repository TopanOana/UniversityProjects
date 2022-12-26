import pickle

from src.domain.rental_class import Rental


class RentalRepository:
    def __init__(self):
        self._list_of_rentals = []

    def __len__(self):
        return len(self._list_of_rentals)

    def __getitem__(self, item):
        return self._list_of_rentals[item]

    def last_rental_id_used(self):
        """returns the last rental id used"""
        last_rental_id = -1
        for r in self._list_of_rentals:
            last_rental_id = r.id
        return last_rental_id

    def check_availability_book(self, book_id):
        """checks availability of the book with book_id"""
        for r in self._list_of_rentals:
            if book_id == r.book_id and r.returned_date is None:
                return False
        return True

    def check_book_rented_by_client(self, book_id, client_id):
        """checks whether a book was rented by a client and it wasn't returned"""
        for r in self._list_of_rentals:
            if r.book_id == book_id and r.client_id == client_id and r.returned_date is None:
                return True
        return False

    def rent_a_book(self, rental_new):
        """adds a new rental to the list of rentals but it wasn't returned"""
        self._list_of_rentals.append(rental_new)

    def return_a_book(self, parameters):
        """returns a book that was already rented"""
        book_id, client_id, date = parameters
        for r in self._list_of_rentals:
            if client_id == r.client_id and book_id == r.book_id and r.returned_date is None:
                r.returned_date = date
                break

    def delete_rental(self, rental):
        """deletes a specific rental from the list"""
        self._list_of_rentals.remove(rental)

    def add_rental(self, rental):
        """adds a rental with all the data"""
        self._list_of_rentals.append(rental)

    def get_all_rentals(self):
        return self._list_of_rentals

    def back_to_none_return_date(self, parameters):
        """sets the return date back to None for the rental with book_id
        and client_id and returned_date"""
        book_id, client_id, returned_date = parameters
        for rental in self._list_of_rentals:
            if rental.book_id == book_id and rental.client_id == client_id and str(rental.returned_date) == str(returned_date):
                rental.returned_date = None


class RentalRepositoryTextFile(RentalRepository):
    def __init__(self, file_name):
        super().__init__()
        self.__file_name = file_name
        self.load_file()

    def load_file(self):
        f = open(self.__file_name, "rt")
        for line in f.readlines():
            if line != "\n":
                split = line.split(",", maxsplit=4)
                rental_id = int(split[0])
                book_id = int(split[1])
                client_id = int(split[2])
                rented_date = split[3]
                returned_date = split[4].rstrip()
                if returned_date == "None":
                    self.add_rental(Rental(rental_id, book_id, client_id, rented_date))
                else:
                    self.add_rental(Rental(rental_id, book_id, client_id, rented_date, returned_date))
        f.close()

    def save_file(self):
        f = open(self.__file_name, "wt")
        all_rentals = self.get_all_rentals()
        for rental in all_rentals:
            f.write(str(rental.id) + "," + str(rental.id) + "," + str(rental.id) + "," + str(rental.rented_date) + "," + str(rental.returned_date) + "\n")
        f.close()

    def rent_a_book(self, rental_new):
        super().rent_a_book(rental_new)
        self.save_file()

    def return_a_book(self, parameters):
        super().return_a_book(parameters)
        self.save_file()

    def back_to_none_return_date(self, parameters):
        super().back_to_none_return_date(parameters)
        self.save_file()

    def delete_rental(self, rental):
        super().delete_rental(rental)
        self.save_file()

    def add_rental(self, rental):
        super().add_rental(rental)
        self.save_file()


class RentalRepositoryBinFile(RentalRepository):
    def __init__(self, file_name):
        super().__init__()
        self.__file_name = file_name
        self.load_file()

    def load_file(self):
        f = open(self.__file_name, "rb")
        try:
            self._list_of_rentals = pickle.load(f)
        except EOFError:
            pass
        f.close()

    def save_file(self):
        f = open(self.__file_name, "wb")
        pickle.dump(self._list_of_rentals, f)
        f.close()

    def rent_a_book(self, rental_new):
        super().rent_a_book(rental_new)
        self.save_file()

    def return_a_book(self, parameters):
        super().return_a_book(parameters)
        self.save_file()

    def back_to_none_return_date(self, parameters):
        super().back_to_none_return_date(parameters)
        self.save_file()

    def delete_rental(self, rental):
        super().delete_rental(rental)
        self.save_file()

    def add_rental(self, rental):
        super().add_rental(rental)
        self.save_file()
