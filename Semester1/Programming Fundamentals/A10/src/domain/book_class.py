"""
FILE FOR BOOK CLASS
"""
from src.exceptions.exceptions import ValidError


class Book:
    "Book class"
    def __init__(self, book_id, title, author):
        self.__book_id = book_id
        self._title = title
        self._author = author

    def __str__(self):
        result = "Book " + str(self.id) + ". " + self.title + " by " + self.author
        return result

    @property
    def id(self):
        return int(self.__book_id)

    @property
    def title(self):
        return self._title

    @title.setter
    def title(self, title_new):
        self._title = title_new

    @property
    def author(self):
        return self._author

    @author.setter
    def author(self, author_new):
        self._author = author_new
