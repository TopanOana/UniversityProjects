//
// Created by Oana on 5/31/2022.
//

#include "extra_operation.h"
#include <assert.h>
#include <utility>
#include "Matrix.h"


void test_extra_operation(){
    Matrix m(4, 4);
    assert(m.nrLines() == 4);
    assert(m.nrColumns() == 4);
    m.modify(1, 1, 5);
    std::pair<int, int> pos = m.positionOf(5);
    assert(pos.first==1);
    assert(pos.second==1);

    std::pair<int, int> pos1 = m.positionOf(7);
    assert(pos1.first==-1);
    assert(pos1.second==-1);

}