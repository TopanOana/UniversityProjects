"""
TESTS FOR NON UI FUNCTIONS
-domain is not tested
"""
from src.domain.domain import Book
from src.services.services import Service
from src.services.history import History


def test_unique_isbn():
    service = Service()
    isbn = "1416524304"
    title = "Carrie"
    author = "Stephen King"
    assert service._verify_unique_isbn(isbn) == True
    service._add_book_to_list(isbn, author, title)
    assert service._verify_unique_isbn(isbn) == False


def test_add_book_to_list():
    service = Service()
    isbn = "1416524304"
    title = "Carrie"
    author = "Stephen King"
    service._add_book_to_list(isbn, author, title)
    b1 = Book(isbn, author, title)
    assert str(service.list[-1]) == str(b1)
    try:
        service._add_book_to_list(isbn, author, title)
    except ValueError as ve:
        assert str(ve) == "non-unique isbn"


def test_delete_book_from_list():
    service = Service()
    assert len(service.list) == 10
    service._delete_book_from_list(7)
    assert len(service.list) == 9
    try:
        service._delete_book_from_list(-8)
    except ValueError as ve:
        assert str(ve) == "index out of bounds"
    try:
        service._delete_book_from_list(78)
    except ValueError as ve:
        assert str(ve) == "index out of bounds"


def test_filter_books():
    service = Service()
    word = "the"
    service._filter_books_with_word(word)
    assert len(service.list) == 6
    word1 = "nothing"
    service._filter_books_with_word(word1)
    assert len(service.list) == 6


def test_add_list_history():
    history = History()
    service = Service()
    history._add_a_list(service.list)
    assert len(history._history_of_lists) == 1
    isbn = "1416524304"
    title = "Carrie"
    author = "Stephen King"
    service._add_book_to_list(isbn, author, title)
    history._add_a_list(service.list)
    assert len(history._history_of_lists) == 2


def test_remove_list_history():
    history = History()
    service = Service()
    history._add_a_list(service.list)
    isbn = "1416524304"
    title = "Carrie"
    author = "Stephen King"
    service._add_book_to_list(isbn, author, title)
    history._add_a_list(service.list)
    assert len(history._history_of_lists) == 2
    history._remove_last_list()
    assert len(history._history_of_lists) == 1
    try:
        history._remove_last_list()
    except ValueError as ve:
        assert str(ve) == "undo impossible"



def test():
    print("starting tests...")
    test_add_book_to_list()
    test_unique_isbn()
    test_delete_book_from_list()
    test_filter_books()
    test_add_list_history()
    test_remove_list_history()
    print("tests done.")


test()
