#include <iostream>
#include <sys/time.h>
#include <vector>
#include <thread>
#include <future>
#include <valarray>

using namespace std;

int first_polynomial[1000];
int first_degree = 100;
int second_polynomial[1000];
int second_degree = 100;
int result_polynomial[2000];
int result_degree = 201;

vector<int> first_poly;
vector<int> second_poly;

int nrThreads = 5;

void initializePolynomialArrays() {
//    first_degree = 10;
    for (int i = 0; i <= first_degree; i++) {
        first_polynomial[i] = 1;
    }

//    second_degree = 10;
    for (int i = 0; i <= second_degree; i++) {
        second_polynomial[i] = 1;
    }

//    result_degree = first_degree + second_degree + 1;
    for (int i = 0; i <= result_degree; i++) {
        result_polynomial[i] = 0;
    }
}

void printResult() {
    for (int i = 0; i < result_degree; i++) {
        printf("%dX%d ", result_polynomial[i], i);
    }
    printf("\n");
}


void regularSequentialAlgorithm() {
    initializePolynomialArrays();

    struct timeval start, end;

    gettimeofday(&start, nullptr);

    result_degree = first_degree + second_degree + 1;
    for (int i = 0; i <= first_degree; i++) {
        for (int j = 0; j <= second_degree; j++) {
            int newIndex = i + j;
            int value = first_polynomial[i] * second_polynomial[j];
            result_polynomial[newIndex] += value;
        }
    }

    gettimeofday(&end, nullptr);
    printf("regular sequential time = %u\n", end.tv_usec - start.tv_usec);

    printResult();

}


void *multiplication(void *args) {
    int id = *(int *) args;

    for (int index = id; index < result_degree; index += nrThreads) {
        for (int j = 0; j <= index; j++) {
            if (j <= first_degree && (index - j) <= second_degree) {

                int value = first_polynomial[j] * second_polynomial[index - j];
//                std::cout << "id " << id << " index " << index << " - j " << j << "result " << value << endl;
                result_polynomial[index] += value;
            }
        }
    }

    return nullptr;
}

void regularParallelizedAlgorithm() {
    initializePolynomialArrays();
    struct timeval start, end;

    gettimeofday(&start, nullptr);
    chrono::high_resolution_clock::time_point const startTimeRegularAlg = chrono::high_resolution_clock::now();
    result_degree = first_degree + second_degree + 1;

    pthread_t *threads = (pthread_t *) malloc(nrThreads * sizeof(pthread_t));
    int *startingPoints = (int *) malloc(nrThreads * sizeof(int));
    for (int i = 0; i < nrThreads; i++) {
        startingPoints[i] = i;
        pthread_create(&threads[i], nullptr, multiplication, (void *) (&startingPoints[i]));
    }


    for (int i = 0; i < nrThreads; i++)
        pthread_join(threads[i], nullptr);

    gettimeofday(&end, nullptr);
    chrono::high_resolution_clock::time_point const endTimeRegularAlg = chrono::high_resolution_clock::now();
    printf("regular parallelized time = %u\n", chrono::duration_cast<chrono::microseconds>(endTimeRegularAlg - startTimeRegularAlg).count());

    printResult();

    free(threads);
    free(startingPoints);

}

void initializePolynomialVectors() {
    first_degree = 100;
    second_degree = 100;

    first_poly.clear();
    for (int i = 0; i <= first_degree; i++) {
        first_poly.push_back(1);
    }

    second_poly.clear();
    for (int i = 0; i <= second_degree; i++) {
        second_poly.push_back(1);
    }
}


// Function to print a polynomial
void printPolynomial(const vector<int> &poly) {
    int degree = poly.size() - 1;
    for (int i = degree; i >= 0; --i) {
        cout << poly[i] << "x^" << i;
        if (i > 0) {
            cout << " + ";
        }
    }
    cout << endl;
}

vector<int> sum(const vector<int> &A, const vector<int> &B) {
    int size = max(A.size(), B.size());
    vector<int> result(size, 0);
    for (int i = 0; i < A.size(); ++i) {
        result[i] += A[i];
    }
    for (int i = 0; i < B.size(); ++i) {
        result[i] += B[i];
    }

    return result;
}

