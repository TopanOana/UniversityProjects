#include <iostream>
#include <regex>
#include "Scanner/Scanner.h"

using namespace std;

void printInitialMenu() {
    std::cout << "Menu options:\n";
    std::cout << "1. Go to scanning algorithm\n";
    std::cout << "2. Play with FAs\n";
//    std::cout<<"3. Display elements from a FA\n";
//    std::cout<<"4. Verify if a sequence is accepted by a DFA\n";
}

void printFAMenu() {
    std::cout << "FA Menu\n";
    std::cout << "1. Display elements from a FA\n";
    std::cout << "2. Verify if a sequence is accepted by a DFA\n";
    std::cout << "3. Exit program\n";
}

void printFADisplayMenu() {
    std::cout << "Display Menu:\n";
    std::cout << "1. Set of states\n";
    std::cout << "2. Alphabet\n";
    std::cout << "3. Initial state\n";
    std::cout << "4. Final states\n";
    std::cout << "5. Transitions\n";
}


void checkSequence(FiniteAutomaton finiteAutomaton) {
    string sequence;
    std::cout << "Enter the sequence: ";
    std::cin >> sequence;
    int value = finiteAutomaton.checkSequence(sequence);
    switch (value) {
        case 1:
            std::cout << "sequence " << sequence << " is accepted by the FA\n";
            break;
        case 0 :
            std::cout << "sequence " << sequence << " is not accepted by the FA\n";
            break;
        case -1:
            std::cout << "the FA is not a DFA\n";
    }
}

int main() {

    char value;
    printInitialMenu();
    std::cin >> value;
    if (value != '1') {
        std::cout << "input file:\n";
        std::string inputFilePath;
        std::cin >> inputFilePath;
        FiniteAutomaton finiteAutomaton(inputFilePath);
        finiteAutomaton.readFromFile();
        do {
            printFAMenu();
            std::cin >> value;
            switch (value) {
                case '1':
                    char nextOption;
                    printFADisplayMenu();
                    std::cin >> nextOption;
                    switch (nextOption) {
                        case '1':
                            std::cout << finiteAutomaton.displayStates() << "\n";
                            break;
                        case '2':
                            std::cout << finiteAutomaton.displayAlphabet() << "\n";
                            break;
                        case '3':
                            std::cout << finiteAutomaton.displayInitialState() << "\n";
                            break;
                        case '4':
                            std::cout << finiteAutomaton.displayFinalStates() << "\n";
                            break;
                        case '5':
                            std::cout << finiteAutomaton.displayTransitions() << "\n";
                            break;
                    }
                    break;
                case '2':
                    checkSequence(finiteAutomaton);
                    break;
                case '3':
                    break;
            }
        } while (value != '3');
    } else {
        Scanner scanner;
        scanner.scanningAlgorithm("D:\\UniversityWork\\FLCD_Oana\\lab2\\paux.txt");
    }
//    FiniteAutomaton finiteAutomaton("C:\\Users\\oanam\\Desktop\\LFTC\\lab2\\IdentifierFA.in");
//    finiteAutomaton.readFromFile();
//    finiteAutomaton.checkSequence("aaa");


    std::cout << "Hello, World!" << std::endl;
    return 0;
}
