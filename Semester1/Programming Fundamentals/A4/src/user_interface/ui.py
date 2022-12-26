import copy

from src.business.business_layer import split_command, participant_init, participants_sorted_list, \
    participants_greater_than, participants_lesser_than, participants_equal_to, participants_top_average, \
    participants_top_problem, remove_participants_greater_than, remove_participants_lesser_than, \
    remove_participants_equal_to, average_score_between, minimum_score_between
from src.core.core_layer import format_participant_to_string, create_participant
from src.exceptions.errors import ValidError
from src.infrastructure.infrastructure import add_participant_to_list, insert_participant_in_list, remove_participant, \
    modify_scores, add_new_list_after_command, remove_list_after_undo
from src.validation.validation_layer import validate_participant


def list_participants(participant_list):
    """
    Function to print a list of participants
    :param participant_list:
    :return:
    """
    for p in participant_list:
        print(format_participant_to_string(p))


def list_participants_ui(participant_list, cmd_rest):
    """
       Function to call the different type of displays.
       :param participant_list: list of current participants
       :return:
    """
    list_to_print = []
    if cmd_rest is None:
        list_to_print = participant_list
    else:
        splits = cmd_rest.split(' ', maxsplit=1)
        if splits[0].strip().lower() == 'sorted':
            list_to_print = participants_sorted_list(participant_list)
        else:
            if len(splits) == 1:
                raise ValueError("Incorrect spacing")
            else:
                splits[0] = splits[0].strip()
                average = int(splits[1].strip())
                if splits[0] == '>':
                    list_to_print = participants_greater_than(participant_list, average)
                elif splits[0] == '<':
                    list_to_print = participants_lesser_than(participant_list, average)
                elif splits[0] == '=':
                    list_to_print = participants_equal_to(participant_list, average)
                else:
                    raise ValueError('Use a correct comparison')
    list_participants(list_to_print)


def add_new_participant_ui(participant_list, cmd_rest):
    """
    Function to add a new participant while communicating with the UI
    :param participant_list:
    :param cmd_rest:
    :return:
    """
    scores = cmd_rest.split(sep=' ', maxsplit=2)
    if len(scores) != 3:
        raise ValueError("incorrect command")
    else:
        p1 = int(scores[0].strip())
        p2 = int(scores[1].strip())
        p3 = int(scores[2].strip())
        new_participant = create_participant(len(participant_list) + 1, p1, p2, p3)
        try:
            validate_participant(new_participant)
            add_participant_to_list(participant_list, new_participant)
            print("participant added successfully")
        except ValidError as ve:
            raise ValidError(str(ve))


def insert_new_participant_ui(participant_list, cmd_rest):
    """
    Function to insert a new participant while communicating with the UI
    :param participant_list:
    :param cmd_rest:
    :return:
    """
    splits = cmd_rest.split(sep=' ', maxsplit=4)
    if len(splits) < 5 or splits[3] != 'at':
        raise ValueError("Command incorrect")
    else:
        p1 = int(splits[0].strip())
        p2 = int(splits[1].strip())
        p3 = int(splits[2].strip())
        p = create_participant(len(participant_list) + 1, p1, p2, p3)
        try:
            validate_participant(p)
            index = int(splits[4].strip())
            insert_participant_in_list(participant_list, p, index)
            print("participant inserted successfully")
        except ValueError as ve:
            raise ValueError(str(ve))
        except ValidError as ve:
            raise ValidError(str(ve))


def remove_participants_ui(participant_list, cmd_rest):
    """
    Function to remove participants based on the different types of commands available
    :param participant_list:
    :param cmd_rest:
    :return:
    """
    splits = cmd_rest.split(sep=' ', maxsplit=2)
    try:
        if len(splits) == 1:
            index = int(splits[0].strip())
            remove_participant(participant_list, index)
            print("participant removed successfully")
        else:
            if splits[1].strip() == "to":
                begin = int(splits[0].strip())
                if len(splits) == 2:
                    raise ValueError("incorrect command")
                else:
                    end = int(splits[2].strip())
                    if begin < 0 or end >= len(participant_list):
                        raise ValueError("incorrect parameters")
                    for i in range(begin, end + 1):
                        remove_participant(participant_list, i)
            else:
                if splits[0].strip() == ">":
                    value = float(splits[1].strip())
                    remove_participants_greater_than(participant_list, value)
                elif splits[0].strip() == "<":
                    value = float(splits[1].strip())
                    remove_participants_lesser_than(participant_list, value)
                elif splits[0].strip() == "=":
                    value = float(splits[1].strip())
                    remove_participants_equal_to(participant_list, value)
                else:
                    raise ValueError("incorrect command")
                print("participants removed successfully.")
    except ValueError as ve:
        raise ValueError(str(ve))


