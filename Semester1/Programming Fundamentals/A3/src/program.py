"""
  Write non-UI functions below
"""


def create_participant(p1, p2, p3):
    """
    Function to create a new participant entry
    :param p1: problem 1 score
    :param p2: problem 2 score
    :param p3: problem 3 score
    :return: the new participant entry
    """
    return {"P1 score": p1, "P2 score": p2, "P3 score": p3}


def get_p1_score(participant):
    return participant["P1 score"]


def get_p2_score(participant):
    return participant["P2 score"]


def get_p3_score(participant):
    return participant["P3 score"]


def set_p1_score(participant, value):
    participant["P1 score"] = value


def set_p2_score(participant, value):
    participant["P2 score"] = value


def set_p3_score(participant, value):
    participant["P3 score"] = value


def participant_init():
    return [create_participant(7, 8, 9), create_participant(8, 8, 8), create_participant(9, 9, 9),
            create_participant(10, 8, 9),
            create_participant(6, 8, 10), create_participant(10, 8, 7),
            create_participant(8, 9, 8), create_participant(7, 7, 7), create_participant(10, 10, 10),
            create_participant(10, 9, 9)]


def add_new_participant(participant_list, participant):
    """
    Function that adds a new participant to the list
    :param participant_list: list of current participants
    :param participant: new participant
    :return:
    """
    participant_list.append(participant)


def insert_participant(participant_list, participant, index):
    """
    Function that inserts a new participant at the index
    :param participant_list: list of current participants
    :param participant: new participant
    :param index: index at which to insert new participant
    :return:
    """
    participant_list.insert(index, participant)


def remove_participant(participant_list, index):
    """
    Function that removes the participant's scores
    :param participant_list: list of current participants
    :param index: index of the participant to be removed
    :return:
    """
    set_p1_score(participant_list[index], 0)
    set_p2_score(participant_list[index], 0)
    set_p3_score(participant_list[index], 0)


def modify_scores(participant_list, index, new_score, problem):
    """
    Function that modifies the score of a problem for a certain participant
    :param participant_list: list of current participants
    :param index: index of the participant to change
    :param new_score: self-explanatory
    :param problem: which problem to modify
    :return:
    """
    if new_score < 0 or new_score > 10:
        raise ValueError("Scores must be between 0 and 10.")
    else:
        if problem.lower() == 'p1':
            set_p1_score(participant_list[index], new_score)
        elif problem.lower() == 'p2':
            set_p2_score(participant_list[index], new_score)
        elif problem.lower() == 'p3':
            set_p3_score(participant_list[index], new_score)
        else:
            raise ValueError('Problem does not exist')


def split_command(command):
    """
    Function that splits commands into command word and the rest of the command
    examples :
    add <P1 score> <P2 score> <P3 score>
    insert <P1 score> <P2 score> <P3 score> at <position>
    remove <position>
    remove <start position> to <end position>
    replace <old score> <P1 | P2 | P3> with <new score>
    list
    list sorted
    list [ < | = | > ] <score>
    :return:
    """
    # Remove whitespace at the beginning and end of the command
    command = command.strip()

    # Remove whitespace between the command word and the next elements
    aux = command.split(sep=' ', maxsplit=1)
    return [aux[0].strip().lower(), aux[1].strip().lower()] if len(aux) == 2 else [aux[0].strip().lower(), None]


def format_participant_to_string(participant):
    participant_string = "P1 score = " + str(get_p1_score(participant)) + ", P2 score = " + str(
        get_p2_score(participant)) + ", P3 score = " + str(get_p3_score(participant))
    return participant_string


def calculate_average(participant):
    sum_of_scores = get_p1_score(participant) + get_p2_score(participant) + get_p3_score(participant)
    return sum_of_scores / 3


def new_participants_sorted(participant_list):
    participants_sorted = sorted(participant_list, key=calculate_average, reverse=True)
    return participants_sorted


"""
  Write the command-driven UI below
"""


def list_all_participants_UI(participant_list):
    """
    Function that shows all the participants
    :param participant_list:
    :return:
    """
    for i in range(0, len(participant_list)):
        to_print = "Participant " + str(i) + ": " + format_participant_to_string(participant_list[i])
        print(to_print)


def add_new_participant_UI(participant_list, command_rest):
    """
    Function to add a new participant in the UI
    :param participant_list:
    :param command_rest:
    :return:
    """
    scores = command_rest.split(sep=' ', maxsplit=2)
    p1 = int(scores[0].strip())
    p2 = int(scores[1].strip())
    p3 = int(scores[2].strip())
    if p1 < 0 or p1 > 10 or p2 < 0 or p2 > 10 or p3 < 0 or p3 > 10:
        raise ValueError('Scores must be between 0 and 10.')
    else:
        add_new_participant(participant_list, create_participant(p1, p2, p3))
        print("Participant added successfully.")


