///hashtable.h
//
// Created by oanam on 10/23/2023.
//

#ifndef LAB2_HASHTABLE_H
#define LAB2_HASHTABLE_H

using namespace std;

#include <vector>

template<class T>
struct Entry {
    T token;
    int nextPosition = -1;
};


template<class T>
class HashTable {
private :
    vector<Entry<T>> table;
    int size = 101; //prime number

    int hashFunction(T element);

    int nextEmptyPosition();


public:
    HashTable();

    HashTable(HashTable &ht);

    int addValue(T element);

    int position(T element);

    int removeValue(T element);

    int containsValue(T element);

    string toString();

};


template<class T>
int HashTable<T>::hashFunction(T element) {
    int sum = 0;

    for (char i: element) {
        sum += (int) i;
    }

    return sum % size;
}

template<class T>
HashTable<T>::HashTable() {
    table = vector<Entry<T>>(size);
}

template<class T>
int HashTable<T>::position(T element) {
    int hashValue = hashFunction(element);
    while (element != table[hashValue].token && table[hashValue].nextPosition != -1) {
        hashValue = table[hashValue].nextPosition;
    }

    if (element == table[hashValue].token)
        return hashValue;
    else {
        if (table[hashValue].token == "") {
            table[hashValue].token = element;
            table[hashValue].nextPosition = -1;
            return hashValue;
        } else {
            int newPosition = addValue(element);
            table[hashValue].nextPosition = newPosition;
            return newPosition;
        }
    }
}


template<class T>
int HashTable<T>::nextEmptyPosition() {
    for (int i = 0; i < table.size(); i++) {
        if (table[i].token == "")
            return i;
    }
    return -1;
}

template<class T>
int HashTable<T>::addValue(T element) {
    int newPosition = nextEmptyPosition();

    table[newPosition].token = element;
    table[newPosition].nextPosition = -1;

    return newPosition;
}

template<class T>
int HashTable<T>::removeValue(T element) {
    int position = containsValue(element);
    if (table[position].nextPosition == -1) {
        table[position].token = "";
        return position;
    } else {
        int hashValue = hashFunction(element);

        while (table[hashValue].nextPosition != position) {
            hashValue = table[hashValue].nextPosition;
        }

        table[hashValue].nextPosition = table[position].nextPosition;
        table[position].token = "";
        table[position].nextPosition = -1;
        return position;
    }
}

template<class T>
int HashTable<T>::containsValue(T element) {
    int hashValue = hashFunction(element);
    while (element != table[hashValue].token && table[hashValue].nextPosition != -1) {
        hashValue = table[hashValue].nextPosition;
    }

    if (element == table[hashValue].token)
        return hashValue;
    return -1;
}

template<class T>
HashTable<T>::HashTable(HashTable &ht) {
    this->table = ht.table;
    this->size = ht.size;
}

template<class T>
string HashTable<T>::toString() {
    string output = "";

    for (int i = 0; i < size; i++) {
        if (table[i].token != "") {
            output += to_string(i) + " " + table[i].token  + "\n";
        }
    }

    return output;
}


#endif //LAB2_HASHTABLE_H