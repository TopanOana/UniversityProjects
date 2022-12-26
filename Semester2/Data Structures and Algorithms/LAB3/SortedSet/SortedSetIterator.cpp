#include "SortedSetIterator.h"
#include <exception>

using namespace std;

SortedSetIterator::SortedSetIterator(const SortedSet& m) : multime(m)
{
	//TODO - Implementation
    current_position = multime.head;
}//Theta(1)


void SortedSetIterator::first() {
	//TODO - Implementation
    current_position = multime.head;
}//Theta(1)


void SortedSetIterator::next() {
	//TODO - Implementation
    if (!valid())
        throw exception();
    current_position = multime.next[current_position];
}//Theta(1)


TElem SortedSetIterator::getCurrent()
{
	//TODO - Implementation
    if (!valid())
        throw exception();
    return multime.elements[current_position];
}//Theta(1)

bool SortedSetIterator::valid() const {
	//TODO - Implementation
    if (current_position!=-1)
        return true;
	return false;
}//Theta(1)

