import unittest

import src.infrastructure.client_repo
from src.infrastructure.client_repo import ClientRepository
from src.infrastructure.undo_redo_repo import UndoRedoRepo
from src.services.client_service import ClientService
from src.domain.client_class import Client
from src.exceptions.exceptions import ValidError, RepoError


class test_client_domain_validation(unittest.TestCase):
    def setUp(self) -> None:
        self.client_repo = ClientRepository()
        self.undo_redo_repo = UndoRedoRepo()
        self.client_service = ClientService(self.client_repo, self.undo_redo_repo, src.infrastructure.client_repo.ClientRepository)
        self.client1 = Client(1, "Iannis")
        self.client2 = Client(2, "Vlad")

    def test_add_client_repo(self):
        self.client_repo.add_client(self.client1)
        client_invalid_id = Client(-1, "Ajjjjj")
        try:
            self.client_repo.add_client(client_invalid_id)
        except RepoError as re:
            assert str(re) == "Invalid id"
        client_existing_id = Client(1, "Oana")
        try:
            self.client_repo.add_client(client_existing_id)
        except RepoError as re:
            assert str(re) == "Id already in use"

    def test_add_client_to_list(self):
        self.client_service.add_client_to_list(self.client1.name)
        assert len(self.client_service.client_repo) == 1

    def test_remove_client_from_list(self):
        self.client_service.add_client_to_list(self.client1.name)
        self.client_service.add_client_to_list(self.client2.name)
        assert len(self.client_service.client_repo) == 2
        self.client_service.remove_client_from_list(1)
        assert len(self.client_service.client_repo) == 1
        try:
            self.client_service.remove_client_from_list(-9)
        except RepoError as ve:
            assert str(ve) == "Invalid id"
        try:
            self.client_service.remove_client_from_list("a")
        except RepoError as ve:
            assert str(ve) == "Invalid id"
        try:
            self.client_service.remove_client_from_list(9)
        except RepoError as ve:
            assert str(ve) == "Client not found"

    def test_update_client_from_list(self):
        self.client_service.add_client_to_list(self.client1.name)
        self.client_service.add_client_to_list(self.client2.name)
        assert str(self.client_service.client_repo[0]) == "Client 0 Iannis"
        self.client_service.update_client_from_list(0, "Oana")
        assert str(self.client_service.client_repo[0]) == "Client 0 Oana"
        try:
            self.client_service.update_client_from_list("a", "jj")
        except RepoError as ve:
            assert str(ve) == "Invalid id"
        try:
            self.client_service.update_client_from_list(-9, "jj")
        except RepoError as ve:
            assert str(ve) == "Invalid id"
        try:
            self.client_service.update_client_from_list(5, "jj")
        except RepoError as ve:
            assert str(ve) == "Client not found"

    def test_search_for_client_by_id(self):
        self.client_service.add_client_to_list(self.client1.name)
        self.client_service.add_client_to_list(self.client2.name)
        self.client_service.add_client_to_list("Oana")
        self.client_service.add_client_to_list("Kitty")
        search_result = self.client_service.search_for_client_by_id("1")
        assert len(search_result) == 1
        search_result = self.client_service.search_for_client_by_id("0")
        assert len(search_result) == 1
        search_result = self.client_service.search_for_client_by_id("7")
        assert len(search_result) == 0

    def test_search_for_client_by_name(self):
        self.client_service.add_client_to_list(self.client1.name)
        self.client_service.add_client_to_list(self.client2.name)
        self.client_service.add_client_to_list("Oana")
        self.client_service.add_client_to_list("Kitty")
        search_result = self.client_service.search_for_client_by_name("i")
        assert len(search_result) == 2
        search_result = self.client_service.search_for_client_by_name("a")
        assert len(search_result) == 3
        search_result = self.client_service.search_for_client_by_name("ksgd")
        assert len(search_result) == 0

    def tearDown(self) -> None:
        pass