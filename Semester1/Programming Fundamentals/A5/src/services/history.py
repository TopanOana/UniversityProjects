import copy


class History:
    def __init__(self):
        self._history_of_lists = []

    def _remove_last_list(self):
        if len(self._history_of_lists) > 1:
            self._history_of_lists.pop()
        else:
            raise ValueError("undo impossible")

    def _add_a_list(self, list):
        new_list = copy.deepcopy(list)
        self._history_of_lists.append(new_list)

