# Solve the problem from the third set here
""""
15.
Generate the largest perfect number smaller than a
given natural number n. If such a number does not exist,
a message should be displayed. A number is perfect if it
is equal to the sum of its divisors, except itself.
(e.g. 6 is a perfect number, as 6=1+2+3).
"""

def perfect_number(number):
    if number == 1:
        return 0
    sum_of_divisors=1
    d=2
    while d<=number/2:
        if(number%d==0):
            sum_of_divisors=sum_of_divisors+d
        d=d+1
    if sum_of_divisors==number:
        result=1
    else: result =0
    return result

def largest_perfect_number(number):
    result =0
    number=number-1
    while (result==0 and number>0):
        if(perfect_number(number)==1):
            result=number
        else: number=number-1
    return result

def start():
    n = int(input("Choose a number: "))
    result=largest_perfect_number(n)
    if(result==0):
        print("such a number does not exist.")
    else: print(result)

start()