import pickle

from src.domain.book_class import Book
from src.exceptions.exceptions import RepoError


class BookRepository:
    def __init__(self):
        self._list_of_books = []

    def __len__(self):
        return len(self._list_of_books)

    def __getitem__(self, item):
        for book in self._list_of_books:
            if book.book_id == item:
                return book

    def unique_book_id(self, book_id):
        """checks list if the id is unique"""
        for b in self._list_of_books:
            if book_id == b.book_id:
                return False
        return True

    def last_book_id_used(self):
        """returns the last book id used"""
        last_book_id = -1
        for b in self._list_of_books:
            last_book_id = b.book_id
        return last_book_id

    def add_book(self, book):
        """adds a book to the repo"""
        if self.unique_book_id(book.book_id) is False:
            raise RepoError("Id already in use")
        if book.book_id < 0:
            raise RepoError("Invalid id")
        self._list_of_books.append(book)

    def remove_book(self, book_id):
        """removes a book from the repo"""
        if str(book_id).isnumeric() is False or book_id < 0:
            raise RepoError("Invalid id")
        if self.unique_book_id(book_id) is True:
            raise RepoError("Book not found")
        for b in self._list_of_books:
            if book_id == b.book_id:
                self._list_of_books.remove(b)

    def update_book(self, parameters):
        book_id, new_title, new_author = parameters
        """updates a book from the repo"""
        if str(book_id).isnumeric() is False or book_id < 0:
            raise RepoError("Invalid id")
        if self.unique_book_id(book_id) is True:
            raise RepoError("Book not found")
        for b in self._list_of_books:
            if book_id == b.book_id:
                b.title = new_title
                b.author = new_author

    def get_all_books(self):
        return self._list_of_books


class BookRepositoryTextFile(BookRepository):
    def __init__(self, file_name):
        self.__file_name = file_name
        BookRepository.__init__(self)
        self.load_file()

    def load_file(self):
        f = open(self.__file_name, "rt")
        for line in f.readlines():
            if line != "\n":
                split = line.split(",", maxsplit=2)
                book_id = int(split[0])
                title = split[1]
                author = split[2].rstrip()
                self.add_book(Book(book_id, title, author))
        f.close()

    def save_file(self):
        f = open(self.__file_name, "wt")
        all_books = self.get_all_books()
        for book in all_books:
            f.write(str(book.book_id)+","+book.title+","+book.author+'\n')
        f.close()

    def add_book(self, book):
        super().add_book(book)
        self.save_file()

    def update_book(self, parameters):
        super().update_book(parameters)
        self.save_file()

    def remove_book(self, book_id):
        super().remove_book(book_id)
        self.save_file()


class BookRepositoryBinFile(BookRepository):
    def __init__(self, file_name):
        self.__file_name = file_name
        super().__init__()
        self.load_file()

    def load_file(self):
        f = open(self.__file_name, "rb")
        try:
            self._list_of_books = pickle.load(f)
        except EOFError:
            pass
        f.close()

    def save_file(self):
        f = open(self.__file_name, "wb")
        pickle.dump(self._list_of_books, f)
        f.close()

    def add_book(self, book):
        super().add_book(book)
        self.save_file()

    def remove_book(self, book_id):
        super().remove_book(book_id)
        self.save_file()

    def update_book(self, parameters):
        super().update_book(parameters)
        self.save_file()
