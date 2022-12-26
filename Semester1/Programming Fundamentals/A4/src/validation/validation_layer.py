from src.core.core_layer import get_id_participant, get_p1_score, get_p2_score, get_p3_score
from src.exceptions.errors import ValidError


def validate_participant(p):
    error_string = ""
    if get_id_participant(p) < 1 or str(get_id_participant(p)).isnumeric() == False:
        error_string += "invalid participant id\n"
    if str(get_p1_score(p)).isnumeric() == False or get_p1_score(p) < 0 or get_p1_score(p) > 10:
        error_string += "invalid score for P1\n"
    if str(get_p2_score(p)).isnumeric() == False or get_p2_score(p) < 0 or get_p2_score(p) > 10:
        error_string += "invalid score for P2\n"
    if str(get_p3_score(p)).isnumeric() == False or get_p3_score(p) < 0 or get_p3_score(p) > 10:
        error_string += "invalid score for P3\n"
    if len(error_string) > 0:
        raise ValidError(error_string)
