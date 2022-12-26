from src.core.core_layer import create_participant, calculate_average, get_p1_score, get_p2_score, get_p3_score, isfloat
from src.infrastructure.infrastructure import remove_participant


def split_command(command):
    """
    Function that splits commands into command word and the rest of the command
    :return:
    """
    # Remove whitespace at the beginning and end of the command
    command = command.strip()

    # Remove whitespace between the command word and the next elements
    aux = command.split(sep=' ', maxsplit=1)
    return [aux[0].strip().lower(), aux[1].strip().lower()] if len(aux) == 2 else [aux[0].strip().lower(), None]


def participant_init():
    return [create_participant(1, 7, 8, 9), create_participant(2, 8, 8, 8), create_participant(3, 9, 9, 9),
            create_participant(4, 10, 8, 9),
            create_participant(5, 6, 8, 10), create_participant(6, 10, 8, 7),
            create_participant(7, 8, 9, 8), create_participant(8, 7, 7, 7), create_participant(9, 10, 10, 10),
            create_participant(10, 10, 9, 9)]


def participants_sorted_list(participant_list):
    """
    Function that returns a list with the participant list sorted by
    the averages in descending order
    :param participant_list:
    :return:
    """
    participants_sorted = sorted(participant_list, key=calculate_average, reverse=True)
    return participants_sorted


def participants_greater_than(participant_list, value):
    """
    Function that returns a list with the participants with an average
    greater than a specific value
    :param participant_list:
    :param value:
    :return:
    """
    if not isfloat(value):
        raise ValueError("invalid score")
    new_list = []
    for p in participant_list:
        if calculate_average(p) > value:
            new_list.append(p)
    return new_list


def participants_lesser_than(participant_list, value):
    """
    Function that returns a list with the participants with an average
    lesser than a specific value
    :param participant_list:
    :param value:
    :return:
    """
    if not isfloat(value):
        raise ValueError("invalid score")
    new_list = []
    for p in participant_list:
        if calculate_average(p) < value:
            new_list.append(p)
    return new_list


def participants_equal_to(participant_list, value):
    """
    Function that returns a list with the participants with an average
    equal to a specific value
    :param participant_list:
    :param value:
    :return:
    """
    if not isfloat(value):
        raise ValueError("invalid score")
    new_list = []
    for p in participant_list:
        if calculate_average(p) == value:
            new_list.append(p)
    return new_list


def participants_top_average(participant_list, number):
    """
    Function that returns a list with participants with the top
    [number] averages
    :param participant_list:
    :param number:
    :return:
    """
    if str(number).isnumeric() == False or number < 1 or number > len(participant_list):
        raise ValueError("invalid podium number")
    else:
        new_sorted = participants_sorted_list(participant_list)
        new_list = []
        for index in range(0, number):
            new_list.append(new_sorted[index])
        return new_list


def participants_top_problem(participant_list, number, problem):
    """
    Function that returns a list with participants with the top
    [number] scores in a {problem}
    :param participant_list:
    :param number:
    :param problem:
    :return:
    """
    if str(number).isnumeric() == False or number < 1 or number > len(participant_list):
        raise ValueError("invalid podium number")
    else:
        new_list = []
        if problem.lower() == "p1":
            new_list = sorted(participant_list, key=get_p1_score, reverse=True)
        elif problem.lower() == "p2":
            new_list = sorted(participant_list, key=get_p2_score, reverse=True)
        elif problem.lower() == "p3":
            new_list = sorted(participant_list, key=get_p3_score, reverse=True)
        else:
            raise ValueError("invalid problem")
        list_to_return = []
        for i in range(0, number):
            list_to_return.append(new_list[i])
        return list_to_return


def remove_participants_greater_than(participant_list, value):
    """
    Function that removes participants with an average greater
    than a specific value
    :param participant_list:
    :param value:
    :return:
    """
    if isfloat(str(value)) == False or value < 0 or value > 10:
        raise ValueError("invalid score")
    else:
        for i in range(0, len(participant_list)):
            if calculate_average(participant_list[i]) > value:
                remove_participant(participant_list, i)


def remove_participants_lesser_than(participant_list, value):
    """
    Function that removes participants with an average lesser
    than a specific value
    :param participant_list:
    :param value:
    :return:
    """
    if isfloat(str(value)) == False or value < 0 or value > 10:
        raise ValueError("invalid score")
    else:
        for i in range(0, len(participant_list)):
            if calculate_average(participant_list[i]) < value:
                remove_participant(participant_list, i)


def remove_participants_equal_to(participant_list, value):
    """
    Function that removes participants with averages equal
    to a specific value
    :param participant_list:
    :param value:
    :return:
    """
    if isfloat(str(value)) == False or value < 0 or value > 10:
        raise ValueError("invalid score")
    else:
        for i in range(0, len(participant_list)):
            if calculate_average(participant_list[i]) == value:
                remove_participant(participant_list, i)


def average_score_between(participant_list, begin, end):
    """
    Function that returns the average score of all the participants
    in a specified interval
    :param participant_list:
    :param begin:
    :param end:
    :return:
    """
    if str(begin).isnumeric() == False or str(end).isnumeric() == False or begin < 0 or begin > len(
            participant_list) or end < 0 or end > len(participant_list):
        raise ValueError("invalid index")
    else:
        sum = 0
        for i in range(begin, end + 1):
            sum += calculate_average(participant_list[i])
        divided_by = end - begin + 1
        return sum / divided_by


def minimum_score_between(participant_list, begin, end):
    """
    Function that returns the minimum score of all the participants
    in a specified interval
    :param participant_list:
    :param begin:
    :param end:
    :return:
    """
    if str(begin).isnumeric() == False or str(end).isnumeric() == False or begin < 0 or begin > len(
            participant_list) or end < 0 or end > len(participant_list):
        raise ValueError("invalid index")
    else:
        minimum = calculate_average(participant_list[begin])
        for i in range(begin + 1, end + 1):
            av = calculate_average(participant_list[i])
            if minimum > av:
                minimum = av
        return minimum
