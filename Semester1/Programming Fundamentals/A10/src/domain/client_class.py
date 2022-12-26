"""
CLIENT CLASS
"""
from src.exceptions.exceptions import ValidError


class Client:
    def __init__(self, client_id, name):
        self.__client_id = client_id
        self.__name = name

    def __str__(self):
        result = "Client " + str(self.id) + " " + self.name
        return result

    @property
    def id(self):
        return int(self.__client_id)

    @property
    def name(self):
        return self.__name

    @name.setter
    def name(self, name):
        self.__name = name