from src.domain.domain import Book
import copy


class Service:
    def __init__(self):
        b1 = Book("0141439580", "Jane Austen", "Emma")
        b2 = Book("0142437204", "Charlotte Bronte", "Jane Eyre")
        b3 = Book("0743273567", "F. Scott Fitzgerald", "The Great Gatsby")
        b4 = Book("0452284236", "George Orwell", "1984")
        b5 = Book("0307347974", "Ray Bradbury", "Fahrenheit 451")
        b6 = Book("0141439572", "Oscar Wilde", "The Picture of Dorian Gray")
        b7 = Book("1529029589", "Toshikazu Kawaguchi", "Before the Coffee Gets Cold")
        b8 = Book("1411498739", "William Shakespeare", "Hamlet")
        b9 = Book("0141439971", "H.G. Wells", "The Time Machine")
        b10 = Book("1481449362", "Amber Smith", "The Way I Used to Be")
        self.list = [b1, b2, b3, b4, b5, b6, b7, b8, b9, b10]

    def _verify_unique_isbn(self, isbn):
        for b in self.list:
            if b.isbn == isbn:
                return False
        return True

    def _add_book_to_list(self, isbn, author, title):
        "Create object of book class"
        b1 = Book(isbn, author, title)
        "Validate object"
        if self._verify_unique_isbn(isbn):
            self.list.append(b1)
        else:
            raise ValueError("non-unique isbn")

    def _all_books_list(self):
        list_to_return = copy.deepcopy(self.list)
        return list_to_return

    def _delete_book_from_list(self, index):
        if index < 0 or index > len(self.list):
            raise ValueError("index out of bounds")
        else:
            self.list.pop(index)

    def _filter_books_with_word(self, word):
        word = word.strip().lower()
        new_list = []
        for i in range(0, len(self.list)):
            aux_title = self.list[i].title.strip().lower()
            aux = aux_title.split(sep=' ', maxsplit=1)
            if aux[0] != word:
                new_list.append(self.list[i])
        self.list = new_list


