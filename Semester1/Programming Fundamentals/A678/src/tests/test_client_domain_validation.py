import unittest

from src.domain.client_class import Client
from src.validation.client_validator import ClientValidator
from src.exceptions.exceptions import ValidError


class test_client_domain_validation(unittest.TestCase):
    def setUp(self) -> None:
        self._client_id = 1
        self._name = "Iannis"
        self._test_client = Client(self._client_id, self._name)
        self._validator = ClientValidator()
        self._client_id_invalid = -1
        self._test_client1 = Client(self._client_id_invalid, self._name)

    def test_creation(self):
        assert self._test_client.client_id == self._client_id
        assert self._test_client.name == self._name

    def test_string(self):
        assert str(self._test_client) == "Client 1 Iannis"

    def test_validation(self):
        assert self._validator.validate_client(self._test_client) is None
        try:
            self._validator.validate_client(self._test_client1)
        except ValidError as ve:
            assert str(ve) == "invalid client id\n"
        pass

    def tearDown(self) -> None:
        pass
