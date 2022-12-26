from src.exceptions.exceptions import ValidError


class BookValidator:
    def validate_book(self, book):
        """Validate book function checks whether the book id is negative or non-integer."""
        error_string = ""
        if book.book_id < 0 or str(book.book_id).isnumeric() == False:
            error_string += "invalid book id\n"
        if len(error_string) > 0:
            raise ValidError(error_string)
