//
// Created by Oana on 3/20/2022.
//

#pragma once
#include "domain/Recipe.h"
#include "repository/Repository.h"
#include "service/Service.h"

class Test_Recipe{
public:
    Test_Recipe();
    ///Constructors
    //Tests first constructor
    void test_constructor_empty();
    //Tests second constructor
    void test_constructor_full();
    //Tests copy constructor
    void test_copy_constructor();
    //Tests partial constructor
    void test_partial_constructor();

    ///Setters
    //Tests set_title()
    void test_set_title();
    //Tests set_chef()
    void test_set_chef();
    //Tests set_link()
    void test_set_link();
    //Tests set_duration()
    void test_set_duration();
    //Tests set_number_of_likes()
    void test_number_of_likes();

    ///Equality checker
    //Tests == operator overload
    void test_operator_overload_equal();

    ///To string
    //Tests recipe_string()
    void test_recipe_string();

    ///All tests in one spot
    void all_tests_recipe();


};

class Test_Repository{
public:
    Test_Repository();

    ///Test constructors
    void test_constructor();

    ///Test other functions
    //Tests get_all_recipes()
    void test_get_all_recipes();
    //Tests add_recipe()
    void test_add_recipe();
    //Tests delete_recipe()
    void test_delete_recipe();
    //Tests update_recipe()
    void test_update_recipe();
    //Tests get_number_of_recipes()
    void test_get_number_of_recipes();
    //Tests get_recipes_by_chef()
    void test_get_recipes_by_chef();

    ///ALL TESTS FOR REPO
    void test_all_repository();



};

class Test_Service{
public:
    Test_Service();

    ///constructor
    //tests empty constructor
    void test_constructor1();
    //tests full constructor
    void test_constructor2();
    //tests copy constructor
    void test_copy_constructor();

    ///tests actual methods
    //tests add_recipe_to_repo()
    void test_add_recipe_to_repo();
    //tests delete_recipe_from_repo()
    void test_delete_recipe_from_repo();
    //tests update_recipe_in_repo()
    void test_update_recipe_in_repo();
    //tests get_all_recipes()
    void test_get_all_recipes();
    //tests get_number_of_recipes()
    void test_get_number_of_recipes();

    //tests initialization
    void test_initialise_repo();

    //test get_watchlist_recipes()
    void test_get_watchlist_recipes();

    //test get_number_of_recipes_watchlist()
    void test_get_number_of_recipes_watchlist();

    //test add_recipe_to_watch_list
    void test_add_recipe_to_watch_list();

    //test delete_recipe_from_watch_list()
    void test_delete_recipe_from_watch_list();

    //test get_recipe_position_watch_list
    void test_get_recipe_position_watch_list();

    //test get_all_recipes_by_chef
    void test_get_all_recipes_by_chef();


    ///TEST ALL METHODS
    void test_all_service();


};


class Test_Watchlist {
public:
    Test_Watchlist();

    //tests the constructor
    void test_constructor();

    //tests adding a recipe to a watch list
    void test_add_recipe();

    //tests deleting a recipe to the watch list
    void test_delete_recipe();

    //tests get all recipes
    void test_get_all_recipes();

    //tests get number of recipes
    void test_get_number_of_recipes();

    //tests get recipe from position watchlist
    void test_get_recipe_from_pos_watchlist();

    ///tests all methods
    void test_all_watch_list();
};

class Test_All{
public:
    Test_All();
    //TESTS ALL OF THE LINES
    void tests_everything();
};