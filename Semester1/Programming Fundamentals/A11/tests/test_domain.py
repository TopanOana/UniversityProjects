from domain.board import Table
import unittest


class Test_domain(unittest.TestCase):
    def setUp(self) -> None:
        self.table_not_completed = Table()
        self.table_completed = Table()
        column = 'A'
        while column <='G':
            for i in range(0, 6):
                self.table_completed.place_piece(column, 1)
            column = chr(ord(column)+1)

    def test_get_table(self):
        table1 = self.table_not_completed.get_table()
        assert table1[0][0] == 0
        table2 = self.table_completed.get_table()
        assert table2[0][0] == 1

    def test_check_if_piece_can_be_placed(self):
        assert self.table_completed.check_if_piece_can_be_placed('A') is False
        assert self.table_not_completed.check_if_piece_can_be_placed('A') is True

    def test_check_if_board_is_not_full(self):
        assert self.table_completed.check_if_board_is_not_full() is False
        assert self.table_not_completed.check_if_board_is_not_full() is True

    def test_place_piece(self):
        self.table_not_completed.place_piece('A', 1)
        self.table_not_completed.place_piece('A', 2)
        table1 = self.table_not_completed.get_table()
        assert table1[5][0] == 1
        assert table1[4][0] == 2

    def test_player_1_won_vertically(self):
        self.table_not_completed.place_piece('A', 1)
        self.table_not_completed.place_piece('A', 1)
        self.table_not_completed.place_piece('A', 1)
        self.table_not_completed.place_piece('A', 1)
        assert self.table_not_completed.player_won(1) is True
        assert self.table_not_completed.player_won(2) is False

    def test_player_1_won_horizontally(self):
        self.table_not_completed.place_piece('A', 1)
        self.table_not_completed.place_piece('B', 1)
        self.table_not_completed.place_piece('C', 1)
        self.table_not_completed.place_piece('D', 1)
        #print(str(self.table_not_completed))
        assert self.table_not_completed.player_won(1) is True
        assert self.table_not_completed.player_won(2) is False

    def test_player_1_won_diagonally(self):
        self.table_not_completed.place_piece('A', 1)
        self.table_not_completed.place_piece('B', 2)
        self.table_not_completed.place_piece('C', 1)
        self.table_not_completed.place_piece('D', 2)
        self.table_not_completed.place_piece('B', 1)
        self.table_not_completed.place_piece('C', 2)
        self.table_not_completed.place_piece('D', 1)
        self.table_not_completed.place_piece('C', 1)
        self.table_not_completed.place_piece('D', 2)
        self.table_not_completed.place_piece('D', 1)
        #print(str(self.table_not_completed))
        assert self.table_not_completed.player_won(1) is True
        assert self.table_not_completed.player_won(2) is False


