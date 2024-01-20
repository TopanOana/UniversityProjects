cmake_minimum_required(VERSION 3.27)
project(flex_bison C)

set(CMAKE_C_STANDARD 11)

add_executable(flex_bison lab9.tab.c lex.yy.c)