def replace_participant_scores_ui(participant_list, cmd_rest):
    """
    Function to replace participant scores while communicating with the UI
    :param participant_list:
    :param cmd_rest:
    :return:
    """
    splits = cmd_rest.split(sep=' ', maxsplit=3)
    if len(splits) < 4 or splits[2] != 'with':
        raise ValueError("Incorrect command")
    else:
        try:
            index = int(splits[0].strip().lower())
            problem = splits[1].strip().lower()
            new_score = int(splits[3].strip())
            modify_scores(participant_list, index, new_score, problem)
            print("score modified successfully.")
        except ValidError as ve:
            raise ValidError(str(ve))
        except ValueError as ve:
            raise ValueError(str(ve))


def podium_top_ui(participant_list, cmd_rest):
    """
    Function to show the podium while communicating with the UI
    :param participant_list:
    :param cmd_rest:
    :return:
    """
    splits = cmd_rest.split(sep=' ', maxsplit=1)
    try:
        podium = []
        if len(splits) == 1:
            number = int(splits[0].strip())
            podium = participants_top_average(participant_list, number)
        else:
            number = int(splits[0].strip())
            problem = splits[1].strip()
            podium = participants_top_problem(participant_list, number, problem)
        list_participants(podium)
    except ValueError as ve:
        raise ValueError(str(ve))


def average_participants_between_ui(participant_list, cmd_rest):
    """
    Function to print the average score of all participants in an interval
    while communicating with the UI
    :param participant_list:
    :param cmd_rest:
    :return:
    """
    splits = cmd_rest.split(sep=' ', maxsplit=2)
    if len(splits) < 3 or splits[1].strip().lower() != "to":
        raise ValueError("invalid command")
    else:
        try:
            begin = int(splits[0].strip())
            end = int(splits[2].strip())
            print(average_score_between(participant_list, begin, end))
        except ValueError as ve:
            raise ValueError(str(ve))


def minimum_participant_between_ui(participant_list, cmd_rest):
    """
    Function to print the minimum score of all participants in an interval
    while communicating with the UI
    :param participant_list:
    :param cmd_rest:
    :return:
    """
    splits = cmd_rest.split(sep=' ', maxsplit=2)
    if len(splits) < 3 or splits[1].strip().lower() != "to":
        raise ValueError("invalid command")
    else:
        try:
            begin = int(splits[0].strip())
            end = int(splits[2].strip())
            print(minimum_score_between(participant_list, begin, end))
        except ValueError as ve:
            raise ValueError(str(ve))


def undo_last_operation_ui(participant_list, history_of_operations):
    if len(history_of_operations) > 1:
        remove_list_after_undo(history_of_operations)
        print("undo complete")
    else:
        print("undo not possible")


def start():
    history_of_operations = []
    participant_list = participant_init()
    add_new_list_after_command(history_of_operations, participant_list)
    while True:
        command = input(">>")
        valid = 1
        cmd_word, cmd_rest = split_command(command)
        try:
            if cmd_word == "list":
                list_participants_ui(participant_list, cmd_rest)
            elif cmd_word == "add" and cmd_rest is not None:
                add_new_participant_ui(participant_list, cmd_rest)
            elif cmd_word == "insert" and cmd_rest is not None:
                insert_new_participant_ui(participant_list, cmd_rest)
            elif cmd_word == "remove" and cmd_rest is not None:
                remove_participants_ui(participant_list, cmd_rest)
            elif cmd_word == "replace" and cmd_rest is not None:
                replace_participant_scores_ui(participant_list, cmd_rest)
            elif cmd_word == "top" and cmd_rest is not None:
                podium_top_ui(participant_list, cmd_rest)
            elif cmd_word == "avg" and cmd_rest is not None:
                average_participants_between_ui(participant_list, cmd_rest)
            elif cmd_word == "min" and cmd_rest is not None:
                minimum_participant_between_ui(participant_list, cmd_rest)
            elif cmd_word == "undo" and cmd_rest is None:
                undo_last_operation_ui(participant_list, history_of_operations)
                participant_list = copy.deepcopy(history_of_operations[-1])
                valid = 0
            elif cmd_word == "exit":
                return
            else:
                print("command does not exist")
                valid = 0
        except ValueError as ve:
            valid = 0
            print(str(ve))
        except ValidError as ve:
            valid = 0
            print(str(ve))
        if valid == 1:
            add_new_list_after_command(history_of_operations, participant_list)


start()
