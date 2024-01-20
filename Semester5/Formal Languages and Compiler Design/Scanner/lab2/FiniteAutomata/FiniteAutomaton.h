//
// Created by oanam on 11/10/2023.
//

#ifndef LAB4_FINITEAUTOMATON_H
#define LAB4_FINITEAUTOMATON_H


#include <vector>
#include <string>

struct Transition {
    std::string source_state;
    std::string value;
    std::string destination_state;
};


class FiniteAutomaton {
private:
    std::string filepath;
    std::vector<std::string> states;
    std::vector<std::string> alphabet;
    std::string initial_state;
    std::vector<std::string> final_states;
    std::vector<Transition> transitions;

    Transition findTransitionBySourceAndValue(std::string source, std::string value);

    bool checkDFA();

public:
    FiniteAutomaton(std::string filepath) {
        this->filepath = filepath;
    }

    void readFromFile();

    void displayElements();

    int checkSequence(std::string sequence);

    std::string displayStates();

    std::string displayAlphabet();

    std::string displayInitialState();

    std::string displayFinalStates();

    std::string displayTransitions();


};


#endif //LAB4_FINITEAUTOMATON_H
