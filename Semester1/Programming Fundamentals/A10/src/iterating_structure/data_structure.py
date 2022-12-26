from src.exceptions.exceptions import CollectionError


class IteratorData:
    def __init__(self):
        self._data = []

    def __len__(self):
        return len(self._data)

    def __setitem__(self, index, value):
        if index < len(self._data) and type(self._data[0]) == type(value):
            self._data[index] = value
        else:
            raise CollectionError("incorrect")

    def __getitem__(self, index):
        return self._data[index]

    def __delitem__(self, index):
        self._data.pop(index)

    def __next__(self):
        if self._iter_position >= len(self._data):
            raise StopIteration()
        result = self._data[self._iter_position]
        self._iter_position = self._iter_position + 1
        return result

    def __iter__(self):
        self._iter_position = 0
        return self

    def append(self, object):
        self._data.append(object)

    def remove(self, object):
        self._data.remove(object)

    @staticmethod
    def compare_id(object1, object2):
        return object1.id < object2.id

    @staticmethod
    def compare_title(object1, object2):
        return object1.title <= object2.title

    @staticmethod
    def compare_author(object1, object2):
        return object1.author <= object2.author

    @staticmethod
    def compare_name(object1, object2):
        return object1.name <= object2.name

    @staticmethod
    def get_next_gap(gap):
        """
        gets the next gap for the comb sort
        shrinking factor of 1.3
        """
        gap = (gap * 10) // 13
        if gap < 1:
            return 1
        return gap

    @staticmethod
    def comb_sort(list_to_sort, compare_function):
        """
        comb sort function
        n = length of the list to be sorted
        gap is initially the length of the list and then gets smaller and smaller
        swapped checks whether any changes have been made during the iteration
        """
        n = len(list_to_sort)
        gap = n
        swapped = True

        while gap != 1 or swapped is True:
            """gap is shrunk"""
            gap = IteratorData.get_next_gap(gap)
            """swapped is changed to False in order to verify whether any elements have been swapped"""
            swapped = False
            for i in range(0, n-gap):
                """ 
                0-> n-gap so that you only go through valid elements in the list
                use of compare function to check the two elements that need to be sorted
                """
                if not compare_function(list_to_sort[i], list_to_sort[i + gap]):
                    list_to_sort[i], list_to_sort[i + gap] = list_to_sort[i + gap], list_to_sort[i]
                    swapped = True

    @staticmethod
    def accept_id(object, value):
        return str(value) in str(object.id)

    @staticmethod
    def accept_title(object, value):
        return value.lower() in object.title.lower()

    @staticmethod
    def accept_author(object, value):
        return value.lower() in object.author.lower()

    @staticmethod
    def accept_name(object, value):
        return value.lower() in object.name.lower()

    @staticmethod
    def filter_function(list_to_filter, acceptance_function, value):
        final_list = []
        for object in list_to_filter:
            if acceptance_function(object, value):
                final_list.append(object)
        return final_list

