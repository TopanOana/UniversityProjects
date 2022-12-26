
#include <iostream>
#include "Matrix.h"
#include "ExtendedTest.h"
#include "ShortTest.h"
#include "extra_operation.h"

using namespace std;


int main() {


	testAll();
	//testAllExtended();
    test_extra_operation();
	cout << "Test End" << endl;
	system("pause");
}