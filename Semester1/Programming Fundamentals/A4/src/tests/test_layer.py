from src.business.business_layer import split_command, participants_sorted_list, participants_greater_than, \
    participants_lesser_than, participants_equal_to, participants_top_average, participants_top_problem, \
    remove_participants_greater_than, remove_participants_lesser_than, remove_participants_equal_to, \
    average_score_between, minimum_score_between
from src.core.core_layer import create_participant, get_id_participant, get_p1_score, get_p2_score, get_p3_score, \
    set_p1_score, set_p2_score, set_p3_score, calculate_average, format_participant_to_string, isfloat
from src.exceptions.errors import ValidError
from src.infrastructure.infrastructure import add_participant_to_list, insert_participant_in_list, remove_participant, \
    modify_scores, add_new_list_after_command, remove_list_after_undo
from src.validation.validation_layer import validate_participant


def test_create_participant():
    id_participant = 1
    P1 = 10
    P2 = 10
    P3 = 10
    p = create_participant(id_participant, P1, P2, P3)
    assert (get_id_participant(p) == id_participant)
    assert (get_p1_score(p) == P1)
    assert (get_p2_score(p) == P2)
    assert (get_p3_score(p) == P3)


def test_setters():
    id_participant = 1
    p1 = 9
    p2 = 9
    p3 = 9
    p = create_participant(id_participant, p1, p2, p3)
    new_p1 = 10
    new_p2 = 10
    new_p3 = 10
    set_p1_score(p, new_p1)
    set_p2_score(p, new_p2)
    set_p3_score(p, new_p3)
    assert (get_p1_score(p) == 10)
    assert (get_p2_score(p) == 10)
    assert (get_p3_score(p) == 10)


def test_averages():
    id_participant = 1
    p1 = 9
    p2 = 9
    p3 = 9
    p = create_participant(id_participant, p1, p2, p3)
    assert calculate_average(p) == 9


def test_validate_participant():
    id_participant = 1
    p1 = 9
    p2 = 9
    p3 = 9
    p = create_participant(id_participant, p1, p2, p3)
    validate_participant(p)

    id_p_invalid = -1
    p1_invalid = -1
    p2_invalid = 21
    p3_invalid = 'a'
    try:
        p_bad1 = create_participant(id_p_invalid, p1, p2, p3)
        validate_participant(p_bad1)
    except ValidError as ve:
        assert (str(ve) == "invalid participant id\n")
    try:
        p_bad2 = create_participant(id_participant, p1_invalid, p2, p3)
        validate_participant(p_bad2)
    except ValidError as ve:
        assert (str(ve) == "invalid score for P1\n")
    try:
        p_bad3 = create_participant(id_participant, p1, p2_invalid, p3)
        validate_participant(p_bad3)
    except ValidError as ve:
        assert (str(ve) == "invalid score for P2\n")
    try:
        p_bad4 = create_participant(id_participant, p1, p2, p3_invalid)
        validate_participant(p_bad4)
    except ValidError as ve:
        assert (str(ve) == "invalid score for P3\n")


def test_add_participant():
    id_participant = 1
    p1 = 9
    p2 = 9
    p3 = 9
    p = create_participant(id_participant, p1, p2, p3)
    participant_list = []
    add_participant_to_list(participant_list, p)
    assert len(participant_list) == 1


def test_split_command():
    cmd = "add 10 10 10"
    cmd_word, cmd_rest = split_command(cmd)
    assert cmd_word == "add"
    cmd1 = "list"
    cmd1_word, cmd1_rest = split_command(cmd1)
    assert cmd1_rest is None


def test_format_participant_to_string():
    id_participant = 1
    p1 = 9
    p2 = 9
    p3 = 9
    p = create_participant(id_participant, p1, p2, p3)
    str = format_participant_to_string(p)
    assert str == "id: 1, P1 = 9, P2 = 9, P3 = 9"


def test_sorted_participants_list():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    new_l = participants_sorted_list(l)
    assert format_participant_to_string(p3) == format_participant_to_string(new_l[1])


