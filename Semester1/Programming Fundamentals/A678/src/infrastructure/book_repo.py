from src.exceptions.exceptions import RepoError


class BookRepository:
    def __init__(self):
        self.__list_of_books = []

    def __len__(self):
        return len(self.__list_of_books)

    def __getitem__(self, item):
        for book in self.__list_of_books:
            if book.book_id == item:
                return book

    def unique_book_id(self, book_id):
        """checks list if the id is unique"""
        for b in self.__list_of_books:
            if book_id == b.book_id:
                return False
        return True

    def last_book_id_used(self):
        """returns the last book id used"""
        last_book_id = -1
        for b in self.__list_of_books:
            last_book_id = b.book_id
        return last_book_id

    def add_book(self, book):
        """adds a book to the repo"""
        if self.unique_book_id(book.book_id) is False:
            raise RepoError("Id already in use")
        if book.book_id < 0:
            raise RepoError("Invalid id")
        self.__list_of_books.append(book)

    def remove_book(self, book_id):
        """removes a book from the repo"""
        if str(book_id).isnumeric() is False or book_id < 0:
            raise RepoError("Invalid id")
        if self.unique_book_id(book_id) is True:
            raise RepoError("Book not found")
        for b in self.__list_of_books:
            if book_id == b.book_id:
                self.__list_of_books.remove(b)

    def update_book(self, parameters):
        book_id, new_title, new_author = parameters
        """updates a book from the repo"""
        if str(book_id).isnumeric() is False or book_id < 0:
            raise RepoError("Invalid id")
        if self.unique_book_id(book_id) is True:
            raise RepoError("Book not found")
        for b in self.__list_of_books:
            if book_id == b.book_id:
                b.title = new_title
                b.author = new_author

    def get_all_books(self):
        return self.__list_of_books
