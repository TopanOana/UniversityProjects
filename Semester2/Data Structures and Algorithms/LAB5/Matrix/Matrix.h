#pragma once

#include <utility>

//DO NOT CHANGE THIS PART
typedef int TElem;
#define NULL_TELEM 0

class Matrix {

private:
	//TODO - Representation
    int nr_lines;
    int nr_columns;

    struct BSTNode{
        TElem info;
        int line, column;
        BSTNode* left;
        BSTNode* right;
    };

    BSTNode* root;

    BSTNode* add_node_to_bst(BSTNode* node, TElem e, int l, int c);

    TElem get_element_from_bst(BSTNode* node, int l, int c) const;

    TElem modify_node_from_bst(BSTNode* node, TElem e, int l, int c);

    BSTNode* remove_node_from_bst(BSTNode* node, TElem& old_element, TElem& remove_element, int l, int c);

    BSTNode* get_minimum_node(BSTNode* node);

    BSTNode * traversal_find_elem(BSTNode* node, TElem elem) const;



public:
	//constructor
	Matrix(int nrLines, int nrCols);

	//returns the number of lines
	int nrLines() const;

	//returns the number of columns
	int nrColumns() const;

	//returns the element from line i and column j (indexing starts from 0)
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem element(int i, int j) const;

	//modifies the value from line i and column j
	//returns the previous value from the position
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem modify(int i, int j, TElem e);

    //returns the position of element elem from the Matrix (as a line,column pair).
    // If elem occurs multiple times,any position where it appears is fine.
    // If elem is not in the Matrix,return the pair<-1,-1>.
    std::pair<int,int> positionOf(TElem elem)const;



};
