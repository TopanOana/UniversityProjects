
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     BEGINN = 258,
     ELSE = 259,
     END = 260,
     IF = 261,
     PRINTF = 262,
     PROGRAM = 263,
     SCANF = 264,
     WHILE = 265,
     INT = 266,
     CHAR = 267,
     STRING = 268,
     ID = 269,
     CONST_INT = 270,
     CONST_STRING = 271,
     CONST_CHAR = 272,
     EQUALS = 273,
     ATRIB = 274,
     NOTEQUAL = 275,
     LESSEQUAL = 276,
     LESS = 277,
     GREATEREQUAL = 278,
     GREATER = 279,
     DIV = 280,
     MOD = 281,
     PLUS = 282,
     MINUS = 283,
     MULTIPLY = 284,
     OPENPAR = 285,
     CLOSEPAR = 286,
     OPENBRACKET = 287,
     CLOSEBRACKET = 288,
     OPENSQUARE = 289,
     CLOSESQUARE = 290,
     SEMICOLON = 291
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{

/* Line 1676 of yacc.c  */
#line 15 "lab9.y"

	char p_val[100];



/* Line 1676 of yacc.c  */
#line 94 "lab9.tab.h"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