def test_participants_greater_than():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    new_list = participants_greater_than(l, 3)
    assert format_participant_to_string(p3) == format_participant_to_string(new_list[1])
    try:
        new_list1 = participants_greater_than(l, 'r')
    except ValueError as ve:
        assert str(ve) == "invalid score"


def test_participants_lesser_than():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    new_list = participants_lesser_than(l, 5)
    assert format_participant_to_string(p3) == format_participant_to_string(new_list[1])
    try:
        new_list1 = participants_lesser_than(l, 'r')
    except ValueError as ve:
        assert str(ve) == "invalid score"


def test_participants_equal_to():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    new_list = participants_equal_to(l, 4)
    assert format_participant_to_string(p3) == format_participant_to_string(new_list[0])
    try:
        new_list1 = participants_equal_to(l,20)
    except ValueError as ve:
        assert str(ve) == "invalid score"


def test_insert_participant_in_list():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    insert_participant_in_list(l, p3, 1)
    assert format_participant_to_string(l[1]) == format_participant_to_string(p3)
    try:
        p4 = create_participant(4, 4, 6, 4)
        insert_participant_in_list(l, p4, -1)
    except ValueError as ve:
        assert str(ve) == "invalid index"
    try:
        p4 = create_participant(4, 4, 6, 4)
        insert_participant_in_list(l, p4, 20)
    except ValueError as ve:
        assert str(ve) == "invalid index"


def test_remove_participant():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    remove_participant(l, 1)
    p4 = create_participant(2, 0, 0, 0)
    assert format_participant_to_string(l[1]) == format_participant_to_string(p4)
    try:
        remove_participant(l, -1)
    except ValueError as ve:
        assert str(ve) == "invalid index"
    try:
        remove_participant(l, 20)
    except ValueError as ve:
        assert str(ve) == "invalid index"


def test_modify_scores():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    modify_scores(l, 2, 6, "p2")
    p4 = create_participant(2, 3, 6, 2)
    assert format_participant_to_string(l[1]) == format_participant_to_string(p4)
    try:
        modify_scores(l, "a", 6, "p2")
    except ValueError as ve:
        assert str(ve) == "invalid participant id"
    try:
        modify_scores(l, -1, 6, "p2")
    except ValueError as ve:
        assert str(ve) == "invalid participant id"
    try:
        modify_scores(l, 90, 6, "p2")
    except ValueError as ve:
        assert str(ve) == "invalid participant id"
    try:
        modify_scores(l, 1, "p", "p2")
    except ValidError as ve:
        assert str(ve) == "invalid score"
    try:
        modify_scores(l, 1, -1, "p2")
    except ValidError as ve:
        assert str(ve) == "invalid score"
    try:
        modify_scores(l, 1, 11, "p2")
    except ValidError as ve:
        assert str(ve) == "invalid score"
    try:
        modify_scores(l, 1, 10, "p4")
    except ValueError as ve:
        assert str(ve) == "problem does not exist"


def test_participants_top_average():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 4, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    new_list = participants_top_average(l, 1)
    assert len(new_list) == 1
    try:
        new_list1 = participants_top_average(l, "a")
    except ValueError as ve:
        assert str(ve) == "invalid podium number"
    try:
        new_list1 = participants_top_average(l, -2)
    except ValueError as ve:
        assert str(ve) == "invalid podium number"
    try:
        new_list1 = participants_top_average(l, 90)
    except ValueError as ve:
        assert str(ve) == "invalid podium number"


def test_participants_top_problem():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    new_list = participants_top_problem(l, 1, "p2")
    assert len(new_list) == 1
    try:
        new_list1 = participants_top_problem(l, "a", "p2")
    except ValueError as ve:
        assert str(ve) == "invalid podium number"
    try:
        new_list1 = participants_top_problem(l, -2, "p2")
    except ValueError as ve:
        assert str(ve) == "invalid podium number"
    try:
        new_list1 = participants_top_problem(l, 90, "p2")
    except ValueError as ve:
        assert str(ve) == "invalid podium number"
    try:
        new_list1 = participants_top_problem(l, 1, "p8")
    except ValueError as ve:
        assert str(ve) == "invalid problem"


