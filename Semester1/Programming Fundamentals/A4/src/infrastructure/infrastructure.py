from src.core.core_layer import set_p1_score, set_p2_score, set_p3_score, get_id_participant
from src.exceptions.errors import ValidError
import copy


def add_participant_to_list(l, p):
    l.append(p)


def insert_participant_in_list(l, p, index):
    if index < 0 or index > (len(l)):
        raise ValueError("invalid index")
    else:
        l.insert(index, p)


def remove_participant(l, index):
    if index < 0 or index >= (len(l)):
        raise ValueError("invalid index")
    else:
        set_p1_score(l[index], 0)
        set_p2_score(l[index], 0)
        set_p3_score(l[index], 0)


def modify_scores(l, id, new_score, problem):
    if str(new_score).isnumeric() == False or new_score < 0 or new_score > 10:
        raise ValidError("invalid score")
    elif str(id).isnumeric() == False or id > len(l) or id < 0:
        raise ValueError("invalid participant id")
    else:
        index = 0
        while get_id_participant(l[index]) != id:
            index += 1
        if problem.lower() == 'p1':
            set_p1_score(l[index], new_score)
        elif problem.lower() == 'p2':
            set_p2_score(l[index], new_score)
        elif problem.lower() == 'p3':
            set_p3_score(l[index], new_score)
        else:
            raise ValueError("problem does not exist")


def add_new_list_after_command(history_of_lists, participant_list):
    new_list = copy.deepcopy(participant_list)
    history_of_lists.append(new_list)


def remove_list_after_undo(history_of_lists):
    history_of_lists.pop()

