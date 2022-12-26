//
// Created by Oana on 2/22/2022.
//
#include <stdio.h>



/*
 * int gcd(a,b)
 * It calculates the greatest common divisor of the two numbers a,b
 * input:
 * a - a natural number
 * b - a natural number
 * output:
 * the greatest common divisor of (a,b)
 */
int gcd(int a, int b)
{
    while (a!=b)
        if(a>b)
            a-=b;
        else b-=a;
    return a;
}



/*
 * int prime(a)
 * It checks whether a function is prime or not
 * input:
 * a - a natural number
 * output:
 * 0 - not prime
 * 1 - prime
 */
int prime(int a)
{
    if (a<2)
        return 0;
    for (int i=2;i*i<=a;i++)
        if(a%i==0)
            return 0;
    return 1;
}

/*
 * compare_with_smaller(x)
 * checks whether the number x satisfies all the conditions for xi
 * input:
 * x - number to check
 * output:
 * returns 1 if the number x satisfies all the conditions
 * 0 otherwise.
 */
int compare_with_smaller(int x)
{
    for (int i=x-1;i>=2;i--)
    {
        int gcd_check = gcd(x, i);
        int prime_check = prime(i);
        if (gcd_check !=1 && prime_check==1)
            return 0;
    }
    return 1;
}


/*
 * implement_a(n, v)
 * it does all of functionality 1
 */
void implement_a(int* n, int v[]) {
    *n = 8;
    for (int i = 0; i < *n; i++)
    {
        int j=3;
        if (i>0)
            j=v[i-1]+1;
        while(compare_with_smaller(j)==0)
            j++;
        v[i]=j;
    }
    for(int i=0;i<8;i++)
        printf("%d ", v[i]);
}


/*
 * int same_digits(a,b)
 * Checks whether two numbers use the same digits.
 * input:
 * a - a natural number
 * b - a natural number
 * output:
 * 0 - if they don't use the same numbers
 * 1 - if they use the same numbers
 */
int same_digits(int a, int b)
{
    int fr1[10]={0};
    int fr2[10]={0};
    while(a>0)
    {
        fr1[a%10]=1;
        a/=10;
    }
    while(b>0)
    {
        fr2[b%10]=1;
        b/=10;
    }
    for (int i=0;i<10;i++)
        if (fr1[i]!=fr2[i])
            return 0;
    return 1;
}


/*
 * void longest_subsequence(v,n,left,right)
 * it sends the longest subsequence with the property that consecutive elements contain the same digits
 * input:
 * v - an array of numbers
 * n - the dimension of the array
 * left - the start point of the possible subsequence
 * right - the end point of the possible subsequence
 * output:
 * the values of left and right will be modified
 */
int longest_subsequence(int v[], int n, int* start)
{
    int local_length=1;
    int maximum_length=-1;
    int start_point_maximum=-1;
    int start_point_local=0;
    for (int i=0;i<n;i++)
    {
        int a = same_digits(v[i], v[i+1]);
        if (a==1)
        {
            local_length++;
        }
        else {
            if (local_length>maximum_length)
            {
                maximum_length=local_length;
                start_point_maximum=start_point_local;
            }
            start_point_local=i+1;
            local_length=1;
        }
    }
    if (local_length>maximum_length)
    {
        maximum_length=local_length;
        start_point_maximum=start_point_local;
    }
    *start = start_point_maximum;
    return maximum_length;
}

/*
 * read_numbers(v)
 * reads the numbers from the console until the number 0
 * saves them in the vector v and the number of numbers in n
 * returns the dimension of the vector
 */
int read_numbers(int v[])
{
    int n=0;
    int x;
    scanf("%d", &x);
    while(x!=0)
    {
        v[n]=x;
        n++;
        scanf("%d", &x);
    }
    return n;
}

/*
 * print_subsequence(v,left,right)
 * prints a subsequence of an array from the starting point left, to the ending point right
 */
void print_subsequence(int v[], int left, int right)
{
    for (int i=left;i<=right;i++)
        printf("%d ", v[i]);
}

/*
 * implement_b(n,v)
 * the implementation of functionality b
 */
void implement_b(int n, int v[])
{
    int start =0;
    int a = longest_subsequence(v, n, &start);
    printf("the longest subsequence: ");
    print_subsequence(v, start, start+a-1);
}


/*
 * reads all the numbers from a vector from the console.
 */
void implement_c(int* n, int v[])
{
    *n = read_numbers(v);
    ///printf("%d", *n);
    print_subsequence(v,0,n);
}


/*
 * prints a menu
 */
void menu()
{
    printf("\n");
    printf("A1: Problem 13\n");
    printf("1: first functionality\n");
    printf("2: second functionality\n");
    printf("3: read a vector of numbers\n");
    printf("4: exit the program\n");
}



int main()
{
    int continue_program=1;
    int n=0, v[20]={0};
    while (continue_program==1)
    {
        menu();
        int value;
        printf("Choose an option: \n");
        scanf("%d", &value);
        if (value == 1)
            implement_a(&n, v);
        else
            if(value == 2)
                implement_b(n, v);
            else
                if(value == 3)
                    implement_c(&n,v);
                else
                    if(value == 4)
                        continue_program=0;
                    else
                        printf("unavailable option\n");

    }
    return 0;
}

