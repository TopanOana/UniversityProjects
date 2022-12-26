from src.exceptions.exceptions import RepoError


class ClientRepository:
    def __init__(self):
        self.__list_of_clients = []

    def __len__(self):
        return len(self.__list_of_clients)

    def __getitem__(self, item):
        for client in self.__list_of_clients:
            if client.client_id == item:
                return client

    def unique_client_id(self, client_id):
        """checks list of clients whether the id is unique"""
        for c in self.__list_of_clients:
            if client_id == c.client_id:
                return False
        return True

    def last_client_id_used(self):
        """returns the last client id used"""
        last_client_id = -1
        for c in self.__list_of_clients:
            last_client_id = c.client_id
        return last_client_id

    def add_client(self, client):
        """adds a client to the repo"""
        if self.unique_client_id(client.client_id) is False:
            raise RepoError("Id already in use")
        if client.client_id < 0:
            raise RepoError("Invalid id")
        self.__list_of_clients.append(client)

    def remove_client(self, client_id):
        """removes a client from the repo"""
        if str(client_id).isnumeric() is False or client_id < 0:
            raise RepoError("Invalid id")
        if self.unique_client_id(client_id) is True:
            raise RepoError("Client not found")
        for c in self.__list_of_clients:
            if c.client_id == client_id:
                self.__list_of_clients.remove(c)
                break

    def update_client(self, parameters):
        client_id, new_name = parameters
        """updates a client in the repo"""
        if str(client_id).isnumeric() is False or client_id < 0:
            raise RepoError("Invalid id")
        if self.unique_client_id(client_id) is True:
            raise RepoError("Client not found")
        for client in self.__list_of_clients:
            if client_id == client.client_id:
                client.name = new_name
                break

    def get_all_clients(self):
        return self.__list_of_clients