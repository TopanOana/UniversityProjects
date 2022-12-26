import unittest

from src.exceptions.exceptions import CollectionError
from src.iterating_structure.data_structure import IteratorData
from src.domain.book_class import Book
from src.domain.client_class import Client


class test_data_structure(unittest.TestCase):
    def setUp(self) -> None:
        self.iterable_object = IteratorData()

    def test_built_in_functions(self):
        self.iterable_object.append(10)
        assert self.iterable_object[0] == 10
        self.iterable_object[0] = 20
        assert self.iterable_object[0] == 20
        try:
            self.iterable_object[10] = 8
        except CollectionError as ce:
            assert str(ce) == "incorrect"
        self.iterable_object.append(50)
        assert self.iterable_object[1] == 50
        self.iterable_object.append(100)
        assert self.iterable_object[2] == 100
        self.iterable_object.__delitem__(1)
        assert self.iterable_object[1] == 100
        self.iterable_object.append(50)
        iterable = iter(self.iterable_object)
        value = next(iterable)
        assert value == 20
        value = next(iterable)
        assert value == 100
        value = next(iterable)
        assert value == 50
        try:
            value = next(iterable)
        except StopIteration:
            assert True
        self.iterable_object.remove(100)
        assert self.iterable_object[1] == 50

    def test_compare_cases_book(self):
        self.iterable_object.append(Book(1, "TITLE1", "AUTHOR1"))
        self.iterable_object.append(Book(2, "TITLE2", "AUTHOR2"))
        self.iterable_object.append(Book(3, "TITLE3", "AUTHOR3"))
        assert self.iterable_object.compare_id(self.iterable_object[0], self.iterable_object[1]) is True
        assert self.iterable_object.compare_title(self.iterable_object[1], self.iterable_object[0]) is False
        assert self.iterable_object.compare_author(self.iterable_object[0], self.iterable_object[2]) is True

    def test_compare_cases_client(self):
        self.iterable_object.append(Client(1, "name1"))
        self.iterable_object.append(Client(2, "name2"))
        assert self.iterable_object.compare_id(self.iterable_object[0], self.iterable_object[1]) is True
        assert self.iterable_object.compare_name(self.iterable_object[1], self.iterable_object[0]) is False

    def test_comb_sort(self):
        self.iterable_object.append(Book(3, "TITLE3", "AUTHOR3"))
        self.iterable_object.append(Book(1, "TITLE1", "AUTHOR1"))
        self.iterable_object.append(Book(2, "TITLE2", "AUTHOR2"))
        self.iterable_object.comb_sort(self.iterable_object._data, self.iterable_object.compare_author)
        assert str(self.iterable_object[0]) == str(Book(1, "TITLE1", "AUTHOR1"))
        assert str(self.iterable_object[1]) == str(Book(2, "TITLE2", "AUTHOR2"))
        assert str(self.iterable_object[2]) == str(Book(3, "TITLE3", "AUTHOR3"))

    def test_accept_functions_book(self):
        self.iterable_object.append(Book(1, "TITLE1", "AUTHOR1"))
        self.iterable_object.append(Book(2, "TITLE2", "AUTHOR2"))
        self.iterable_object.append(Book(3, "TITLE3", "AUTHOR3"))
        assert self.iterable_object.accept_id(self.iterable_object[0], 1) is True
        assert self.iterable_object.accept_title(self.iterable_object[1],"TITLE") is True
        assert self.iterable_object.accept_author(self.iterable_object[2],"a") is True

    def test_accept_functions_client(self):
        self.iterable_object.append(Client(1, "name1"))
        self.iterable_object.append(Client(2, "name2"))
        assert self.iterable_object.accept_id(self.iterable_object[0], 3) is False
        assert self.iterable_object.accept_name(self.iterable_object[1], "io") is False
        assert self.iterable_object.accept_name(self.iterable_object[0], "ame") is True

    def test_filter_function(self):
        self.iterable_object.append(Book(1, "aa", "AUTHOR1"))
        self.iterable_object.append(Book(2, "ba", "AUTHOR2"))
        self.iterable_object.append(Book(3, "bb", "AUTHOR3"))
        final_list = self.iterable_object.filter_function(self.iterable_object._data, self.iterable_object.accept_title, "a")
        assert len(final_list) == 2
        assert final_list[0].title == "aa"
        assert final_list[1].title == "ba"
