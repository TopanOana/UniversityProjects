//
// Created by oanam on 10/23/2023.
//

#ifndef LAB2_SYMBOLTABLE_H
#define LAB2_SYMBOLTABLE_H

#include <string>
#include <vector>
#include "../HashTable/HashTable.h"


using namespace std;


class SymbolTable {
private:
    HashTable<string> hashTable;

public:

    SymbolTable();


    int position(string token);

    int removeToken(string token);

    int addToken(string token);

    string toString();




};


#endif //LAB2_SYMBOLTABLE_H