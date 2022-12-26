//
// Created by Oana on 3/20/2022.
//
#include "user_interface/User_interface.h"
#include "tests/Tests.h"

int main()
{
    Test_All t;
    t.tests_everything();
    //Repository repository = Repository();
    User_interface ui = User_interface();

    ui.start_application();

    return 0;
}