#pragma once
//DO NOT INCLUDE SETITERATOR

//DO NOT CHANGE THIS PART
typedef int TElem;
typedef TElem TComp;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TELEM (-11111)
class SortedSetIterator;


class SortedSet {
	friend class SortedSetIterator;
private:
	//TODO - Representation
    int capacity;
    TComp* elements;
    int* next;
    int* prev;
    int head;
    int tail;
    int first_empty;
    int nr_elements;
    Relation relation;
    void resize();
    int allocate();
    void deallocate(int i);
    int create_node(TComp el);
    void insert_first(TComp el);
    void remove_from_position(int pos);


public:
	//constructor
	SortedSet(Relation r);

	//adds an element to the sorted set
	//if the element was added, the operation returns true, otherwise (if the element was already in the set) 
	//it returns false
	bool add(TComp e);

	
	//removes an element from the sorted set
	//if the element was removed, it returns true, otherwise false
	bool remove(TComp e);

	//checks if an element is in the sorted set
	bool search(TElem elem) const;


	//returns the number of elements from the sorted set
	int size() const;

	//checks if the sorted set is empty
	bool isEmpty() const;

	//returns an iterator for the sorted set
	SortedSetIterator iterator() const;

	// destructor
	~SortedSet();

   //keeps only the elements which appear in s as well(assume both SortedSets use the same relation)
    void intersection(const SortedSet&s);


};