def test_remove_participants_greater_than():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    remove_participants_greater_than(l, 2)
    p4 = create_participant(1, 0, 0, 0)
    assert format_participant_to_string(l[0]) == format_participant_to_string(p4)
    try:
        remove_participants_greater_than(l, "y")
    except ValueError as ve:
        assert str(ve) == "invalid score"


def test_remove_participants_lesser_than():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    remove_participants_lesser_than(l, 9)
    p4 = create_participant(2, 0, 0, 0)
    assert format_participant_to_string(l[1]) == format_participant_to_string(p4)
    try:
        remove_participants_lesser_than(l, "y")
    except ValueError as ve:
        assert str(ve) == "invalid score"


def test_remove_participants_equal_to():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    remove_participants_equal_to(l, 3)
    p4 = create_participant(2, 0, 0, 0)
    assert format_participant_to_string(l[1]) == format_participant_to_string(p4)
    try:
        remove_participants_equal_to(l, "y")
    except ValueError as ve:
        assert str(ve) == "invalid score"


def test_isfloat():
    assert isfloat("0.56") == True
    assert isfloat("a76") == False
    assert isfloat("1") == True


def test_average_participants_between():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    p4 = create_participant(4, 6, 7, 8)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    add_participant_to_list(l, p4)
    assert abs(average_score_between(l, 1, 3) - (
            calculate_average(p2) + calculate_average(p3) + calculate_average(p4)) / 3) < 0.001
    try:
        x = average_score_between(l, 'a', 'c')
    except ValueError as ve:
        assert str(ve) == "invalid index"
    try:
        x = average_score_between(l, 1, 'c')
    except ValueError as ve:
        assert str(ve) == "invalid index"
    try:
        x = average_score_between(l, 'a', 1)
    except ValueError as ve:
        assert str(ve) == "invalid index"


def test_minimum_participants_between():
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    p4 = create_participant(4, 6, 7, 8)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    add_participant_to_list(l, p4)
    assert abs(minimum_score_between(l, 0, 2) - calculate_average(p2)) < 0.001
    try:
        x = minimum_score_between(l, 'a', 'a')
    except ValueError as ve:
        assert str(ve) == "invalid index"
    try:
        x = minimum_score_between(l, 1, 'a')
    except ValueError as ve:
        assert str(ve) == "invalid index"
    try:
        x = minimum_score_between(l, 'a', 2)
    except ValueError as ve:
        assert str(ve) == "invalid index"


def test_add_new_list_to_history():
    history = []
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    p4 = create_participant(4, 6, 7, 8)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    add_participant_to_list(l, p4)
    add_new_list_after_command(history, l)
    assert len(history) == 1
    remove_participants_lesser_than(l, 8)
    add_new_list_after_command(history, l)
    assert len(history) == 2


def test_remove_list_from_history():
    history = []
    p1 = create_participant(1, 10, 10, 10)
    p2 = create_participant(2, 3, 4, 2)
    p3 = create_participant(3, 4, 6, 4)
    p4 = create_participant(4, 6, 7, 8)
    l = []
    add_participant_to_list(l, p1)
    add_participant_to_list(l, p2)
    add_participant_to_list(l, p3)
    add_participant_to_list(l, p4)
    add_new_list_after_command(history, l)
    assert len(history) == 1
    remove_participants_lesser_than(l, 8)
    add_new_list_after_command(history, l)
    assert len(history) == 2
    remove_list_after_undo(history)
    assert len(history) == 1


def tests():
    print("starting tests...")
    test_create_participant()
    test_setters()
    test_averages()
    test_validate_participant()
    test_add_participant()
    test_split_command()
    test_format_participant_to_string()
    test_sorted_participants_list()
    test_participants_greater_than()
    test_participants_lesser_than()
    test_participants_equal_to()
    test_insert_participant_in_list()
    test_remove_participant()
    test_modify_scores()
    test_participants_top_average()
    test_participants_top_problem()
    test_remove_participants_greater_than()
    test_remove_participants_lesser_than()
    test_remove_participants_equal_to()
    test_isfloat()
    test_average_participants_between()
    test_minimum_participants_between()
    test_add_new_list_to_history()
    test_remove_list_from_history()
    print("finishing tests...")


tests()
