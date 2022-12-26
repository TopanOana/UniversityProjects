"""ui"""
from domain.board import Table
from gameplay.gameplay import GamePlay


class UI:
    def __init__(self):
        self.table = Table()
        self.game_play = GamePlay(self.table)

    def start_up_print(self):
        print("WELCOME TO CONNECT FOUR")
        print("You play as the human player and are represented by the X")
        print("The computer is represented by O")
        print("In order to win you must place four consecutive pieces horizontally, vertically, or diagonally")
        print("Good luck!")

    def human_player_turn(self):
        while True:
            column = input("Choose a column: ").strip()
            column = column.upper()
            if column in "ABCDEFG" and len(column) == 1:
                break
        self.game_play.player_one_turn(column)

    def computer_player_turn(self):
        self.game_play.player_two_turn()

    def start_game(self):
        self.start_up_print()
        while True:
            print(str(self.table))
            if self.table.check_if_board_is_not_full() is False:
                print("Tie!")
                return
            self.human_player_turn()
            if self.table.player_won(1) is True:
                print("You have won!")
                print(str(self.table))
                return
            if self.table.check_if_board_is_not_full() is False:
                print("Tie!")
                return
            self.computer_player_turn()
            if self.table.player_won(2) is True:
                print("Computer has won!")
                print(str(self.table))
                return