vector<int> subtract(const vector<int> &A, const vector<int> &B) {
    int size = max(A.size(), B.size());
    vector<int> result(size, 0);
    for (int i = 0; i < A.size(); ++i) {
        result[i] += A[i];
    }
    for (int i = 0; i < B.size(); ++i) {
        result[i] -= B[i];
    }
    return result;
}


vector<int> karatsuba(const vector<int> &A, const vector<int> &B) {
    int n = A.size();

    // Base case: if the polynomials are small, use the standard multiplication
    if (n <= 4) {
        vector<int> result(2 * n - 1, 0);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                result[i + j] += A[i] * B[j];
            }
        }
        return result;
    }

    // Split the polynomials into two halves
    int mid = n / 2;
    vector<int> A0(A.begin(), A.begin() + mid);
    vector<int> A1(A.begin() + mid, A.end());
    vector<int> B0(B.begin(), B.begin() + mid);
    vector<int> B1(B.begin() + mid, B.end());

    // Recursive steps
    vector<int> Z0 = karatsuba(A0, B0);
    vector<int> Z2 = karatsuba(A1, B1);
    vector<int> Z1mid = karatsuba(sum(A0, A1), sum(B0, B1));
    vector<int> Z1 = subtract(subtract(Z1mid, Z0), Z2);

    // Combine the results
    vector<int> result(2 * n - 1, 0);
    for (int i = 0; i < n; ++i) {
        if (i < Z0.size())
            result[i] += Z0[i];
        if (i < Z1.size())
            result[i + mid] += Z1[i];
        if (i < Z2.size())
            result[i + 2 * mid] += Z2[i];
    }

    return result;
}


void karatsubaSequentialAlgorithm() {
    initializePolynomialVectors();
    struct timeval start, end;

    gettimeofday(&start, nullptr);

    vector<int> result = karatsuba(first_poly, second_poly);


    gettimeofday(&end, nullptr);
    printf("karatsuba sequential time = %u\n", end.tv_usec - start.tv_usec);

    printPolynomial(result);

}

int checkForThread(int currentDepth) {
    if (currentDepth == 0)
        return 3;
    else
        return checkForThread(currentDepth - 1) + pow(3, currentDepth + 1);
}


vector<int> karatsubaParallelized(const vector<int> &A, const vector<int> &B, int currentDepth) {
    int n = A.size();

    if (checkForThread(currentDepth) > nrThreads)
        return karatsuba(A, B);

    // Base case: if the polynomials are small, use the standard multiplication
    if (n <= 4) {
        vector<int> result(2 * n - 1, 0);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                result[i + j] += A[i] * B[j];
            }
        }
        return result;
    }

    // Split the polynomials into two halves
    int mid = n / 2;
    vector<int> A0(A.begin(), A.begin() + mid);
    vector<int> A1(A.begin() + mid, A.end());
    vector<int> B0(B.begin(), B.begin() + mid);
    vector<int> B1(B.begin() + mid, B.end());

    // Recursive steps
    auto first_async = async(karatsubaParallelized, A0, B0, currentDepth + 1);
    auto second_async = async(karatsubaParallelized, A1, B1, currentDepth + 1);
    auto third_async = async(karatsubaParallelized, sum(A0, A1), sum(B0, B1), currentDepth + 1);
    std::vector<int> Z0 = first_async.get();
    std::vector<int> Z2 = second_async.get();
    std::vector<int> Z1mid = third_async.get();
    std::vector<int> Z1 = subtract(subtract(Z1mid, Z0), Z2);

    // Combine the results
    vector<int> result(2 * n - 1, 0);
    for (int i = 0; i < n; ++i) {
        if (i < Z0.size())
            result[i] += Z0[i];
        if (i < Z1.size())
            result[i + mid] += Z1[i];
        if (i < Z2.size())
            result[i + 2 * mid] += Z2[i];
    }

    return result;
}


void karatsubaParallelizedAlgorithm() {
    initializePolynomialVectors();
    struct timeval start, end;

    gettimeofday(&start, nullptr);

    vector<int> result = karatsubaParallelized(first_poly, second_poly, 0);


    gettimeofday(&end, nullptr);
    printf("karatsuba parallelized time = %u\n", end.tv_usec - start.tv_usec);

    printPolynomial(result);
}

int main() {

//    regularSequentialAlgorithm();

    regularParallelizedAlgorithm();

//    karatsubaSequentialAlgorithm();
//
//    karatsubaParallelizedAlgorithm();

    cout << "Hello, World!" << endl;
    return 0;
}
