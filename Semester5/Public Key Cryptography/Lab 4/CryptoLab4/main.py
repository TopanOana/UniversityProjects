import math
import sympy
import random


def get_numerical_equivalent(word, size_of_block):
    """
    A function that returns a list of numbers that are the numerical equivalents of
    a block of letters by calculating 27^(size_of_block-1)*index of character + 27^(size_of_block-2) * index of charater + etc
    block = ABC
    numerical equivalent = 27^2 * 1 + 27^1 * 2 + 27^0 * 3
    :param word: string of letters
    :param size_of_block: size of the block of letters
    :return:list of numerical equivalents for the blocks of letters
    """
    characters = ' abcdefghijklmnopqrstuvwxyz'
    char_dict = {char: idx for idx, char in enumerate(characters)}

    numerical_equivalent = []
    for i in range(0, len(word), size_of_block):
        block = word[i:i + size_of_block]
        equivalent = sum(char_dict[char] * 27 ** (size_of_block - a - 1) for a, char in enumerate(block))
        numerical_equivalent.append(equivalent)

    return numerical_equivalent


def get_equivalent_letters(numerical_message, size_of_block):
    """
    Function that uses the numerical message to transform it into an equivalent string
    of letters that correspond to the number.
    The characters are selected by taking each size of the block
    :param numerical_message: number that will be transformed
    :param size_of_block: size of the block of letters that will be transformed
    :return: string of letters representing a block
    """
    characters = ' abcdefghijklmnopqrstuvwxyz'

    letters = ""
    size_of_block -= 1

    while numerical_message > 0 or size_of_block >= 0:
        letters += characters[(numerical_message // 27 ** size_of_block) % 27]
        numerical_message = numerical_message % 27 ** size_of_block
        size_of_block -= 1

    return letters


def pollard_p_1(number, B=13, a=2):
    """
    Function which is used to compute the nontrivial factor of the number by using the Pollard's p-1 algorithm.
    :param number: odd composite number
    :param B: bound
    :param a: base
    :return: Nontrivial factor of the number
    """
    k_list = []
    # q^i <= B
    for i in range(1, B + 1):
        k_list.append(i)

    k = sympy.lcm_list(k_list)
    a = a ** k % number
    gcd = math.gcd(a - 1, number)

    return gcd


def get_nontrivial_factor(number):
    """
    Function used to compute the nontrivial factor by using the Pollard's p-1 algorithm.
    :param number: odd composite number
    :return: nontrivial factors of a number
    """
    factor = pollard_p_1(number)
    return (factor, number // factor)


def generate(k, l):
    """
    Function that randomly generates the private and public key
    :param k: the block size for plaintext
    :param l: the block size for ciphertext
                where k < l
    :return: a tuple that represents (p,q) the private key
    and the final element is n which is the public key
    """
    while True:
        aux = random.randrange(27 ** k, 27 ** l, 2)
        p, q = get_nontrivial_factor(aux)
        if sympy.isprime(p) and sympy.isprime(q) and 0 < abs(p - q) < 50:
            break

    return (p, q, p * q)


def encrypt(message, key, k, l):
    """
    Function that encrypts a message using the function (numerical equivalent value)^2 % key
    then uses the final values to transform them into blocks of letters according to the l value
    :param message: string to be encrypted
    :param key: the public key used for encryption
    :param k: the encryption block size
    :param l: the decryption block size
    :return: the encrypted message (ciphertext) and the list of numerical equivalents
    """
    numerical_equivalent = get_numerical_equivalent(message, k)

    final_list = []
    for i in numerical_equivalent:
        final_list.append(i ** 2 % key)

    block_of_letters = []
    for i in final_list:
        block_of_letters.append(get_equivalent_letters(i, l))

    ciphertext = ''.join(block_of_letters)
    return ciphertext, final_list


def get_solutions(result, modulus):
    """
    Get the solution of x^2 = result mod modulus if possible
    :param result: integer representing the result of the modulus operation
    :param modulus: integer representing the modulus
    :return:
    """
    for i in range(modulus):
        if i * i % modulus == result:
            return i, -i

    return None, None


def modular_inverse(a, b):
    """
    Function that calculates the modular inverse of a number a^(-1) = 1
    :param a: number to be inverted
    :param b: modulo number
    :return:
    """
    b0 = b
    x0, x1 = 0, 1

    if b == 1:
        return 1

    while a > 1:
        q = a // b
        a, b = b, a % b
        x0, x1 = x1 - q * x0, x0

    if x1 < 0:
        x1 += b0

    return x1


def decrypt(block_of_letters, cipher_message, p, q, k, l):
    """
    Function that decrypts the ciphertext and checks which is the correct value to take from the
    decrypted values x1,x2,x3,x4
    by using the Rabin method:
        use the private key (p,q) to determine the 4 square roots x1,x2,x3,x4
        decide which one of the 4 messages x1,x2,x3,x4 was used
    :param block_of_letters: final list from the encrypt function
    :param cipher_message: string
    :param p: part of private key
    :param q: part of private key
    :param k: plaintext block size
    :param l: ciphertext blocksize
    :return: string of plaintext value
    """
    plain_text = ""
    N = p * q
    numerical_message = get_numerical_equivalent(cipher_message, l)

    for i in range(0, len(numerical_message)):
        a1, a2 = get_solutions(numerical_message[i] % p, p)  # gets the solutions to x^2 = numerical_message[i] % p
        a3, a4 = get_solutions(numerical_message[i] % q, q)  # gets the solutions to x^2 = numerical_message[i] % q

        k2 = modular_inverse(p, q)  # gets the modular inverse of p in modulo q
        k1 = modular_inverse(q, p)  # gets the modular inverse of q in modulo p

        # 4 solutions are calculated using the following system
        # (a * q * k1 + b * p * k2 ) % N

        x1 = (a1 * q * k1 + a3 * p * k2) % N
        x2 = (a1 * q * k1 + a4 * p * k2) % N
        x3 = (a2 * q * k1 + a3 * p * k2) % N
        x4 = (a2 * q * k1 + a4 * p * k2) % N

        # decides which of the values should be used
        for x in [x1, x2, x3, x4]:
            aux = get_equivalent_letters(x, k)
            if aux == block_of_letters[i]:
                plain_text += aux
                break  # Break the loop if a match is found

    return plain_text


def validator(plaintext):
    """
    Check if every character in the plaintext is a letter from the alphabet
    :param plaintext: string which needs to be validated
    :return: true, if the plaintext contains only English alphabet letters, false otherwise
    """
    characters = [' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                  't', 'u', 'v', 'w', 'x', 'y', 'z']
    plaintext_as_list = list(plaintext)
    for character in plaintext_as_list:
        if character not in characters:
            return False
    return True


if __name__ == "__main__":
    print("Rabin Cryptosystem\n")

    while True:
        try:
            k = int(input("k = ").strip())
            l = int(input("l = ").strip())
            if l <= k:
                raise ValueError("ERROR! l cannot be less or equal to k!")
            else:
                break
        except ValueError as e:
            print(e)
            print("Try again!")

    p, q, key = generate(k, l)
    print("Public Key = ", key)
    print(f"Private key (p, q): ({p}, {q})")

    while True:
        try:
            message = input("Input encryption message: ")
            if validator(message) is False:
                raise ValueError("ERROR! Invalid message! The message cannot contain numbers or special characters! "
                                 "Use lowercase letters!")
            else:
                break
        except ValueError as e:
            print(e)
            print("Try again!")

    print("Encryption process has begun!")
    ciphertext, encrypted_values = encrypt(message, key, k, l)
    print("Encryption values:", encrypted_values)
    print("Ciphertext:", ciphertext)

    if validator(ciphertext):
        print("Decryption process has begun!")
        message_as_list = [message[i:i + k] for i in range(0, len(message), k)]
        print("Decrypted message:", decrypt(message_as_list, ciphertext, p, q, k, l))
    else:
        print("Invalid ciphertext!")

    print("\nGoodbye! ")
