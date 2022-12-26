#include "Matrix.h"
#include <exception>
#include <utility>

using namespace std;


Matrix::Matrix(int nrLines, int nrCols) {
    nr_lines=nrLines;
	nr_columns=nrCols;
    root = nullptr;
}
//Theta(1)


int Matrix::nrLines() const {
	return nr_lines;
}
//Theta(1)


int Matrix::nrColumns() const {
	return nr_columns;
}
//Theta(1)



TElem Matrix::element(int i, int j) const {
    if ((i<0 || i>nr_lines) || (j<0 || j>=nr_columns))
        throw std::exception();
    return get_element_from_bst(root, i, j);
}
//BC = Theta(1), WC = Theta(n) Total = O(n)


TElem Matrix::modify(int i, int j, TElem e) {
    //TODO - Implementation
    if ((i < 0 || i > nr_lines) || (j < 0 || j >= nr_columns))
        throw std::exception();
    TElem to_return = NULL_TELEM;
    TElem element = this->element(i, j);

    if (e != NULL_TELEM || element != NULL_TELEM) {
        if (e == NULL_TELEM) {
            TElem old_element = 0;
            TElem remove_elements = 0;

            root = remove_node_from_bst(root, old_element, remove_elements, i, j);
            to_return = old_element;
        } else if (element == NULL_TELEM) {
            root = add_node_to_bst(root, e, i, j);
        } else
            to_return = modify_node_from_bst(root, e, i, j);
    }
    return to_return;
}
//BC = Theta(1), WC = Theta(n) Total = O(n)


Matrix::BSTNode* Matrix::add_node_to_bst(Matrix::BSTNode *node, TElem e, int l, int c) {
    if (node == nullptr)
    {
        node = new BSTNode;
        node->info = e;
        node->line = l;
        node->column = c;
        node->left = nullptr;
        node->right = nullptr;
    }
    else{
        if (l < node->line || (l == node->line && c < node->column))
            node->left = add_node_to_bst(node->left, e, l, c);
        else
            node->right = add_node_to_bst(node->right, e, l, c);
    }
    return node;
}
//BC = Theta(1), WC = Theta(n) Total = O(n)


TElem Matrix::get_element_from_bst(Matrix::BSTNode *node, int l, int c) const {
    if (node == nullptr)
        return NULL_TELEM;
    if (node->line == l && node->column == c)
        return node->info;
    if (l < node->line || (l == node->line && c < node->column))
        return get_element_from_bst(node->left,l,c);
    else
        return get_element_from_bst(node->right, l, c);
}
//BC = Theta(1), WC = Theta(n) Total = O(n)

TElem Matrix::modify_node_from_bst(Matrix::BSTNode *node, TElem e, int l, int c) {
    if (node == nullptr)
        return NULL_TELEM;
    else {
        if (node->line == l && node->column == c) {
            TElem aux = node->info;
            node->info = e;
            return aux;
        } else {
            if (l < node->line || (l == node->line && c < node->column))
                modify_node_from_bst(node->left, e, l, c);
            else
                modify_node_from_bst(node->right, e, l, c);
        }
    }
}
//BC = Theta(1), WC = Theta(n) Total = O(n)

Matrix::BSTNode *Matrix::remove_node_from_bst(Matrix::BSTNode *node, TElem &old_element, TElem &remove_element, int l, int c) {
    if (node == nullptr)
        return nullptr;
    if  (l < node->line || (l==node->line && c < node->column))
        node->left = remove_node_from_bst(node->left, old_element, remove_element, l, c);
    else
        if (l > node->line || (l==node->line && c > node->column))
            node->right = remove_node_from_bst(node->right, old_element, remove_element, l, c);
        else
            if (node->left != nullptr && node->right != nullptr)
            {
                BSTNode* temp;
                temp = get_minimum_node(node->right);
                if (remove_element == 0)
                    old_element = node->info;
                node->info=temp->info;
                node->line=temp->line;
                node->column=temp->column;
                remove_element++;
                node->right = remove_node_from_bst(node->right, old_element, remove_element, node->line, node->column);
            }
            else{
                BSTNode* temp;
                temp=node;
                if(remove_element==0)
                    old_element=node->info;
                if(node->left == nullptr)
                    node = node->right;
                else {
                    if (node->right == nullptr)
                        node = node->left;
                }
                delete temp;
            }
    return node;
}
//BC = Theta(1), WC = Theta(n) Total = O(n)

Matrix::BSTNode *Matrix::get_minimum_node(Matrix::BSTNode *node) {
    if (node == nullptr)
        return nullptr;
    if (node->left == nullptr)
        return node;
    return get_minimum_node(node->left);
}
//BC = Theta(1), WC = Theta(n) Total = O(n)

std::pair<int, int> Matrix::positionOf(TElem elem) const {
    int line,column;
    BSTNode* check = traversal_find_elem(root, elem);
    if (check!=nullptr){
        line=check->line;
        column=check->column;
    }
    else{
        line=-1;
        column=-1;
    }
    std::pair<int, int> pair;
        pair.first=line;
        pair.second=column;
    return pair;
}

Matrix::BSTNode * Matrix::traversal_find_elem(Matrix::BSTNode *node, TElem elem) const {
    if (node == nullptr)
        return nullptr;
    if(node->info == elem)
        return node;
    BSTNode* left_side_check = traversal_find_elem(node->left, elem);
    BSTNode* right_side_check = traversal_find_elem(node->right, elem);

    if (left_side_check != nullptr)
        return left_side_check;
    else
        if(right_side_check != nullptr)
            return right_side_check;
    return nullptr;
}
//BC = Theta(1) WC = Theta(n) TOTAL = O(n)


