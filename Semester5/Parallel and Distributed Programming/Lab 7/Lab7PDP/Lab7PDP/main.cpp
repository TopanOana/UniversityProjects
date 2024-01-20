#include <mpi.h>
#include <stdio.h>
#include <iostream>
#include <chrono>
#include <vector>

using namespace std;

int first_polynomial[20000];
int first_degree = 100;
int second_polynomial[20000];
int second_degree = 100;
int result_polynomial[20000];
int result_degree;

void initializePolynomialArrays() {
	//first_degree = 100;
	for (int i = 0; i <= first_degree; i++) {
		first_polynomial[i] = 1;
	}

	//second_degree = 100;
	for (int i = 0; i <= second_degree; i++) {
		second_polynomial[i] = 1;
	}

	result_degree = first_degree + second_degree + 1;
	for (int i = 0; i <= result_degree; i++) {
		result_polynomial[i] = 0;
	}
}

//worker process
void multiplicationRegularAlg(int id, int nrProcesses) {

	for (int index = id; index < result_degree; index += nrProcesses) {
		int currentVal = 0;
		for (int j = 0; j <= index; j++) {
			if (j <= first_degree && (index - j) <= second_degree) {
				int value = first_polynomial[j] * second_polynomial[index - j];
				//result_polynomial[index] += value;
				currentVal += value;
				//MPI_Ssend(&value, 1, MPI_INT, 0, index * j, MPI_COMM_WORLD);
				//send the value here
			}
		}
		//cout << "process " << id << " sending value " << currentVal << " for position " << index << endl;
		MPI_Ssend(&currentVal, 1, MPI_INT, 0, index, MPI_COMM_WORLD);
	}
}

//main process
void mainProcessRegularAlg(int nrProcesses) {
	MPI_Status status;

	//first computes its part
	for (int i = 0; i < result_degree; i += nrProcesses) {
		for (int j = 0; j <= i; j++) {
			if (j <= first_degree && (i - j) <= second_degree) {
				int value = first_polynomial[j] * second_polynomial[i - j];
				result_polynomial[i] += value;
			}
		}
	}

	//cout << "main computed its result!" << endl;

	//now it receives the values from the workers

	for (int i = 0; i < result_degree; i++) {
		int process = i % nrProcesses;
		if (process > 0) {

			int current;
			//cout << "before recv" << endl;
			MPI_Recv(&current, 1, MPI_INT, process, i, MPI_COMM_WORLD, &status);
			result_polynomial[i] = current;
		}
		/*printf(" %dX%d ", result_polynomial[i], i);
		if (i != result_degree - 1)
			printf("+");*/
	}

}