def insert_new_participant_UI(participant_list, command_rest):
    """
    Function to insert a new participant into the list at a specific index
    :param participant_list: list of current participants
    :param command_rest: rest of command
    :return:
    insert <P1 score> <P2 score> <P3 score> at <position>
    """
    splits = command_rest.split(sep=' ', maxsplit=4)
    if len(splits) < 5 or splits[3] != 'at':
        raise ValueError("Command incorrect")
    else:
        p1 = int(splits[0].strip())
        p2 = int(splits[1].strip())
        p3 = int(splits[2].strip())
        if p1 < 0 or p1 > 10 or p2 < 0 or p2 > 10 or p3 < 0 or p3 > 10:
            raise ValueError('Scores must be between 0 and 10.')
        else:
            index = int(splits[4].strip())
            if index < 0 or index > len(participant_list):
                raise ValueError("Invalid index")
            else:
                insert_participant(participant_list, create_participant(p1, p2, p3), index)
                print("Participant inserted successfully.")


def remove_participants_UI(participant_list, command_rest):
    """
    Function to remove one or more participants from the list of participants
    :param participant_list: list of current participants
    :param command_rest: rest of command
    :return:
    """
    splits = command_rest.split(sep=' ', maxsplit=2)
    if len(splits) == 1:
        index = int(splits[0].strip())
        if index > len(participant_list) or index < 0:
            raise ValueError("Index invalid")
        else:
            remove_participant(participant_list, index)
            print("Participant removed successfully.")
    else:
        begin = int(splits[0].strip())
        if len(splits) == 2:
            raise ValueError("Incorrect command")
        else:
            end = int(splits[2].strip())
            if begin < 0 or end >= len(participant_list):
                raise ValueError("Incorrect parameters")
            for i in range(begin, end + 1):
                remove_participant(participant_list, i)
            print("Participants removed successfully.")


def replace_participant_scores_UI(participant_list, command_rest):
    """
    Function to replace a score from <problem 1/2/3> for a certain participant
    :param participant_list: list of current participants
    :param command_rest: rest of command
    replace <old score> <P1 | P2 | P3> with <new score>
    :return:
    """
    splits = command_rest.split(sep=' ', maxsplit=3)
    if len(splits) < 4 or splits[2] != 'with':
        raise ValueError("Incorrect command")
    else:
        index = int(splits[0].strip())
        if index < 0:
            raise ValueError("Invalid index")
        else:
            problem = splits[1].strip().lower()
            new_score = int(splits[3].strip())
            modify_scores(participant_list, index, new_score, problem)
            print("Score modified successfully.")


def list_sorted_participants_UI(participant_list):
    """
    Function that prints a sorted list of all the participants in descending order
    after the average score.
    :param participant_list: list of current participants
    :return:
    """
    participants_sorted = new_participants_sorted(participant_list)
    for i in range(0, len(participants_sorted)):
        print(format_participant_to_string(participants_sorted[i]))


def list_participants_greater_than(participant_list, average):
    """
    Function prints all the participants with an average greater than a given average
    :param participant_list: list of current participants
    :param average: used for comparison
    :return:
    """
    for i in range(0, len(participant_list)):
        if calculate_average(participant_list[i]) > average:
            to_print = "Participant " + str(i) + ": " + format_participant_to_string(participant_list[i])
            print(to_print)


def list_participants_lesser_than(participant_list, average):
    """
    Function prints all the participants with an average lesser than a given average
    :param participant_list: list of current participants
    :param average: used for comparison
    :return:
    """
    for i in range(0, len(participant_list)):
        if calculate_average(participant_list[i]) < average:
            to_print = "Participant " + str(i) + ": " + format_participant_to_string(participant_list[i])
            print(to_print)


def list_participants_equal_to(participant_list, average):
    """
    Function prints all the participants with the average equal to a given average
    :param participant_list: list of current participants
    :param average: used for comparison
    :return:
    """
    for i in range(0, len(participant_list)):
        if calculate_average(participant_list[i]) == average:
            to_print = "Participant " + str(i) + ": " + format_participant_to_string(participant_list[i])
            print(to_print)


def list_participants_UI(participant_list, command_rest):
    """
    Function to call the different type of displays.
    :param participant_list: list of current participants
    :param command_rest: rest of command
    :return:
    """
    if command_rest is None:
        list_all_participants_UI(participant_list)
    else:
        splits = command_rest.split(' ', maxsplit=1)
        if splits[0].strip().lower() == 'sorted':
            list_sorted_participants_UI(participant_list)
        else:
            if len(splits) == 1:
                raise ValueError("Incorrect spacing")
            else:
                splits[0] = splits[0].strip()
                average = int(splits[1].strip())
                if splits[0] == '>':
                    list_participants_greater_than(participant_list, average)
                elif splits[0] == '<':
                    list_participants_lesser_than(participant_list, average)
                elif splits[0] == '=':
                    list_participants_equal_to(participant_list, average)
                else:
                    raise ValueError('Use a correct comparison')


def start():
    participant_list = participant_init()
    while True:
        command = input("prompt> ")
        command_word, command_rest = split_command(command)
        try:
            if command_word == 'list':
                list_participants_UI(participant_list, command_rest)
            elif command_word == 'add' and command_rest is not None:
                add_new_participant_UI(participant_list, command_rest)
            elif command_word == 'insert' and command_rest is not None:
                insert_new_participant_UI(participant_list, command_rest)
            elif command_word == 'remove' and command_rest is not None:
                remove_participants_UI(participant_list, command_rest)
            elif command_word == 'replace' and command_rest is not None:
                replace_participant_scores_UI(participant_list, command_rest)
            elif command_word == 'exit':
                return
            else:
                print("Command does not exist")
        except ValueError as ve:
            print(str(ve))


start()
