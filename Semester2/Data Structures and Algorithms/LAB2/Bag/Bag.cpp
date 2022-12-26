#include "Bag.h"
#include "BagIterator.h"
#include <exception>

using namespace std;

//struct Bag::SLLNode{
//    TElem element;
//    int frequency;
//    SLLNode* next;
//    SLLNode(TElem e, int f, PNode n);
//};
//Theta(1)

Bag::SLLNode::SLLNode(TElem e, int f, PNode n) {
    element=e;
    frequency=f;
    next=n;
}
//Theta(1)

Bag::Bag() {
	//TODO - Implementation
    head = nullptr;
    nr_elements = 0;
}
//Theta(1)

void Bag::add(TElem elem) {
	//TODO - Implementation
    int ok = 0;
    PNode aux = head;
    while (aux != nullptr && ok==0)
    {
        if (aux->element==elem)
        {
            aux->frequency++;
            ok=1;
        }
        aux = aux->next;
    }
    if (ok==0)
    {
        PNode pn = new SLLNode(elem, 1, nullptr);
        pn->next = head;
        head = pn;
    }
    nr_elements++;
}
//BC=Theta(1), WC(n), Total= O(n)


bool Bag::remove(TElem elem) {
	//TODO - Implementation
    int ok = 0;
    PNode aux = head;
    PNode prev = nullptr;
    while (aux != nullptr && aux->element!=elem)
    {
        prev = aux;
        aux = aux->next;
    }
    if (aux!= nullptr)
    {
        ok=1;
        if (prev == nullptr)
        {
            if (aux->frequency==1)
            {
                head = head->next;
            }
            else{
                head->frequency--;
            }
        }
        else{
            if (aux->frequency==1)
            {
                prev->next = aux->next;
            }
            else {
                aux->frequency--;
            }
        }
        nr_elements--;
    }
    if (ok==1)
        return true;
	return false; 
}
//BC=Theta(1), WC=Theta(n), Total= O(n)


bool Bag::search(TElem elem) const {
	//TODO - Implementation
    PNode aux = head;
    int ok=0;
    while (aux!= nullptr && ok==0)
    {
        if (aux->element==elem)
            ok=1;
        aux=aux->next;
    }
    if (ok==1)
        return true;
	return false; 
}
//BC=Theta(1), WC=Theta(n), Total= O(n)

int Bag::nrOccurrences(TElem elem) const {
	//TODO - Implementation
    PNode aux = head;
    int ok=0;
    int number=0;
    while(aux!= nullptr && ok==0)
    {
        if (aux->element==elem)
        {
            number=aux->frequency;
            ok=1;
        }
        aux=aux->next;
    }
	return number;
}


int Bag::size() const {
	//TODO - Implementation
	return nr_elements;
}


bool Bag::isEmpty() const {
	//TODO - Implementation
	return (head == nullptr);
}

BagIterator Bag::iterator() const {
	return BagIterator(*this);
}


Bag::~Bag() {
	//TODO - Implementation
    while (head != nullptr)
    {
        PNode p = head;
        head = head->next;
        delete p;
    }
}
//
//
//BagIterator::BagIterator(const Bag& c): bag(c)
//{
//    //TODO - Implementation
//    current_element=bag.head;
//}
////Theta(1)
//
//void BagIterator::first() {
//    //TODO - Implementation
//    current_element=bag.head;
//}
////Theta(1)
//
//void BagIterator::next() {
//    //TODO - Implementation
//    if(!valid())
//        throw exception();
//    current_element = current_element->next;
//}
//
//
//bool BagIterator::valid() const {
//    //TODO - Implementation
//    if(current_element != nullptr)
//        return true;
//    return false;
//}
//
//
//
//TElem BagIterator::getCurrent() const
//{
//    //TODO - Implementation
//    if (current_element == nullptr)
//        throw exception();
//    return current_element->element;
//}


