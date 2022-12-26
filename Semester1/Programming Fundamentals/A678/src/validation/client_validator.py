from src.exceptions.exceptions import ValidError


class ClientValidator:
    def validate_client(self, client):
        """Validate client checks whether the id used is valid or not"""
        error_string = ""
        if client.client_id < 0 or str(client.client_id).isnumeric() == False:
            error_string += "invalid client id\n"
        if len(error_string) > 0:
            raise ValidError(error_string)
