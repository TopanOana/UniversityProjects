cmake_minimum_required(VERSION 3.23)
project(lab2)

set(CMAKE_CXX_STANDARD 14)

add_executable(lab2 main.cpp
        HashTable/HashTable.h
        SymbolTable/SymbolTable.h
        SymbolTable/SymbolTable.cpp Scanner/Scanner.cpp Scanner/Scanner.h
        FiniteAutomata/FiniteAutomaton.h
        FiniteAutomata/FiniteAutomaton.cpp)
