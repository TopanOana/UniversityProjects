import pickle

from src.domain.client_class import Client
from src.exceptions.exceptions import RepoError
from src.iterating_structure.data_structure import IteratorData


class ClientRepository:
    def __init__(self):
        self._list_of_clients = IteratorData()

    def __len__(self):
        return len(self._list_of_clients)

    def __getitem__(self, item):
        for client in self._list_of_clients:
            if client.id == item:
                return client

    def unique_client_id(self, client_id):
        """checks list of clients whether the id is unique"""
        for c in self._list_of_clients:
            if client_id == c.id:
                return False
        return True

    def last_client_id_used(self):
        """returns the last client id used"""
        last_client_id = -1
        for c in self._list_of_clients:
            last_client_id = c.id
        return last_client_id

    def add_client(self, client):
        """adds a client to the repo"""
        if self.unique_client_id(client.id) is False:
            raise RepoError("Id already in use")
        if client.id < 0:
            raise RepoError("Invalid id")
        self._list_of_clients.append(client)

    def remove_client(self, client_id):
        """removes a client from the repo"""
        if str(client_id).isnumeric() is False or client_id < 0:
            raise RepoError("Invalid id")
        if self.unique_client_id(client_id) is True:
            raise RepoError("Client not found")
        for c in self._list_of_clients:
            if c.id == client_id:
                self._list_of_clients.remove(c)
                break

    def update_client(self, parameters):
        client_id, new_name = parameters
        """updates a client in the repo"""
        if str(client_id).isnumeric() is False or client_id < 0:
            raise RepoError("Invalid id")
        if self.unique_client_id(client_id) is True:
            raise RepoError("Client not found")
        for client in self._list_of_clients:
            if client_id == client.id:
                client.name = new_name
                break

    def get_all_clients(self):
        return self._list_of_clients


class ClientRepositoryTextFile(ClientRepository):
    def __init__(self, file_name):
        self.__file_name = file_name
        super().__init__()
        self.load_file()

    def load_file(self):
        f = open(self.__file_name, "rt")
        for line in f.readlines():
            if line != "\n":
                split = line.split(",", maxsplit=1)
                client_id = int(split[0])
                name = split[1].rstrip()
                self.add_client(Client(client_id, name))
        f.close()

    def save_file(self):
        f = open(self.__file_name, "wt")
        all_clients = self.get_all_clients()
        for client in all_clients:
            f.write(str(client.id) + "," + client.name + "\n")
        f.close()

    def add_client(self, client):
        super().add_client(client)
        self.save_file()

    def update_client(self, parameters):
        super().update_client(parameters)
        self.save_file()

    def remove_client(self, client_id):
        super().remove_client(client_id)
        self.save_file()


class ClientRepositoryBinFile(ClientRepository):
    def __init__(self, file_name):
        self.__file_name = file_name
        super().__init__()
        self.load_file()

    def load_file(self):
        f = open(self.__file_name, "rb")
        try:
            self._list_of_clients = pickle.load(f)
        except EOFError:
            pass
        f.close()

    def save_file(self):
        f = open(self.__file_name, "wb")
        pickle.dump(self._list_of_clients, f)
        f.close()

    def add_client(self, client):
        super().add_client(client)
        self.save_file()

    def remove_client(self, client_id):
        super().remove_client(client_id)
        self.save_file()

    def update_client(self, parameters):
        super().update_client(parameters)
        self.save_file()
