#pragma once
//DO NOT INCLUDE SETITERATOR

//DO NOT CHANGE THIS PART
#define NULL_TELEM -111111
#define DELETED -222222
typedef int TElem;
class SetIterator;

class Set {
	//DO NOT CHANGE THIS PART
	friend class SetIterator;

    private:
		//TODO - Representation
        int nr_elements;
        TElem* elements;
        int capacity;

        int hash1(TElem x) const {
            if (x < 0)
                return (-1)*(x % capacity);
            return x % capacity;
        }

        int hash2(TElem x) const {
            if (x < 0)
                return 1 + ( (-1)* x % (capacity-1));
            return 1 + (x % (capacity-1));
        }

        int hash(TElem x, int i) const {
            return (hash1(x) + i * hash2(x)) % capacity;
        }

        void resize();

        bool prime_number(int x){
            if (x < 2) return false;
            for (int d = 2 ; d*d <= x; d++)
                if (x % d == 0) return false;
            return true;
        }

        int prev_prime_number(int x){
            int prev_prime = x-1;
            while (!prime_number(prev_prime)) prev_prime--;
            return prev_prime;
        }

        int next_prime_number(int x){
            int next_prime = x+1;
            while (!prime_number(next_prime)) next_prime++;
            return next_prime;
        }

        int get_position(TElem elem);

    public:
        //implicit constructor
        Set();

        //adds an element to the set
		//returns true if the element was added, false otherwise (if the element was already in the set and it was not added)
        bool add(TElem e);

        //removes an element from the set
		//returns true if e was removed, false otherwise
        bool remove(TElem e);

        //checks whether an element belongs to the set or not
        bool search(TElem elem) const;

        //returns the number of elements;
        int size() const;

        //check whether the set is empty or not;
        bool isEmpty() const;

        //return an iterator for the set
        SetIterator iterator() const;

        // destructor
        ~Set();



};





