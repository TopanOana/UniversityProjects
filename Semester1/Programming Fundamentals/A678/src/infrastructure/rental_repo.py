class RentalRepository:
    def __init__(self):
        self.__list_of_rentals = []

    def __len__(self):
        return len(self.__list_of_rentals)

    def __getitem__(self, item):
        return self.__list_of_rentals[item]

    def last_rental_id_used(self):
        """returns the last rental id used"""
        last_rental_id = -1
        for r in self.__list_of_rentals:
            last_rental_id = r.rental_id
        return last_rental_id

    def check_availability_book(self, book_id):
        """checks availability of the book with book_id"""
        for r in self.__list_of_rentals:
            if book_id == r.book_id and r.returned_date is None:
                return False
        return True

    def check_book_rented_by_client(self, book_id, client_id):
        """checks whether a book was rented by a client and it wasn't returned"""
        for r in self.__list_of_rentals:
            if r.book_id == book_id and r.client_id == client_id and r.returned_date is None:
                return True
        return False

    def rent_a_book(self, rental_new):
        """adds a new rental to the list of rentals but it wasn't returned"""
        self.__list_of_rentals.append(rental_new)

    def return_a_book(self, parameters):
        """returns a book that was already rented"""
        book_id, client_id, date = parameters
        for r in self.__list_of_rentals:
            if client_id == r.client_id and book_id == r.book_id and r.returned_date is None:
                r.returned_date = date
                break

    def delete_rental(self, rental):
        """deletes a specific rental from the list"""
        self.__list_of_rentals.remove(rental)

    def add_rental(self, rental):
        """adds a rental with all the data"""
        self.__list_of_rentals.append(rental)

    def get_all_rentals(self):
        return self.__list_of_rentals

    def back_to_none_return_date(self, parameters):
        """sets the return date back to None for the rental with book_id
        and client_id and returned_date"""
        book_id, client_id, returned_date = parameters
        for rental in self.__list_of_rentals:
            if rental.book_id == book_id and rental.client_id == client_id and str(rental.returned_date) == str(returned_date):
                rental.returned_date = None

