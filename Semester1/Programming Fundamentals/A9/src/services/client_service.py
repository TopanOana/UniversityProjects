from src.domain.client_class import Client
from src.domain.undo_classes import UndoEvent
from src.validation.client_validator import ClientValidator
from src.infrastructure.client_repo import ClientRepository


class ClientService:
    def __init__(self, client_repo, undo_redo_repo, repo_access):
        self.client_repo = client_repo
        self.undo_repo_repo = undo_redo_repo
        self.repo_access = repo_access

    def add_client_to_list(self, name):
        """
        Adds client to the list of clients
        ALSO validates the client
        :param client_id:
        :param name:
        :return:
        """
        client_id = self.client_repo.last_client_id_used() + 1
        client = Client(client_id, name)
        validator = ClientValidator()
        validator.validate_client(client)
        self.client_repo.add_client(client)
        """for the undo/redo repository"""
        undo_event = UndoEvent(self.repo_access.remove_client, client_id, self.repo_access.add_client, client, self.client_repo)
        self.undo_repo_repo.add_event_to_list(undo_event)

    def remove_client_from_list(self, client_id):
        """
        removes a client from the list of clients
        also checks whether the client id exists in the list of clients
        :param client_id:
        :return:
        """
        """for the undo/redo functionality"""
        undo_event = UndoEvent(self.repo_access.add_client, self.client_repo[client_id], self.repo_access.remove_client, client_id, self.client_repo)
        self.undo_repo_repo.add_event_to_list(undo_event)
        """actual deletion"""
        self.client_repo.remove_client(client_id)

    def update_client_from_list(self, client_id, name_new):
        """
        updates a client from the list of clients with a new name
        checks whether the client id exists in the list of clients
        :param client_id:
        :param name_new:
        :return:
        """
        parameters_redo = client_id, name_new
        if self.client_repo.unique_client_id(client_id) is False:
            """for the undo/redo functionality"""
            parameters_undo = client_id, self.client_repo[client_id].name
            undo_event = UndoEvent(self.repo_access.update_client, parameters_undo, self.repo_access.update_client, parameters_redo, self.client_repo)
            self.undo_repo_repo.add_event_to_list(undo_event)
        """actual update"""
        self.client_repo.update_client(parameters_redo)

    def search_for_client_by_id(self, partial_client_id):
        """
        Returns a list of all the clients with ids that contain the partial client id
        :param partial_client_id:
        :return:
        """
        partial_client_id = partial_client_id.lower()
        list_of_results = []
        list_of_clients = self.client_repo.get_all_clients()
        for client in list_of_clients:
            if partial_client_id in str(client.client_id):
                list_of_results.append(client)
        return list_of_results

    def search_for_client_by_name(self, partial_name):
        """
        Returns a list of all the clients with names that contain the partial_name string
        :param partial_name:
        :return:
        """
        partial_name = partial_name.lower()
        list_of_results = []
        list_of_clients = self.client_repo.get_all_clients()
        for client in list_of_clients:
            if partial_name in client.name:
                list_of_results.append(client)
        return list_of_results

    def initialise_list(self):
        """ADDING CLIENTS TO THE LIST OF CLIENTS"""
        self.client_repo.add_client(Client(1, "Oana"))
        self.client_repo.add_client(Client(2, "Vlad"))
        self.client_repo.add_client(Client(3, "Krisztina"))
        self.client_repo.add_client(Client(4, "Iannis"))
        self.client_repo.add_client(Client(5, "Alexia"))
        self.client_repo.add_client(Client(6, "Andrei"))
        self.client_repo.add_client(Client(7, "Robert"))
        self.client_repo.add_client(Client(8, "Alexandra"))
        self.client_repo.add_client(Client(9, "Bianca"))
        self.client_repo.add_client(Client(10, "Alex"))
