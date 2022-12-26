# Solve the problem from the first set here
# 1. Generate the first prime number larger than a given natural number n.

"""""
function that checks whether a number is a prime number or not.
returns 1 for a prime number
returns 0 for a non-prime number
"""""
def prime_number(number):
    result =1
    if(number <2):
        result = 0
    d=2
    while (d*d <= number and result==1):
        if (number % d == 0):
            result = 0
            d=number
        else: d=d+1
    return result

def first_prime_larger(number):
    prime=number+1
    while (prime_number((prime))==0):
        prime=prime+1
    return prime

"""
3. For a given natural number n find the minimal natural number m 
formed with the same digits. (e.g. n=3658, m=3568).
"""

def list_of_digits(number):
    digits = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    while (number > 0):
        digits[number % 10] = digits[number % 10] + 1
        number = int(number / 10)
    return digits

def smallest_number(number):
    digits = list_of_digits(number)
    new_number = 0
    for i in range(0,10):
        how_many=digits[i]
        while(how_many>0):
            new_number=new_number*10+i
            how_many=how_many-1
    return new_number

def start():
    m = int(input("Enter a number: "))
    print(str(first_prime_larger((m)))+" is the first prime number larger than the one that was introduced")
    n = int(input("Enter a number: "))
    print(str(smallest_number(n))+" is the minimal number formed with the same digits")



start()