vector<int> sum(const vector<int>& A, const vector<int>& B) {
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

vector<int> subtract(const vector<int>& A, const vector<int>& B) {
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

vector<int> karatsuba(const vector<int>& A, const vector<int>& B, int nrProcesses, int me) {
	int n = A.size();

	if (n <= 4) {
		vector<int> result(2 * n - 1, 0);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
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

	if (nrProcesses >= 2) {
		MPI_Status status;

		int child1 = me + nrProcesses / 2;
		int newNrProcesses = nrProcesses - nrProcesses / 2;

		//send newNrProcesses to child1
		MPI_Ssend(&newNrProcesses, 1, MPI_INT, child1, 1, MPI_COMM_WORLD);

		//first send vector A0 to child1
		//send size of vector then all the numbers
		int size_A0 = A0.size();
		MPI_Ssend(&size_A0, 1, MPI_INT, child1, 2, MPI_COMM_WORLD);
		//now send the vector
		for (int i = 0; i < A0.size(); i++) {
			int value = A0[i];
			MPI_Ssend(&value, 1, MPI_INT, child1, 2 + i, MPI_COMM_WORLD);
		}

		//now send vector B0 to child1
		int size_B0 = B0.size();
		MPI_Ssend(&size_B0, 1, MPI_INT, child1, 2, MPI_COMM_WORLD);
		//now send the vector
		for (int i = 0; i < B0.size(); i++) {
			int value = B0[i];
			MPI_Ssend(&value, 1, MPI_INT, child1, 2 + i, MPI_COMM_WORLD);
		}


		////send newNrProcesses to child2
		//MPI_Ssend(&newNrProcesses, 1, MPI_INT, child2, 1, MPI_COMM_WORLD);

		////first send vector A1 to child2
		////send size of vector then all the numbers
		//int size_A1 = A1.size();
		//MPI_Ssend(&size_A1, 1, MPI_INT, child2, 2, MPI_COMM_WORLD);
		////now send the vector
		//for (int i = 0; i < A1.size(); i++) {
		//	int value = A1[i];
		//	MPI_Ssend(&value, 1, MPI_INT, child2, 2 + i, MPI_COMM_WORLD);
		//}

		////now send vector B1 to child2
		//int size_B1 = B1.size();
		//MPI_Ssend(&size_B1, 1, MPI_INT, child2, me, MPI_COMM_WORLD);
		////now send the vector
		//for (int i = 0; i < B1.size(); i++) {
		//	int value = B1[i];
		//	MPI_Ssend(&value, 1, MPI_INT, child2, 2 + i, MPI_COMM_WORLD);
		//}

		vector<int> Z2 = karatsuba(A1, B1, nrProcesses / 2, me);

		vector<int> Z1mid = karatsuba(sum(A0, A1), sum(B0, B1), 1, me);

		//receive size of vector Z0
		int size_Z0;
		MPI_Recv(&size_Z0, 1, MPI_INT, child1, child1, MPI_COMM_WORLD, &status);

		//receive vector Z0
		vector<int> Z0(size_Z0);

		for (int i = 0; i < size_Z0; i++) {
			int val;
			MPI_Recv(&val, 1, MPI_INT, child1, child1, MPI_COMM_WORLD, &status);
			Z0[i] = val;
		}

		////receive size of vector Z2
		//int size_Z2;
		//MPI_Recv(&size_Z2, 1, MPI_INT, child2, child2, MPI_COMM_WORLD, &status);

		////receive vector Z2
		//vector<int> Z2(size_Z2);

		//for (int i = 0; i < size_Z2; i++) {
		//	int val;
		//	MPI_Recv(&val, 1, MPI_INT, child2, child2, MPI_COMM_WORLD, &status);
		//	Z2[i] = val;
		//}

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
	else {
		//no more delegation
		// Recursive steps
		vector<int> Z0 = karatsuba(A0, B0, nrProcesses, me);
		vector<int> Z2 = karatsuba(A1, B1, nrProcesses, me);
		vector<int> Z1mid = karatsuba(sum(A0, A1), sum(B0, B1), nrProcesses, me);
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
}


void mainProcessKaratsubaAlg(const vector<int>& A, const vector<int>& B, int nrProcesses) {
	vector<int> result = karatsuba(A, B, nrProcesses, 0);

	for (int i = 0; i < result.size(); i++) {
		printf(" %dX%d ", result[i], i);
		if (i != result.size() - 1)
			printf("+");
	}
}


void karatsubaWorker(int me) {
	MPI_Status status;
	//receive the nr processes
	int nrProcesses;
	int parent;

	MPI_Recv(&nrProcesses, 1, MPI_INT, MPI_ANY_SOURCE, 1, MPI_COMM_WORLD, &status);
	parent = status.MPI_SOURCE;
	//receive size of vector A
	int sizeA;
	MPI_Recv(&sizeA, 1, MPI_INT, parent, 2, MPI_COMM_WORLD, &status);

	//receive vector A
	vector<int> A(sizeA);
	for (int i = 0; i < sizeA; i++) {
		int val;
		MPI_Recv(&val, 1, MPI_INT, parent, 2 + i, MPI_COMM_WORLD, &status);
		A[i] = val;
	}

	//receive size of vector B
	int sizeB;
	MPI_Recv(&sizeB, 1, MPI_INT, parent, 2, MPI_COMM_WORLD, &status);

	//receive vector A
	vector<int> B(sizeB);
	for (int i = 0; i < sizeB; i++) {
		int val;
		MPI_Recv(&val, 1, MPI_INT, parent, 2 + i, MPI_COMM_WORLD, &status);
		B[i] = val;
	}

	vector<int> result = karatsuba(A, B, nrProcesses, me);

	//now send vector B1 to child2
	int size_result = result.size();
	MPI_Ssend(&size_result, 1, MPI_INT, parent, me, MPI_COMM_WORLD);
	//now send the vector
	for (int i = 0; i < result.size(); i++) {
		int value = result[i];
		MPI_Ssend(&value, 1, MPI_INT, parent, me, MPI_COMM_WORLD);
	}


}


int main(int argc, char** argv) {
	// Initialize the MPI environment
	MPI_Init(NULL, NULL);
	// Get the rank of the process
	int my_rank;
	int nrProcesses;
	MPI_Comm_size(MPI_COMM_WORLD, &nrProcesses);
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

	//initialize the polynomial arrays for the regular algorithm
	initializePolynomialArrays();
	//cout << my_rank << " process!" << endl;

	if (my_rank == 0) {
		//this is the master process
		chrono::high_resolution_clock::time_point const startTimeRegularAlg = chrono::high_resolution_clock::now();
		mainProcessRegularAlg(nrProcesses);
		chrono::high_resolution_clock::time_point const endTimeRegularAlg = chrono::high_resolution_clock::now();
		printf("\nregular time : %ld microseconds\n", chrono::duration_cast<chrono::microseconds>(endTimeRegularAlg - startTimeRegularAlg).count());
	}
	else {
		//this is the worker process
		multiplicationRegularAlg(my_rank, nrProcesses);
	}


	//initialize the polynomial vectors for karatsuba
	first_degree = 100;
	second_degree = 100;

	int max_rank = log2(first_degree + 1) - 1;

	nrProcesses = min(max_rank + 1, nrProcesses);

	if (my_rank == 0) {


		vector<int> first_poly;
		for (int i = 0; i <= first_degree; i++) {
			first_poly.push_back(1);
		}

		vector<int> second_poly;
		for (int i = 0; i <= second_degree; i++) {
			second_poly.push_back(1);
		}

		chrono::high_resolution_clock::time_point const startTimeKaratsubaAlg = chrono::high_resolution_clock::now();
		mainProcessKaratsubaAlg(first_poly, second_poly, nrProcesses);
		chrono::high_resolution_clock::time_point const endTimeKaratsubaAlg = chrono::high_resolution_clock::now();
		printf("\nkaratsuba time : %ld microseconds\n", chrono::duration_cast<chrono::microseconds>(endTimeKaratsubaAlg - startTimeKaratsubaAlg).count());
	}
	else {
		if (my_rank <= max_rank)
			karatsubaWorker(my_rank);

	}

	// Print the message
	//printf("Hello World! My rank is %d\n", my_rank);
	// Finalize the MPI environment.
	MPI_Finalize();
}