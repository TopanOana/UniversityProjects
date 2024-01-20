//
// Created by oanam on 10/27/2023.
//

#include <regex>
#include <iostream>
#include <algorithm>

#include <functional>
#include <string>
#include <cstring>
#include "Scanner.h"

Scanner::Scanner() {
    populateTokens();
}

void Scanner::scanningAlgorithm(string filepath) {
    //opening input file
    bool correct = true;
    ifstream inputFile;
    inputFile.open(filepath);

    vector<string> listOfTokens;
    char *separators = "([{}]);";

    char *operators = "!=<>+-*/%";

    char line[100];
    int lineNumber = 1;
    while (inputFile.getline(line, 100)) {
        //scanning each line character by character in order to construct the keywords:
        int lengthOfLine = strlen(line);
        string currentToken;
        for (int j = 0; j < lengthOfLine; j++) {
            char c = line[j];
            //the end of a token
            if (strchr("\n\t ", c) != nullptr) {
                if (!currentToken.empty() && addTokenToPIF(currentToken, lineNumber) != 1) {
//                    inputFile.close();
//                    exit(1);
                    correct = false;
                }

                currentToken.clear();
            } else if (strchr(separators, c) != nullptr) {
                if (!currentToken.empty() && addTokenToPIF(currentToken, lineNumber) != 1) {
//                    inputFile.close();
//                    exit(1);
                    correct = false;
                }

                currentToken.clear();
                string nou;
                nou.push_back(c);
                if (addTokenToPIF(nou, lineNumber) != 1) {
//                    exit(1);
//                    inputFile.close();
                    correct = false;
                }

            } else if (strchr(operators, c) != nullptr) {
                if (!currentToken.empty() && addTokenToPIF(currentToken, lineNumber) != 1) {
//                    inputFile.close();
//                    exit(1);
                    correct = false;
                }
                currentToken.clear();
                if (strchr("+-", c) != nullptr && j + 1 < lengthOfLine && isdigit(line[j + 1]) != 0) {
                    currentToken.push_back(c);
                } else {
                    string nou;
                    nou.push_back(c);
                    if (j + 1 < lengthOfLine && line[j + 1] == '=') {
                        nou.push_back('=');
                        j++;
                    }
                    if (addTokenToPIF(nou, lineNumber) != 1) {
//                        inputFile.close();
//                        exit(1);
                        correct = false;
                    }
                }


            } else {
                currentToken.push_back(c);
            }

        }

        if (!currentToken.empty() && addTokenToPIF(currentToken, lineNumber) != 1) {
//            inputFile.close();
//            exit(1);
            correct = false;
        }
        currentToken.clear();

        lineNumber++;
    }

    inputFile.close();
    if (correct) {
        cout << "lexically correct" << endl;
        writeToOutputFiles();
    }

}

void Scanner::populateTokens() {
    ifstream tokenFile;
    tokenFile.open("D:\\UniversityWork\\FLCD_Oana\\lab1b\\token.in");

    char current_token[10];
    while (tokenFile.getline(current_token, 10)) {
        string auxiliar = current_token;
        auxiliar.erase(std::remove_if(auxiliar.begin(),
                                      auxiliar.end(),
                                      std::bind(std::isspace<char>,
                                                std::placeholders::_1,
                                                std::locale::classic())),
                       auxiliar.end());

        keywords.push_back(auxiliar);
    }

    tokenFile.close();

    integerFA.readFromFile();
    identifierFA.readFromFile();
}

int Scanner::addTokenToPIF(string token, int lineNumber) {
    if (std::find(keywords.begin(), keywords.end(), token) != keywords.end()) {
        programmingInternalForm.emplace_back(make_pair(token, -1));
    } else if (regex_match(token, regex(regexForCharacterConstants)) ||
               regex_match(token, regex(regexForStringConstants))) {//||
//               regex_match(token, regex(regexForIntegerConstants))) {
        int position = symbolTable.position(token);
        programmingInternalForm.emplace_back(make_pair("const", position));
    } else if (integerFA.checkSequence(token)==1) {
        int position = symbolTable.position(token);
        programmingInternalForm.emplace_back(make_pair("const", position));
//    } else if (regex_match(token, regex(regexForIdentifiers))) {
    } else if (identifierFA.checkSequence(token)==1) {
        int position = symbolTable.position(token);
        programmingInternalForm.emplace_back(make_pair("id", position));
    } else {
        cout << "Lexical error! Unidentified token " + token + " on line " << lineNumber << endl;
        return -1;
    }
    return 1;
}

void Scanner::writeToOutputFiles() {
    ofstream symbolTableOutput;
    ofstream programmingInternalFormOutput;

    symbolTableOutput.open("D:\\UniversityWork\\FLCD_Oana\\lab2\\ST1.out");
    programmingInternalFormOutput.open("D:\\UniversityWork\\FLCD_Oana\\lab2\\PIF1.out");

    string symbolTbl = symbolTable.toString();
    const char *headerST = "Symbol table represented as a hash table with coalesced chaining.\nSymbol Table for both constants and identifiers:\nPosition | Token\n";

    symbolTableOutput << headerST;
    symbolTableOutput << symbolTbl;
    programmingInternalFormOutput << "token | position\n";

    for (auto &i: programmingInternalForm) {
        programmingInternalFormOutput << i.first << " " << i.second << "\n";
    }

    symbolTableOutput.close();
    programmingInternalFormOutput.close();
}
