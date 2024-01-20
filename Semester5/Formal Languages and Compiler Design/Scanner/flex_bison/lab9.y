%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define YYDEBUG 1

#define TYPE_INT 1
#define TYPE_CHAR 2
#define TYPE_STRING 3


%}


%union {
	char p_val[100];
}

%token BEGINN
%token ELSE
%token END
%token IF
%token PRINTF
%token PROGRAM
%token SCANF
%token WHILE
%token INT
%token CHAR
%token STRING
%token ID
%token <p_val> CONST_INT
%token <p_val> CONST_STRING
%token <p_val> CONST_CHAR
%token EQUALS
%token ATRIB
%token NOTEQUAL
%token LESSEQUAL
%token LESS
%token GREATEREQUAL
%token GREATER
%token DIV
%token MOD
%token PLUS
%token MINUS
%token MULTIPLY
%token OPENPAR
%token CLOSEPAR
%token OPENBRACKET
%token CLOSEBRACKET
%token OPENSQUARE
%token CLOSESQUARE
%token SEMICOLON


%%
program:	BEGINN statement_list END
		;
statement_list:		statement  
		| statement statement_list
		;
statement:	simple_statement SEMICOLON
		| structured_statement
		;
simple_statement: declaration_statement 
		| assignment_statement
		| input_statement
		| output_statement
		;
declaration_statement: type ID
		;
type: 	basic_type 
		| array_declaration
		;
basic_type: INT 
		| CHAR
		| STRING
		;
array_declaration: basic_type OPENSQUARE CONST_INT CLOSESQUARE
		;
assignment_statement: assigner ATRIB expression
		;
assigner: ID
		| access_array
		;
access_array: ID OPENSQUARE accesser CLOSESQUARE
		;
accesser: CONST_INT 
		| ID
		;
expression: term 
		| term add_subtract expression
		;
add_subtract: PLUS 
		| MINUS
		;
multiply_divide: MULTIPLY
		| DIV
		| MOD
		;
term: 	factor 
		| factor multiply_divide term
		;
factor: assigner
		| CONST_INT
		| OPENPAR expression CLOSEPAR
		;
input_statement: SCANF OPENPAR assigner CLOSEPAR
		;
output_statement: PRINTF OPENPAR expression CLOSEPAR
		;
structured_statement: if_statement 
		| while_statement
		;
if_statement: IF OPENPAR condition CLOSEPAR OPENBRACKET statement_list CLOSEBRACKET ELSE OPENBRACKET statement_list CLOSEBRACKET
		;
while_statement: WHILE OPENPAR condition CLOSEPAR OPENBRACKET statement_list CLOSEBRACKET
		;
condition: expression relation expression
		;
relation: GREATER 
		| GREATEREQUAL
		| LESS
		| LESSEQUAL
		| EQUALS
		| NOTEQUAL
		;



%%

extern int yylineno;

yyerror(char *s)
{
  printf("%s - line: %d\n", s, yylineno);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}


