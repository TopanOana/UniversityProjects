#include <iostream>
/*
 * 8. Algorithm for determining all bases b with respect to which a composite odd number n is
    pseudoprime. Use the repeated squaring modular exponentiation method.
 */


/*
 * compute the binary representation of the value k
 */
void binary_representation(int k, int binary[], int &length) {
    length = 0;
    while (k > 0) {
        binary[length] = k % 2;
        k /= 2;
        length++;
    }
}


/*
 * input : b = base, k = power, n = number
 * output = b^k % n
 */
int repeatedSquaringModularExponentiation(int b, int k, int n) {
    int binary_k[20];
    int length;
    binary_representation(k, binary_k, length); //get the binary representation of k

    int a = 1;

    if (k == 0) // if the power is 0, then the number will be 1 since any power raised to 0 is 1.
        return a;

    int c = b;
    if (binary_k[0] == 1) // if the number is 1, then we start with a = the base.
        a = b;

    //generally what happens is when k has the power of two i on the position i, then a will be computed
    // by multiplying a and c and getting the remainder.
    // c in each loop will still be computed in order to make sure it is always consistent.

    for (int i = 1; i < length; i++) {
        c = (c * c) % n;
        if (binary_k[i] == 1)
            a = (c * a) % n;
    }

    return a;
}

/*
 * a function that calculates the greatest common denominator of two numbers;
 */
int gcd(int a, int b) {
    if (a == 0)
        return b;
    if (b == 0)
        return a;

    while (a != b) {
        if (a > b)
            a -= b;
        else
            b -= a;
    }

    return a;
}

/*
 * a number is pseudoprime if :
 *          gcd(b,n)=1
 * and       b^(n-1) = 1 (mod n)
 */
bool isPseudoprime(int n, int b) {
    if (gcd(n, b) == 1 && repeatedSquaringModularExponentiation(b, n - 1, n) == 1)
        return true;
    return false;
}


int main() {
    std::cout << "Hello, World!" << std::endl;

//    int n = 101;
//    printf("pseudoprime bases to number %d:\n", n);
//    for (int i=2;i<n;i++){
//        if (isPseudoprime(n,i))
//            printf("%d,", i);
//    }
//

    std::cout << gcd((59-1)*(97-1), 5) << std::endl;

    std::cout << repeatedSquaringModularExponentiation(1505, 3341, 5723) << std::endl;
    std::cout << repeatedSquaringModularExponentiation(958, 3341, 5723) << std::endl;
    std::cout << repeatedSquaringModularExponentiation(267, 3341, 5723) << std::endl;

//    std::cout << repeatedSquaringModularExponentiation(41, 3, 2491) << std::endl;
//    std::cout << repeatedSquaringModularExponentiation(298, 3, 2491) << std::endl;
//    std::cout << repeatedSquaringModularExponentiation(487, 3, 2491) << std::endl;




    return 0;
}
