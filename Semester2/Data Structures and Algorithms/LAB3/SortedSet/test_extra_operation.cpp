//
// Created by Oana on 5/3/2022.
//

#include "test_extra_operation.h"
#include "SortedSet.h"
#include <assert.h>
#include <iostream>
using namespace std;

bool r3(TComp e1, TComp e2) {
    if (e1 <= e2) {
        return true;
    }
    else {
        return false;
    }
}

void test_extra_operation()
{
    SortedSet s1(r3);
    assert(s1.add(5) == true);
    assert(s1.add(1) == true);
    assert(s1.add(10) == true);
    cout<<"Works"<<endl;
    SortedSet s2(r3);
    assert(s2.add(5) == true);
    assert(s2.add(1) == true);
    assert(s2.add(7) == true);
    assert(s2.add(9)==true);

    s1.intersection(s2);
    assert(s1.size()==2);
}