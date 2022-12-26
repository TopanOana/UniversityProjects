#include "SortedSet.h"
#include "SortedSetIterator.h"

SortedSet::SortedSet(Relation r) {
    relation = r;
    capacity = 10;
    elements = new TComp[capacity];
    next = new int[capacity];
    prev = new int[capacity];
    nr_elements = 0;

    for (int i=0; i<capacity-1;i++)
    {
        next[i] = i+1;
        prev[i] = i-1;
    }
    next[capacity-1]=-1;
    prev[capacity-1]=capacity-2;

    head =-1;
    tail =-1;
    first_empty = 0;

}
//O(capacity)


bool SortedSet::add(TComp elem) {
	//TODO - Implementation
    if (nr_elements==capacity)
        resize();
    if (isEmpty())
    {
        elements[first_empty] = elem;
        int next_one = next[first_empty];
        head = first_empty;
        tail = first_empty;
        next[first_empty] =-1;
        prev[first_empty]=-1;
        first_empty = next_one;
        prev[first_empty]=-1;
        nr_elements++;
        return true;
    }
    else{
        if (relation(elem, elements[head])) {
            if (elem == elements[head])
                return false;
            insert_first(elem);
            return true;
        }
        else{
            int go_through = next[head];
            while(go_through!=-1)
            {
                if (relation(elem, elements[go_through])){
                    if (elem == elements[go_through])
                        return false;
                    elements[first_empty]=elem;
                    int next_one = next[first_empty];
                    next[first_empty]=go_through;
                    prev[first_empty]=prev[go_through];
                    next[prev[go_through]]=first_empty;
                    prev[go_through] = first_empty;
                    first_empty = next_one;
                    prev[next_one]=-1;
                    nr_elements++;
                    return true;
                }
                go_through = next[go_through];
            }
            elements[first_empty]=elem;
            int next_one = next[first_empty];
            next[first_empty]=-1;
            next[tail]=first_empty;
            prev[first_empty] = tail;
            tail=first_empty;
            first_empty=next_one;
            prev[first_empty]=-1;
            nr_elements++;
            return true;
        }

    }
}
//BC = Theta(1) WC = O(n) Total: O(n)


bool SortedSet::remove(TComp elem) {
	//TODO - Implementation
    int go_through=head;
    int aux = -1;
    while (go_through!=-1 && elements[go_through]!=elem){
        aux = go_through;
        go_through=next[go_through];
    }
    if (elements[go_through]==elem)
    {
        if (head==go_through)
            head=next[head];
        else {
            if (tail==go_through)
                tail=prev[tail];
            next[aux]=next[go_through];
        }
        next[go_through]=first_empty;
        prev[go_through]=-1;
        prev[first_empty]=go_through;
        first_empty=go_through;
        nr_elements--;
        return true;
    }
	return false;
}
//BC =Theta(1) WC =O(n) Total=O(n)


bool SortedSet::search(TComp elem) const {
	//TODO - Implementation
    int go_through=head;
    while (go_through!=-1 && elements[go_through]!=elem)
        go_through=next[go_through];
    if (go_through!=-1)
        return true;
    return false;
}//O(n)


int SortedSet::size() const {
	return nr_elements;
}//Theta(1)


bool SortedSet::isEmpty() const {
	return nr_elements==0;
}//Theta(1)

SortedSetIterator SortedSet::iterator() const {
	return SortedSetIterator(*this);
}


SortedSet::~SortedSet() {
    delete[] elements;
    delete[] next;
    delete[] prev;
}//Theta(1)

void SortedSet::resize() {
    capacity*=2;
    auto* aux_elements = new TComp[capacity];
    int* aux_next = new int[capacity];
    int* aux_prev = new int[capacity];

    for (int i=0;i<nr_elements;i++)
    {
        aux_elements[i] = elements[i];
        aux_next[i] = next[i];
        aux_prev[i] = prev[i];
    }
    for (int i = nr_elements;i<capacity;i++)
    {
        aux_prev[i]=i-1;
        aux_next[i]=i+1;
    }
    aux_next[capacity-1]=-1;
    aux_prev[capacity-1]=capacity-2;

    //delete[] prev;
    prev = aux_prev;
    //delete[] next;
    next = aux_next;
    //delete[] elements;
    elements=aux_elements;
    first_empty = nr_elements;
}
//O(n)

int SortedSet::allocate() {
    int i = first_empty;
    tail = i;
    first_empty = next[first_empty];
    return i;
}//Theta(1)

void SortedSet::deallocate(int i) {
    next[i] = first_empty;
    first_empty = i;
}

int SortedSet::create_node(TComp el) {
    if (first_empty == -1)
        resize();
    int i = allocate();

    return 0;
}//Theta(1)

void SortedSet::insert_first(TComp el) {
    elements[first_empty]=el;
    int next_one=next[first_empty];
    next[first_empty]=head;
    prev[first_empty]=-1;
    prev[head]=first_empty;
    head=first_empty;
    first_empty=next_one;
    prev[first_empty]=-1;
    nr_elements++;
}
//Theta(1)

void SortedSet::intersection(const SortedSet &s) {
    int go_through_this = head;
    int go_through_s = s.head;
    TComp* all_elements = new TComp[capacity];
    int* all_next = new int[capacity];
    int* all_prev = new int[capacity];
    int head=0;
    int tail=0;
    int continue_=0;
    for (int i=0; i<capacity-1;i++)
    {
        all_next[i] = i+1;
        all_prev[i] = i-1;
    }
    all_next[capacity-1]=-1;
    all_prev[capacity-1]=capacity-2;
    nr_elements=0;
    while (go_through_this!=-1 && go_through_s !=-1)
    {
        if (elements[go_through_this]==s.elements[go_through_s])
        {
            all_elements[continue_]=elements[go_through_this];
            continue_++;
            go_through_s=s.next[go_through_s];
            go_through_this=next[go_through_this];
            nr_elements++;
        }
        else if (relation(elements[go_through_this],s.elements[go_through_s]))
                go_through_this=next[go_through_this];
            else
                go_through_s=s.next[go_through_s];
    }
    head=0;
    prev[head]=-1;
    tail = nr_elements-1;
    next[tail]=-1;
    first_empty=nr_elements;
    next=all_next;
    prev=all_prev;
    elements=all_elements;
}
//O(n*s.n)


void SortedSet::remove_from_position(int position)
{
    if (tail==position)
    {
        next[position]=-1;
        next[prev[position]]=-1;
        first_empty=position;
        nr_elements--;
        return;
    }
    next[position]=first_empty;
    prev[position]=-1;
    prev[first_empty]=position;
    first_empty=position;
    nr_elements--;
}
//Theta(1)








