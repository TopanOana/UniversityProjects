from domain.board import Table
from gameplay.gameplay import GamePlay
import unittest


class Test_gameplay(unittest.TestCase):
    def setUp(self) -> None:
        self.table = Table()
        self.gameplay = GamePlay(self.table)

    def test_check_if_column_provided_is_valid(self):
        assert self.gameplay.check_if_column_provided_is_valid('A')

    def test_player_one_turn(self):
        self.gameplay.player_one_turn('A')
        table = self.table.get_table()
        assert table[5][0] == 1

    def test_stop_horizontal_win(self):
        assert self.gameplay.stop_horizontal_win() is None
        self.gameplay.player_one_turn('A')
        self.gameplay.player_one_turn('B')
        self.gameplay.player_one_turn('C')
        assert self.gameplay.stop_horizontal_win() == 3

    def test_stop_vertical_win(self):
        assert self.gameplay.stop_vertical_win() is None
        self.gameplay.player_one_turn('A')
        self.gameplay.player_one_turn('A')
        self.gameplay.player_one_turn('A')
        assert self.gameplay.stop_vertical_win() == 0

    def test_stop_diagonal_win(self):
        assert self.gameplay.stop_diagonal_win() is None
        self.table.place_piece('A', 1)
        self.table.place_piece('B', 2)
        self.table.place_piece('C', 1)
        self.table.place_piece('D', 2)
        self.table.place_piece('B', 1)
        self.table.place_piece('C', 2)
        self.table.place_piece('D', 1)
        self.table.place_piece('C', 1)
        self.table.place_piece('D', 2)
        assert self.gameplay.stop_diagonal_win() == 3

    def test_win_horizontal(self):
        assert self.gameplay.win_horizontal() is None
        self.table.place_piece('A', 2)
        self.table.place_piece('B', 2)
        self.table.place_piece('C', 2)
        assert self.gameplay.win_horizontal() == 3

    def test_win_vertical(self):
        assert self.gameplay.win_vertical() is None
        self.table.place_piece('A', 2)
        self.table.place_piece('A', 2)
        self.table.place_piece('A', 2)
        assert self.gameplay.win_vertical() == 0

    def test_win_diagonal(self):
        assert self.gameplay.win_diagonal() is None
        self.table.place_piece('A', 2)
        self.table.place_piece('B', 1)
        self.table.place_piece('C', 2)
        self.table.place_piece('D', 1)
        self.table.place_piece('B', 2)
        self.table.place_piece('C', 1)
        self.table.place_piece('D', 2)
        self.table.place_piece('C', 2)
        self.table.place_piece('D', 1)
        assert self.gameplay.win_diagonal() == 3

    def test_player_two_turn_diag_win(self):
        self.table.place_piece('A', 2)
        self.table.place_piece('B', 1)
        self.table.place_piece('C', 2)
        self.table.place_piece('D', 1)
        self.table.place_piece('B', 2)
        self.table.place_piece('C', 1)
        self.table.place_piece('D', 2)
        self.table.place_piece('C', 2)
        self.table.place_piece('D', 1)
        self.gameplay.player_two_turn()
        table = self.table.get_table()
        assert table[2][3] == 2

    def test_player_two_turn_horiz_win(self):
        self.table.place_piece('A', 2)
        self.table.place_piece('B', 2)
        self.table.place_piece('C', 2)
        self.gameplay.player_two_turn()
        table = self.table.get_table()
        assert table[5][3] == 2

    def test_player_two_turn_vert_win(self):
        self.table.place_piece('A', 2)
        self.table.place_piece('A', 2)
        self.table.place_piece('A', 2)
        self.gameplay.player_two_turn()
        table = self.table.get_table()
        assert table[2][0] == 2

    def test_player_two_turn_diag_obstruct(self):
        self.table.place_piece('A', 1)
        self.table.place_piece('B', 2)
        self.table.place_piece('C', 1)
        self.table.place_piece('D', 2)
        self.table.place_piece('B', 1)
        self.table.place_piece('C', 2)
        self.table.place_piece('D', 1)
        self.table.place_piece('C', 1)
        self.table.place_piece('D', 2)
        self.gameplay.player_two_turn()
        table = self.table.get_table()
        assert table[2][3] == 2

    def test_player_two_turn_horiz_obstruct(self):
        self.gameplay.player_one_turn('A')
        self.gameplay.player_one_turn('B')
        self.gameplay.player_one_turn('C')
        self.gameplay.player_two_turn()
        table = self.table.get_table()
        assert table[5][3] == 2

    def test_player_two_turn_vert_obstruct(self):
        self.gameplay.player_one_turn('A')
        self.gameplay.player_one_turn('A')
        self.gameplay.player_one_turn('A')
        self.gameplay.player_two_turn()
        table = self.table.get_table()
        assert table[2][0] == 2

    def test_player_two_turn_random(self):
        self.gameplay.player_two_turn()
        table =  self.table.get_table()
        number = 0
        for i in range(0, 6):
            for j in range(0, 7):
                if table[i][j] == 2:
                    number+=1
        assert number == 1

