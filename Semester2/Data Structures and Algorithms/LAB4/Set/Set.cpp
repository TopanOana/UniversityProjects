#include <iostream>
#include "Set.h"
#include "SetIterator.h"

Set::Set() {
	capacity = 13;
    nr_elements = 0;

    elements = new TElem[capacity];

    for (int i=0;i<capacity;i++)
        elements[i]=NULL_TELEM;

}
//Theta(n)

bool Set::add(TElem elem) {
	//TODO - Implementation
    if (search(elem))
        return false;
    if (nr_elements == capacity-1)
        resize();
    int i=0;
    int pos=hash(elem, i);
    while(elements[pos]!=NULL_TELEM && elements[pos]!=DELETED)
        pos = hash(elem, ++i);
    elements[pos]=elem;
    nr_elements++;
    return true;
}
//BC = Theta(1) WC = O(n) Total = O(n)




bool Set::remove(TElem elem) {
	//TODO - Implementation
    int pos = get_position(elem);
    if (pos == -1) return false;
    elements[pos]=DELETED;
    nr_elements--;
    return true;
}
//Bc = Theta(1) WC = Theta(n) Total = O(n)

bool Set::search(TElem elem) const {
	//TODO - Implementation
    int i=0;
    int pos = hash(elem, i);
    while (elements[pos]!=elem && elements[pos]!=NULL_TELEM && elements[pos]!=DELETED)
        pos=hash(elem, ++i);
    if (elements[pos]==elem) return true;
    return false;
}
//BC = Theta(1) WC= Theta(n) Total= O(n)


int Set::size() const {

	return nr_elements;
}
//Theta(1)

bool Set::isEmpty() const {

	return (nr_elements==0);
}
//Theta(1)


Set::~Set() {

    delete[] elements;
}
//Theta(1)


SetIterator Set::iterator() const {
	return SetIterator(*this);
}

int Set::get_position(TElem elem) {
    int i=0;
    int pos = hash(elem, i);
    while (elements[pos]!=elem && elements[pos]!=NULL_TELEM)
        pos=hash(elem, ++i);
    if (elements[pos]==elem) return pos;
    return -1;

}
//Bc = Theta(1) WC=Theta(n) Total=O(n)

void Set::resize() {
    int old_capacity = capacity;
    capacity = next_prime_number(capacity*2);

    auto new_elements = new TElem[capacity];

    for (int i=0;i<capacity;i++)
        new_elements[i]=NULL_TELEM;

    for (int i=0;i<old_capacity;i++)
    {
        int j=0;
        int pos = hash(elements[i], j);

        while(new_elements[pos]!=NULL_TELEM)
        {
            j++;
            pos=hash(elements[i], j);
        }
        new_elements[pos] = elements[i];
    }
    delete[] elements;
    elements=new_elements;

}
//BC = Theta(n) WC = more than theta of n;




