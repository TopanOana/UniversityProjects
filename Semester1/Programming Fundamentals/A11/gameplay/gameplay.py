"""gameplay"""
from random import randint


class GamePlayException(Exception):
    pass


class GamePlay:
    def __init__(self, table):
        self._table = table
        self._round = 0
        self._current_player = 1
        """
        current player = 1 => the human player
        current player = 2 => the computer player
        """

    def check_if_column_provided_is_valid(self, column):
        """checks whether the column provided could be used to place a piece"""
        return self._table.check_if_piece_can_be_placed(column)

    def player_one_turn(self, column):
        """
        places a piece if the column isn't completely filled up
        column comes from the user interface
        :param column:
        :return:
        """
        self._table.place_piece(column, 1)

    def stop_horizontal_win(self):
        """
        returns a column where you can stop a win from the human player
        stops when a human player has placed 2+ pieces on the same row consecutively
        """
        """starting with the lowest row and decreasing so that the representation is correct"""
        row = 5
        table = self._table.get_table()
        while row >= 0:
            consecutive_identical_values_for_human_player = 0
            for column in range(7):
                if table[row][column] == 1:
                    consecutive_identical_values_for_human_player += 1
                elif table[row][column] == 0 and consecutive_identical_values_for_human_player > 2:
                    if row <= 4 and table[row + 1][column] != 0 or row == 5:
                        return column

                else:
                    consecutive_identical_values_for_human_player = 0
            row -= 1
        return None

    def stop_vertical_win(self):
        """
        returns a column where you can stop a win from the human player
        stops when a human player has placed 2+ pieces on the same column consecutively
        :return:
        """
        table = self._table.get_table()
        for column in range(7):
            row = 5
            consecutive_identical_values_for_human_player = 0
            while row >= 0:
                if table[row][column] == 1:
                    consecutive_identical_values_for_human_player += 1
                elif table[row][column] == 0 and consecutive_identical_values_for_human_player > 2:
                    return column
                else:
                    consecutive_identical_values_for_human_player = 0
                row -= 1
        return None

    def stop_diagonal_win(self):
        """
        returns a column where you can stop a win from the human player
        stops when a human player has placed 3 pieces on a diagonal consecutively
        :return:
        """
        row = 5
        table = self._table.get_table()
        while row >= 0:
            for column in range(7):
                if table[row][column] == 1:
                    consecutive_identical_values_for_human_player = 1
                    check_diagonal_left = 1
                    while consecutive_identical_values_for_human_player < 3 and consecutive_identical_values_for_human_player != 0 and row - check_diagonal_left >= 0 and column - check_diagonal_left >= 0:
                        if table[row - check_diagonal_left][column - check_diagonal_left] == 1:
                            consecutive_identical_values_for_human_player += 1
                            check_diagonal_left += 1
                        else:
                            consecutive_identical_values_for_human_player = 0
                    if consecutive_identical_values_for_human_player == 3 and column - check_diagonal_left - 1 >= 0:
                        if table[row - check_diagonal_left + 1][column - check_diagonal_left - 1] != 0 and \
                                table[row - check_diagonal_left][column - check_diagonal_left] == 0:
                            return column - check_diagonal_left - 1
                    consecutive_identical_values_for_human_player = 1
                    check_diagonal_right = 1
                    while consecutive_identical_values_for_human_player < 3 and consecutive_identical_values_for_human_player != 0 and row - check_diagonal_right >= 0 and column + check_diagonal_right <= 6:
                        if table[row - check_diagonal_right][column + check_diagonal_right] == 1:
                            consecutive_identical_values_for_human_player += 1
                            check_diagonal_right += 1
                        else:
                            consecutive_identical_values_for_human_player = 0
                    if consecutive_identical_values_for_human_player == 3 and column + check_diagonal_right + 1 <= 6:
                        if table[row - check_diagonal_right + 1][column + check_diagonal_right] != 0 and \
                                table[row - check_diagonal_right][column + check_diagonal_right] == 0:
                            return column + check_diagonal_right
            row -= 1
        return None

    def win_horizontal(self):
        """
        function that returns the column that needs to placed in order for the computer
        to win on the horizontal placement(1 move away)
        :return:
        """
        row = 5
        table = self._table.get_table()
        while row >= 0:
            consecutive_identical_values_for_computer_player = 0
            for column in range(7):
                if table[row][column] == 2:
                    consecutive_identical_values_for_computer_player += 1
                elif table[row][column] == 0 and consecutive_identical_values_for_computer_player > 2:
                    if row <= 4 and table[row + 1][column] != 0 or row == 5:
                        return column

                else:
                    consecutive_identical_values_for_computer_player = 0
            row -= 1
        return None

    def win_vertical(self):
        """
         function that returns the column that needs to placed in order for the computer
        to win on the vertical placement(1 move away)
        :return:
        """
        table = self._table.get_table()
        for column in range(7):
            row = 5
            consecutive_identical_values_for_computer_player = 0
            while row >= 0:
                if table[row][column] == 2:
                    consecutive_identical_values_for_computer_player += 1
                elif table[row][column] == 0 and consecutive_identical_values_for_computer_player > 2:
                    return column
                else:
                    consecutive_identical_values_for_computer_player = 0
                row -= 1
        return None

    def win_diagonal(self):
        """
        function that returns the column that needs to be placed in order for the computer to
        win on the diagonal placement (1 move away)
        :return:
        """
        row = 5
        table = self._table.get_table()
        while row >= 0:
            for column in range(7):
                if table[row][column] == 2:
                    consecutive_identical_values_for_computer_player = 1
                    check_diagonal_left = 1
                    while consecutive_identical_values_for_computer_player < 3 and consecutive_identical_values_for_computer_player != 0 and row - check_diagonal_left >= 0 and column - check_diagonal_left >= 0:
                        if table[row - check_diagonal_left][column - check_diagonal_left] == 2:
                            consecutive_identical_values_for_computer_player += 1
                            check_diagonal_left += 1
                        else:
                            consecutive_identical_values_for_computer_player = 0
                    if consecutive_identical_values_for_computer_player == 3 and column - check_diagonal_left - 1 >= 0:
                        if table[row - check_diagonal_left + 1][column - check_diagonal_left - 1] != 0 and \
                                table[row - check_diagonal_left][column - check_diagonal_left] == 0:
                            return column - check_diagonal_left - 1
                    consecutive_identical_values_for_computer_player = 1
                    check_diagonal_right = 1
                    while consecutive_identical_values_for_computer_player < 3 and consecutive_identical_values_for_computer_player != 0 and row - check_diagonal_right >= 0 and column + check_diagonal_right <= 6:
                        if table[row - check_diagonal_right][column + check_diagonal_right] == 2:
                            consecutive_identical_values_for_computer_player += 1
                            check_diagonal_right += 1
                        else:
                            consecutive_identical_values_for_computer_player = 0
                    if consecutive_identical_values_for_computer_player == 3 and column + check_diagonal_right + 1 <= 6:
                        if table[row - check_diagonal_right + 1][column + check_diagonal_right] != 0 and \
                                table[row - check_diagonal_right][column + check_diagonal_right] == 0:
                            return column + check_diagonal_right
            row -= 1
        return None

    def player_two_turn(self):
        """
        computer will place a piece in order to win or prevent the human player from winning
        :return:
        """
        """generate a good column/row combination"""
        rand = randint(0, 6)
        diag_obstruct = self.stop_diagonal_win()
        horiz_obstruct = self.stop_horizontal_win()
        vert_obstruct = self.stop_vertical_win()
        diag_win = self.win_diagonal()
        horiz_win = self.win_horizontal()
        vert_win = self.win_vertical()
        if diag_win is not None:
            column = chr(ord('A') + diag_win)
        elif horiz_win is not None:
            column = chr(ord('A') + horiz_win)
        elif vert_win is not None:
            column = chr(ord('A') + vert_win)
        elif diag_obstruct is not None:
            column = chr(ord('A') + diag_obstruct)
        elif horiz_obstruct is not None:
            column = chr(ord('A') + horiz_obstruct)
        elif vert_obstruct is not None:
            column = chr(ord('A') + vert_obstruct)
        else:
            column = chr(ord('A') + rand)
            while self.check_if_column_provided_is_valid(column) is False:
                rand = randint(0, 6)
                column = chr(ord('A') + rand)
        if self.check_if_column_provided_is_valid(column) is True:
            self._table.place_piece(column, 2)
