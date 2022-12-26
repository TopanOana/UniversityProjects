#include <exception>
#include "SetIterator.h"
#include "Set.h"


SetIterator::SetIterator(const Set& m) : set(m)
{
	//TODO - Implementation
    position = 0;
    if (set.isEmpty())
        position = set.capacity;
    while (position < set.capacity && (set.elements[position] == DELETED || set.elements[position] == NULL_TELEM))
        position++;
}
//BC = Theta(1), WC = Theta(m), Total = O(m)

void SetIterator::first() {
	//TODO - Implementation
    position = 0;
    if (set.isEmpty())
        position = set.capacity;
    while (position < set.capacity && (set.elements[position] == DELETED || set.elements[position] == NULL_TELEM))
        position++;
}
//BC = Theta(1), WC = Theta(m), Total:O(m)

void SetIterator::next() {
	//TODO - Implementation
    if (position >= set.capacity || set.isEmpty())
        throw std::exception();
    position++;
    while ((set.elements[position] == NULL_TELEM || set.elements[position] == DELETED) && position < set.capacity-1)
        position++;
}
//BC = Theta(1), WC = Theta(m), Total:O(m)


TElem SetIterator::getCurrent()
{
	//TODO - Implementation
    if (!valid())
        throw std::exception();
	return set.elements[position];
}
//Theta(1)

bool SetIterator::valid() const {
	//TODO - Implementation
    if (set.elements[position] == NULL_TELEM || set.elements[position] == DELETED || position >= set.capacity)
        return false;
	return true;
}
//Theta(1)





