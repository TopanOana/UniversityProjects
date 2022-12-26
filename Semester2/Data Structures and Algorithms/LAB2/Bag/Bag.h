#pragma once
//DO NOT INCLUDE BAGITERATOR


//DO NOT CHANGE THIS PART
#define NULL_TELEM -111111;
typedef int TElem;
class BagIterator;
class Bag {

private:
	//TODO - Representation
    struct SLLNode{
            TElem element;
            int frequency;
            SLLNode* next;
           SLLNode(TElem e, int f, SLLNode* n);
    };
    typedef SLLNode *PNode;
    PNode head;
    int nr_elements;

	//DO NOT CHANGE THIS PART
	friend class BagIterator;
public:
	//constructor
	Bag();

	//adds an element to the bag
	void add(TElem e);

	//removes one occurence of an element from a bag
	//returns true if an element was removed, false otherwise (if e was not part of the bag)
	bool remove(TElem e);

	//checks if an element appearch is the bag
	bool search(TElem e) const;

	//returns the number of occurrences for an element in the bag
	int nrOccurrences(TElem e) const;

	//returns the number of elements from the bag
	int size() const;

	//returns an iterator for this bag
	BagIterator iterator() const;

	//checks if the bag is empty
	bool isEmpty() const;

	//destructor
	~Bag();
};

//
//class BagIterator
//{
//    //DO NOT CHANGE THIS PART
//    friend class Bag;
//
//private:
//    const Bag& bag;
//    //TODO  - Representation
//    Bag::PNode current_element;
//
//    BagIterator(const Bag& c);
//public:
//    void first();
//    void next();
//    TElem getCurrent() const;
//    bool valid() const;
//};
