class Book:
    """
    Book class
    includes:
    -isbn - unique string
    -author - string
    -title - string
    """

    def __init__(self, isbn, author, title):
        self.__isbn = isbn
        self._author = author
        self._title = title

    def __str__(self):
        result = "Book with isbn: "
        result += str(self.isbn) + ", Title: "
        result += str(self.title) + " by "
        result += str(self.author)
        return result

    @property
    def isbn(self):
        return self.__isbn

    @property
    def author(self):
        return self._author

    @author.setter
    def author(self, author_new):
        self._author = author_new

    @property
    def title(self):
        return self._title

    @title.setter
    def title(self, title_new):
        self._title = title_new

