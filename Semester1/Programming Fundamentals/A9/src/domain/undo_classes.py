class AbstractUndoEvent:
    """This is the larger class, aka the way they should all look"""
    def execute_undo(self):
        pass

    def execute_redo(self):
        pass


class UndoEvent(AbstractUndoEvent):
    def __init__(self, function_undo, undo_parameters, function_redo, redo_parameters, repo):
        """
        :param function_undo: the function to call in order to undo the event
        :param undo_parameters: the parameters for the undo function
        :param function_redo: the function to call in order to redo the event
        :param redo_parameters: the parameters for the redo function
        :param repo: the repository that is used for the event
        """
        self.__function_undo = function_undo
        self.__undo_parameters = undo_parameters
        self.__function_redo = function_redo
        self.__redo_parameters = redo_parameters
        self.__repo = repo

    def execute_undo(self):
        """Calls the undo function with the repo and the undo parameters"""
        self.__function_undo(self.__repo, self.__undo_parameters)

    def execute_redo(self):
        """Calls the redo function with the repo and the redo parameters"""
        self.__function_redo(self.__repo, self.__redo_parameters)


class ComplexUndoEvent(AbstractUndoEvent):
    def __init__(self):
        """list of undo_redo events"""
        self.__list_for_undo_redo = list()

    @property
    def list_for_undo_redo(self):
        return self.__list_for_undo_redo

    def add_undo_redo_to_list(self, undo_redo):
        """adds an undo_redo event to the list"""
        self.__list_for_undo_redo.append(undo_redo)

    def execute_undo(self):
        """executes the undo operation for the whole list starting with the last element in the list"""
        list_of_events = self.list_for_undo_redo
        for event in reversed(list_of_events):
            event.execute_undo()

    def execute_redo(self):
        """executes the redo operation for the whole list starting with the last element in the list"""
        list_of_events = self.list_for_undo_redo
        for event in reversed(list_of_events):
            event.execute_redo()
