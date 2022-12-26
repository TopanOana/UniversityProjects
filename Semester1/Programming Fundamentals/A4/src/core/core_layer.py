"""
Module for all structures that are needed.
AKA: creating a participant, getters, setters etc.

"""


def create_participant(id_participant, p1, p2, p3):
    """
    Function to create a new participant entry
    :param p1: problem 1 score
    :param p2: problem 2 score
    :param p3: problem 3 score
    :return: the new participant entry
    """
    return {"id participant": id_participant, "P1 score": p1, "P2 score": p2, "P3 score": p3}


def get_id_participant(p):
    return p["id participant"]


def get_p1_score(p):
    return p["P1 score"]


def get_p2_score(p):
    return p["P2 score"]


def get_p3_score(p):
    return p["P3 score"]


def set_p1_score(participant, value):
    participant["P1 score"] = value


def set_p2_score(participant, value):
    participant["P2 score"] = value


def set_p3_score(participant, value):
    participant["P3 score"] = value


def calculate_average(participant):
    sum_of_scores = get_p1_score(participant) + get_p2_score(participant) + get_p3_score(participant)
    return sum_of_scores / 3


def format_participant_to_string(p):
    string_participant = "id: " + str(get_id_participant(p)) + ", P1 = " + str(get_p1_score(p)) + ", P2 = " + str(
        get_p2_score(p)) + ", P3 = " + str(get_p3_score(p))
    return string_participant


def isfloat(number):
    """
    checks whether a string is a real number
    :param number:
    :return: True if the string is a real number, False otherwise
    """
    try:
        float(number)
        return True
    except ValueError:
        return False
