#pragma once
//DO NOT INCLUDE SORTEDBAGITERATOR

//DO NOT CHANGE THIS PART
typedef int TComp;
typedef TComp TElem;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TCOMP -11111;

class SortedBagIterator;

class SortedBag {
	friend class SortedBagIterator;

private:
	//TODO - Representation
    TComp* elements;
    int capacity;
    int nr_elements;
    Relation relation;


public:
	//constructor
	SortedBag(Relation r);

	//adds an element to the sorted bag
	void add(TComp e);

	//removes one occurence of an element from a sorted bag
	//returns true if an element was removed, false otherwise (if e was not part of the sorted bag)
	bool remove(TComp e);

	//checks if an element appearch is the sorted bag
	bool search(TComp e) const;

	//returns the number of occurrences for an element in the sorted bag
	int nrOccurrences(TComp e) const;

	//returns the number of elements from the sorted bag
	int size() const;

	//returns an iterator for this sorted bag
	SortedBagIterator iterator() const;

	//checks if the sorted bag is empty
	bool isEmpty() const;

	//destructor
	~SortedBag();

    //removes nr occurrences of elem.
    //If the element appears less than nr times,
    //all occurrences will be removed.
    //returns the number of times the element was removed.
    //throws an exception if nr is negative
    int removeOccurrences(int nr,TComp elem);
private:
    //resize
    void resize();

    //position to insert into
    int position_for_insert(TComp e) const;

    //position the element e was found at
    int position_found(TComp e) const;
};