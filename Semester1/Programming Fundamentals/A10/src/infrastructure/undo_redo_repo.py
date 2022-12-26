from src.exceptions.exceptions import UndoRedoRepoError


class UndoRedoRepo:
    def __init__(self):
        """
        list of events = list of all the actions that modify a repository
        index = the last event marked in the repositories
        """
        self.__list_of_events = list()
        self.__index = 0

    def add_event_to_list(self, event):
        """
        1. slice the list so that only the older events are relevant
        2. append the event to the list
        3. increment the index
        :param event:
        :return:
        """
        self.__list_of_events = self.__list_of_events[:self.__index]
        self.__list_of_events.append(event)
        self.__index += 1

    def undo(self):
        """
        Raises exception if there are no events to undo
        otherwise calls the undo function from the last event
        :return:
        """
        if self.__index == 0:
            raise UndoRedoRepoError("Cannot undo further")
        self.__index -= 1
        self.__list_of_events[self.__index].execute_undo()

    def redo(self):
        """
        Raises exception if there are no events to redo
        otherwise calls the redo function from the last event
        :return:
        """
        if self.__index == len(self.__list_of_events):
            raise UndoRedoRepoError("Cannot redo further")
        self.__list_of_events[self.__index].execute_redo()
        self.__index += 1


