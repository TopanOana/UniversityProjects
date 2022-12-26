from src.exceptions.exceptions import ValidError


class RentalValidator:
    def validate_rental(self, rental):
        """Validate rental checks whether the id used is valid or not"""
        error_string = ""
        if rental.rental_id < 0 or str(rental.rental_id).isnumeric() == False:
            error_string += "invalid rental id\n"
        if len(error_string) > 0:
            raise ValidError(error_string)
