"""CONNECT FOUR"""

"""
    it's a 7 column by 6 row table
"""

from texttable import Texttable


class Table:
    def __init__(self):
        """
        initialisating the table with 0
        meaning that no pieces have been places on the table
        """
        self._table = [[0 for i in range(7)] for j in range(6)]

    def get_table(self):
        return self._table

    def __str__(self):
        """returns a string version of the table for printing"""
        header = []
        for letter in range(7):
            header.append(chr(65 + letter))

        show_table = Texttable()
        show_table.header(header)

        for i in range(6):
            visible_array = []
            for j in range(7):
                val = self._table[i][j]
                if val == 0:
                    visible_array.append(' ')
                elif val == 1:
                    """player one piece"""
                    visible_array.append('X')
                elif val == 2:
                    """player two piece"""
                    visible_array.append('O')
            show_table.add_row(visible_array)

        return show_table.draw()

    def check_if_piece_can_be_placed(self, column):
        """
        checks whether a piece can be placed on a column
        aka whether the column has been filled or not
        :param column:
        :return:
        """
        return self._table[0][ord(column)-ord('A')] == 0

    def check_if_board_is_not_full(self):
        return self.check_if_piece_can_be_placed('A')  or self.check_if_piece_can_be_placed('B') or self.check_if_piece_can_be_placed('C') or self.check_if_piece_can_be_placed('D') or self.check_if_piece_can_be_placed('E') or self.check_if_piece_can_be_placed('F') or self.check_if_piece_can_be_placed('G')

    def place_piece(self, column, player):
        """
        function to place a piece on the table
        start from the bottom of the table and on the column
        we place it on the available spot
        :param column:
        :param player: which player the piece belongs to
        :return:
        """
        column_to_check = ord(column) - 65
        row_to_check = 5
        while row_to_check >= 0:
            if self._table[row_to_check][column_to_check] == 0:
                self._table[row_to_check][column_to_check] = player
                return
            row_to_check -= 1

    def player_won(self, player): #->TO MOVE TO GAMEPLAY
        """
        function that checks whether a player has won
        1st check :  rows
        2nd check : columns
        3rd check : diagonals
        :param player:
        :return:
        """
        # check rows
        row = 5
        while row >= 0:
            consecutive_identical_values_for_player = 0
            for column in range(7):
                value = self._table[row][column]
                if value == player:
                    consecutive_identical_values_for_player += 1
                else:
                    consecutive_identical_values_for_player = 0
                if consecutive_identical_values_for_player == 4:
                    return True
            row -= 1

        # check columns
        column = 0
        while column < 7:
            consecutive_identical_values_for_player = 0
            for row in range(6):
                value = self._table[row][column]
                if value == player:
                    consecutive_identical_values_for_player += 1
                else:
                    consecutive_identical_values_for_player = 0
                if consecutive_identical_values_for_player == 4:
                    return True
            column += 1

        # check diagonals
        row = 5
        while row >= 0:
            for column in range(7):
                value = self._table[row][column]
                if value == player:
                    # check on each diagonal upwards
                    # check up-left => row-1 and column-1
                    consecutive_identical_values_for_player = 1
                    lesser = 1
                    while consecutive_identical_values_for_player < 4 and consecutive_identical_values_for_player != 0 and row - lesser >= 0 and column - lesser >= 0:
                        if self._table[row - lesser][column - lesser] == player:
                            consecutive_identical_values_for_player += 1
                            lesser += 1
                        else:
                            consecutive_identical_values_for_player = 0
                    if consecutive_identical_values_for_player == 4:
                        return True
                    consecutive_identical_values_for_player = 1
                    greater = 1
                    while consecutive_identical_values_for_player < 4 and consecutive_identical_values_for_player != 0 and row - greater >= 0 and column + greater <= 6:
                        if self._table[row - greater][column + greater] == player:
                            consecutive_identical_values_for_player += 1
                            greater += 1
                        else:
                            consecutive_identical_values_for_player = 0
                    if consecutive_identical_values_for_player == 4:
                        return True
            row -= 1
        return False
