#include <stdio.h>
#include <sys/time.h>


/*Dijkstra's algorithm ///
The idea of the algorithm is
    you keep subtracting the smaller number from the greater one until the numbers are equal
    when the numbers are equal, they constitute the greatest common denominator
*/
long long dijkstraAlgorithm(long long a, long long b){
    if (a == 0)
        return b;
    else if (b==0)
        return a;
    while (a!=b){ //checking whether the numbers are equal
        if (a > b) //checking which number is greater
            a -= b; //subtracting the smaller number from the greater one.
        else {
            b -= a;
        }
    }

    return a; //returning one of the numbers since they are equal and GCD
}


/*
 The basic Euclidean Algorithm
 The idea of the algorithm is similar to the previous one, but instead of subtracting,
 we use the remainder of the division between the first element and the last one.

*/
long long euclideanAlgorithm(long long a, long long b){
    long long division;
    while (b!=0){ //checking whether the remainder is 0
        division = a % b; //computing the remainder
        a = b; //putting the previous value of b in a
        b = division; //putting the remainder of the division in b
    }

    return a;
}

/*
 * Stein's algorithm || Binary GCD Algorithm
 * It computes the GCD by using arithmetic shifts, comparisons and subtraction
 *
 */
long long steinsAlgorithm(long long a, long long b){
    //First we check whether a and b are 0, if so then the other value is the gcd
    if (a == 0)
        return b;
    if (b == 0)
        return a;

    //now we calculate how many times a and b are divided by 2
    //so that we know how many times to multiply the gcd of the final values by 2
    int k=0;
    while (((a|b) & 1)==0){
        k++;
        a >>= 1;
        b >>= 1;
    }

    //now we divide a until it is odd in order to continue with the algorithm (this means that 2 is no longer
    // a factor of the gcd)
    while ( (a & 1) == 0){
        a >>= 1;
    }

    //from now on a is odd
    do{
        //check if b is even in order to remove all factors of 2 in b
        while ((b & 1) == 0){
            b >>= 1;
        }

        //now that both a and b are odd
        //we swap the values such that a <= b
        if (a > b){
            long long c = a;
            a = b;
            b = c;
        }

        //we use this as the continuation of the algorithm so that the gcd new pair check is
        // |a-b| and b
        b = b - a;
    }while (b != 0);

    return a << k; //multiplying the common factors of 2
}



int main() {
//    printf("Hello, World!\n");

    struct timeval startDijkstra, endDijkstra;
    struct timeval startEuclidean, endEuclidean;
    struct timeval startStein, endStein;

    long long testValuesA[10] = {36, 10, 33554431, 2005, 100000000002, 4000, 509, 10000002, 414507281407, 0};
    long long testValuesB[10] = {60, 35, 16777215, 305 , 200046564, 399509, 2070, 2, 53982894593057, 61};

    long long gcdDijkstra, gcdEuclidean, gcdStein;

    for (int i = 0; i < 10; i++){
        gettimeofday(&startDijkstra, NULL);
        gcdDijkstra = dijkstraAlgorithm(testValuesA[i], testValuesB[i]);
        gettimeofday(&endDijkstra, NULL);

        gettimeofday(&startEuclidean, NULL);
        gcdEuclidean = euclideanAlgorithm(testValuesA[i], testValuesB[i]);
        gettimeofday(&endEuclidean, NULL);

        gettimeofday(&startStein, NULL);
        gcdStein = steinsAlgorithm(testValuesA[i], testValuesB[i]);
        gettimeofday(&endStein, NULL);

        printf("\nValues: %lld and %lld\n", testValuesA[i], testValuesB[i]);

        printf("Dijkstra : gcd = %lld in time %f microseconds\n", gcdDijkstra, ((endDijkstra.tv_sec - startDijkstra.tv_sec) * 1000000 + (double)(endDijkstra.tv_usec - startDijkstra.tv_usec) ));
        printf("Euclidean : gcd = %lld in time %f microseconds\n", gcdEuclidean, ((endEuclidean.tv_sec - startEuclidean.tv_sec) * 1000000 + (double)(endEuclidean.tv_usec - startEuclidean.tv_usec) ));
        printf("Stein's : gcd = %lld in time %f microseconds\n", gcdStein, ((endStein.tv_sec - startStein.tv_sec) * 1000000 + (double)(endStein.tv_usec - startStein.tv_usec) ));

    }

    return 0;
}
