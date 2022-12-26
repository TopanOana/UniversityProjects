#include <exception>
#include "BagIterator.h"
#include "Bag.h"

using namespace std;


BagIterator::BagIterator(const Bag& c): bag(c)
{
	//TODO - Implementation
    current_element=bag.head;
    number_frequency=1;
}
//Theta(1)

void BagIterator::first() {
	//TODO - Implementation
    current_element=bag.head;
    number_frequency=1;
}
//Theta(1)

void BagIterator::next() {
	//TODO - Implementation
    if(!valid())
        throw exception();
    if (number_frequency==current_element->frequency) {
        this->current_element = this->current_element->next;
        number_frequency = 1;
    }
    else
        number_frequency++;
}


bool BagIterator::valid() const {
	//TODO - Implementation
    if(current_element != nullptr)
        return true;
	return false;
}



TElem BagIterator::getCurrent() const
{
	//TODO - Implementation
    if (current_element == nullptr)
        throw exception();
	return current_element->element;
}


