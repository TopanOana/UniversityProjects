//
// Created by Oana on 3/29/2022.
//

#include "ExtraOperation.h"
#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <assert.h>
#include <iostream>

bool relation4(TComp e1, TComp e2) {
    return e1 <= e2;
}


void test_extra_operation(){
    SortedBag sb(relation4);
    sb.add(5);
    sb.add(6);
    sb.add(0);
    sb.add(5);
    sb.add(10);
    sb.add(8);

    assert(sb.size() == 6);
    assert(sb.nrOccurrences(5) == 2);

    assert(sb.removeOccurrences(1,5)==1);
    assert(sb.size()==5);

    try {
        sb.removeOccurrences(-2, 6);
        assert(false);
    }
    catch(...){
        assert(true);
    }
    assert(sb.removeOccurrences(5,5)==1);
    assert(sb.size()==4);

    assert(sb.removeOccurrences(10, 28)==0);
    assert(sb.size()==4);

    std::cout<<"Test extra operation done.\n";
}