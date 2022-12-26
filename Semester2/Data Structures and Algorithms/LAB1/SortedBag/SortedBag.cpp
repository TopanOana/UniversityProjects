#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <iostream>

SortedBag::SortedBag(Relation r) {
    this->capacity = 10;
    this->nr_elements = 0;
    this->relation = r;
    this->elements = new TComp[this->capacity];
}
//Theta(1)

void SortedBag::add(TComp e) {
    if (this->nr_elements == this->capacity)
        this->resize();
    int position = this->position_for_insert(e);
    for (int i=this->nr_elements;i>position;i--)
        this->elements[i]=this->elements[i-1];
    this->elements[position] = e;
    this->nr_elements++;
}
//BC = Theta(logn) WC = Theta(n) AC = Theta(logn) Overall: O(n)


bool SortedBag::remove(TComp e) {
	int position = this->position_found(e);
    if(position==-1)
        return false;
    else{
        for(int i=position;i<this->nr_elements-1;i++)
            this->elements[i]=this->elements[i+1];
        this->nr_elements--;
        return true;
    }
}
//BC = Theta(1) WC = Theta(n) AC = O(n)


bool SortedBag::search(TComp elem) const {
    int left = 0, right = this->nr_elements-1;
    int mid;
    while (left<=right)
    {
        mid = (left+right)/2;
        if (this->elements[mid]==elem)
            return true;
        else
        if (this->relation(this->elements[mid], elem)==0)
            right = mid-1;
        else
            left = mid+1;
    }
	return false;
}
//BC = Theta(1) WC = Theta(logn) AC = Theta(logn) Overall = O(logn)


int SortedBag::nrOccurrences(TComp elem) const {
	int position = this->position_found(elem);
    int first_index = position, second_index = position+1;
    int number = 0;
    while (first_index>=0 && this->elements[first_index]==elem){
        number++;
        first_index--;
    }
    while(second_index<this->nr_elements && this->elements[second_index]==elem){
        number++;
        second_index++;
    }
	return number;
}
//BC = Theta(1)  WC = Theta(n) AC = Theta(n)



int SortedBag::size() const {
	return this->nr_elements;
}
//Theta(1)


bool SortedBag::isEmpty() const {
    if (this->nr_elements==0)
        return true;
	return false;
}
//Theta(1)

SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}


SortedBag::~SortedBag() {
    delete[] this->elements;
}
//Theta(1)

void SortedBag::resize() {
    TComp* intermediate = new TComp[this->capacity*2];
    for (int i=0;i<this->nr_elements;i++)
        intermediate[i] = this->elements[i];
    delete this->elements;
    this->elements = intermediate;
    this->capacity = this->capacity*2;
}

// polynomial = n, n^2, n^3
//exponential = 2^n, 3^n, e^n => backtracking
//logarithmic = logn lgn
//factorial = n!
//merge sort = Theta(nlogn)
//cautare binara/ binary search = logn
int SortedBag::position_for_insert(TComp e) const {
    int left = 0, right = this->nr_elements-1;
    int mid;
    while (left<=right)
    {
        mid = (left+right)/2;
        if (this->elements[mid]==e)
            return mid;
        else
        if (this->relation(this->elements[mid], e)==0)
            right = mid-1;
        else
            left = mid+1;
    }
    return left;
}

int SortedBag::position_found(TComp e) const{
    int left = 0, right = this->nr_elements-1;
    int mid;
    while (left<=right)
    {
        mid = (left+right)/2;
        if (this->elements[mid]==e)
            return mid;
        else
        if (this->relation(this->elements[mid], e)==0)
            right = mid-1;
        else
            left = mid+1;
    }
    return -1;
}

int SortedBag::removeOccurrences(int nr, TComp elem) {
    if (nr < 0)
        throw std::exception();
    int position = position_found(elem);
    //int initial_number_of_occurrences = nrOccurrences(elem);
    int initial_nr = nr;
    int left = position, right = position+1;
    while (nr > 0 && elements[left]==elem && left >= 0) {
        nr--;
        left--;
    }
    while (nr>0 && elements[right]==elem && right<nr_elements){
        nr--;
        right++;
    }
    int nr_elements_deleted = initial_nr - nr;
    if (nr_elements_deleted!=0)
    {
        for (int i=right;i<nr_elements;i++)
            elements[i-nr_elements_deleted] = elements[i];
    }
    nr_elements-=nr_elements_deleted;
    return nr_elements_deleted;
}
//BC=Theta(logn), WC = Theta(n), Total = O(n)
