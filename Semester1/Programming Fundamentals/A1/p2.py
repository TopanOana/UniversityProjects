# Solve the problem from the second set here
""""
11. The numbers n1 and n2 have the property P if their writing
in base 10 uses the same digits (e.g. 2113 and 323121).
Determine whether two given natural numbers have property P.
"""

def list_of_digits(number):
    digits = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    while (number > 0):
        digits[number % 10] = digits[number % 10] + 1
        number = int(number / 10)
    return digits

def same_digits_in_b10(n1,n2):
    d1=list_of_digits(n1)
    d2=list_of_digits(n2)
    result = 1
    for i in range(0,10):
        if ((d1[i]!=0 and d2[i]==0) or (d1[i]==0 and d2[i]!=0)):
            result=0
    return result

def start():
    n = int(input("Enter a number: "))
    m = int(input("Enter a number: "))
    print("Verifying the property...")
    if(same_digits_in_b10(n,m)==1):
        print("The two numbers use the same digits.")
    else: print("The numbers don't use the same digits")

start()