#include <iostream>
#include <vector>
#include <cstring>
#include <cmath>

using namespace std;

vector<string> polynomial;

/*
 * Pollard’s ρ algorithm. The implicit function will be
 * f (x) = x2 + 1, but it will also allow the use
 * of a function f given by the user.
 */

int implicitFunction(int x) {
    return x * x + 1;
}


/*
 * gcd (a,b)
 */
int gcd(int a, int b) {
    if (a == 0)
        return b;
    return gcd(b % a, a);
}


int pollardsPAlgorithm(int n, int (*function)(int)) {
    // declaring a vector of numbers to start the algorithm
    int x[n];
    // starting point is 0, and its value is 2
    x[0] = 2;

    for (int j = 1; j < n; j++) {
        x[j] = function(x[j - 1]) % n;
//        std::cout << "x[" << j << "]= " << x[j] << "\n";
        if (j % 2 == 0) {
            int d = gcd(abs(x[j] - x[j / 2]), n);
//            std::cout << "(|x[" << j << "]-x[" << j / 2 << "]|, " << n << ") = " << d << endl;

            if (d > 1 && d < n)
                return d;
        }

    }

    return n;
}


//the function that will be called for the Pollard's Rho algorithm
//it goes through the polynomial saved by the user and computes the value
//of the function
auto lambdaFunction = [](int x) {
    int value = 0;
    int i;
    for (i = 0; i < polynomial.size() - 2; i += 4) {
        int power = stoi(polynomial[i + 3]);
        int coefficient = stoi(polynomial[i + 1]);
        if (polynomial[i] == "+") {
            value += coefficient * pow(x, power);
        } else {
            value -= coefficient * pow(x, power);
        }
    }
    return value;
};

int main() {
    std::cout << "Hello, World!" << std::endl;

    int n;
    int result;

    std::cout << "Introduce an odd composite number\nn = ";
    std::cin >> n;

    std::cout << "Do you want to introduce a function? (y/n)\n";
    char response;
    std::cin >> response;
    cin.get();
    if (response == 'y') {
        result = 0;
        char inputMap[100];
        std::cout << "Please write the polynomial map of the form: +2X2+1X1+1X0\n";
        std::cout << "Input a polynomial map f(X) = ";
        cin.getline(inputMap, 100);

        string currentEl;
        for (int i = 0; i < strlen(inputMap); i++) {
            char c = inputMap[i];
            if (c == 'X') {
                if (currentEl.empty())
                    currentEl = "1";
                polynomial.emplace_back(currentEl);
                polynomial.emplace_back("X");
                currentEl.clear();
            } else if (strchr("+-", c) != nullptr) {
                if (i != 0) {
                    if (currentEl.empty())
                        currentEl = "1";
                    polynomial.emplace_back(currentEl);
                    currentEl.clear();
                }
                currentEl.push_back(c);
                polynomial.emplace_back(currentEl);
                currentEl.clear();
            } else {
                currentEl.push_back(c);
            }
        }
        if (!currentEl.empty())
            polynomial.emplace_back(currentEl);


        result = pollardsPAlgorithm(n, lambdaFunction);

    } else {
        result = pollardsPAlgorithm(n, implicitFunction);
    }


    std::cout << "Result of Pollards p algorithm for n " << n << " = " << result << std::endl;

    return 0;
}
