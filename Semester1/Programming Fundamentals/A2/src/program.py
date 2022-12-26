#
# Write the implementation for A2 in this file
#

# UI section
# (write all functions that have input or print statements here). 
# Ideally, this section should not contain any calculations relevant to program functionalities
import math

"""NON-UI FUNCTIONS"""


def create_number(a, b):
    """
    Function to create a complex number object
    :param a: real part
    :param b: imaginary part
    :return: the complex number
    """
    return {"real": a, "imag": b}


def init_numbers():
    """
    Function to create some complex numbers to use at startup.
    :return: a list of complex numbers
    """
    return [create_number(0, 0), create_number(1, 0), create_number(0, 1), create_number(1, -1), create_number(1, 3),
            create_number(2, 1), create_number(3, -1), create_number(0, 2), create_number(3, -2), create_number(1, -1)]


def get_real(number):
    return number["real"]


def get_imag(number):
    return number["imag"]

def add_number(numbers_list, number):
    """

    :param numbers_list: list of numbers
    :param number: the new number to be added
    :return:
    """
    numbers_list.append(number)


def calculate_modulus(number):
    """
    Function calculates the modulus of a complex number
    :param number:
    :return: modulus of the complex number
    """
    mod = get_real(number) * get_real(number) + get_imag(number) * get_imag(number)
    mod = math.sqrt(mod)
    return mod


def test_calculate_modulus():
    """
    test function dor calculate_modulus()
    :return:
    """
    assert calculate_modulus(create_number(3, 4)) == 5
    assert calculate_modulus(create_number(4, 3)) == 5
    assert calculate_modulus(create_number(6, 8)) == 10


def digits_used(number):
    """
    creates a frequency list that has all the appearances of
    each digit in a complex number
    :param number:
    :return: frequency list
    """
    fr = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    a = abs(get_real(number))
    if a==0: fr[a]=1
    else :
        while a > 0:
            fr[a % 10] = 1
            a = a // 10
    b = abs(get_imag(number))
    if b==0: fr[b]=1
    else:
        while b > 0:
            fr[b % 10] = 1
            b = b // 10
    return fr


def test_digits_used():
    """
    test function for digits_used()
    :return:
    """
    fr = digits_used(create_number(1, -1))
    for i in range(0, 10):
        print(fr[i])
    fr = digits_used(create_number(0,-1))
    print("middle")
    for i in range(0, 10):
        print(fr[i])



#test_digits_used()


def same_digits(fr1, fr2):
    """
    checks whether two frequency lists are the same
    :param fr1: frequency list 1
    :param fr2: frequency list 2
    :return: True if the frequency lists are the same, False otherwise
    """
    for i in range(0, 10):
        if fr1[i] != fr2[i]:
            return False
    return True


def complex_numbers_with_same_digits(n1, n2):
    """
    checks whether two complex numbers use the same digits
    :param n1: first complex number
    :param n2: second complex number
    :return: True if they use the same digits, False otherwise
    """
    if same_digits(digits_used(n1), digits_used(n2)):
        return True
    else:
        return False


def test_complex_numbers_with_same_digits():
    """
    test function for complex_numbers_with_same_digits()
    :return:
    """
    assert complex_numbers_with_same_digits(create_number(2, 2), create_number(1, 1)) == False
    assert complex_numbers_with_same_digits(create_number(0, 1), create_number(1, 0)) == True
    assert complex_numbers_with_same_digits(create_number(1, -1), create_number(1, 1)) == True


# test_complex_numbers_with_same_digits()

def longest_sequence_with_same_digits(number_list):
    """
    saves the longest sequence of complex numbers in a list that use the same digits
    :param number_list: list of complex numbers
    :return: the longest sequence
    """
    sequence_final = []
    sequence_current = [number_list[0]]
    for i in range(1, len(number_list)):
        if complex_numbers_with_same_digits(sequence_current[0], number_list[i]) != True and sequence_current != []:
            if len(sequence_current) > len(sequence_final):
                sequence_final = sequence_current
            sequence_current = [number_list[i]]
        else:
            sequence_current.append(number_list[i])
    if len(sequence_current) > len(sequence_final):
        sequence_final = sequence_current
    return sequence_final


# test_calculate_modulus()


def equal_modulus(n1, n2):
    """
    function checks whether two numbers have the same modulus
    :param n1: first number
    :param n2: second number
    :return: True for same modulus, False otherwise
    """
    if calculate_modulus(n1) == calculate_modulus(n2):
        return True
    else:
        return False


def longest_equal_modulus_sequence(numbers_list):
    """
    saves the longest equal modulus sequence
    :param numbers_list: list of complex numbers
    :return: longest sequence
    """
    sequence_final = []
    sequence_current = [numbers_list[0]]
    for i in range(1, len(numbers_list)):
        if equal_modulus(sequence_current[0], numbers_list[i]) != True and sequence_current != []:
            if len(sequence_current) > len(sequence_final):
                sequence_final = sequence_current
            sequence_current = [numbers_list[i]]
        else:
            sequence_current.append(numbers_list[i])
    if len(sequence_current) > len(sequence_final):
        sequence_final = sequence_current
    return sequence_final


def format_complex_number_to_string(number):
    """
    formats a complex number for it to be easier to print
    :param number:
    :return: string to print
    """
    a = get_real(number)
    b = get_imag(number)
    if b < 0:
        string = "z = " + str(a) + str(b) + "i"
    else:
        string = "z = " + str(a) + "+" + str(b) + "i"
    return string


"""UI FUNCTIONS"""


def show_all_numbers(numbers):
    """
    prints all numbers in list
    :param numbers:
    :return:
    """
    for number in numbers:
        print(format_complex_number_to_string(number))


def add_numbers_UI(numbers_list):
    """
    adds numbers to the list, if it isn't possible, the error will be shown in the console
    :param numbers_list: the list of complex numbers
    :return:
    """
    try:
        a = int(input("Enter the real part: "))
        b = int(input("Enter the imaginary part:"))
        number = create_number(a, b)
        add_number(numbers_list, number)
        print("Complex number added successfully.")
    except ValueError as ve:
        print(str(ve))
        return


def print_sequence(sequence):
    """
    prints a sequence
    :param sequence:
    :return:
    """
    for i in range(0, len(sequence)):
        print(format_complex_number_to_string(sequence[i]))


def test_print_sequence():
    """
    function to test print_sequence()
    :return:
    """
    sequence = [(0, 1), (3, 2), (3, 5)]
    print_sequence(sequence)


def longest_equal_modulus_sequence_UI(numbers_list):
    print("Longest sequence with equal modulus:")
    sequence = longest_equal_modulus_sequence(numbers_list)
    print_sequence(sequence)


def longest_sequence_with_same_digits_UI(number_list):
    print("Longest sequence with the same digits:")
    sequence = longest_sequence_with_same_digits(number_list)
    print_sequence(sequence)


def print_menu():
    """
    prints the menu
    :return:
    """
    print("1. Add complex number")
    print("2. Show all numbers.")
    print("3. Longest sequence of numbers.")
    print("4. Exit")


def start():
    number_list = init_numbers()

    while True:
        print_menu()
        option = input("User option: ")
        if option == '1':
            add_numbers_UI(number_list)
        elif option == '2':
            show_all_numbers(number_list)
        elif option == '3':
            longest_equal_modulus_sequence_UI(number_list)
            longest_sequence_with_same_digits_UI(number_list)
        elif option == '4':
            return
        else:
            print("Option does not exist.")

start()
