//
// Created by Oana on 3/20/2022.
//

#pragma once
#include "service/Service.h"

class User_interface {
private:
    Service service;
    int mode ; //0 for admin, 1 for user
public:
    explicit User_interface(Repository repo);
    //Starts up the application
    void start_application();
    //Chooses the mode of the application
    void choose_mode();

    ///ADMINISTRATOR MODE
    //Starts admin mode of application
    void start_admin_mode();
    //Prints admin menu for application
    void print_admin_menu();
    //Add a recipe admin mode
    void add_recipe_admin_mode();
    //Delete a recipe admin mode
    void delete_recipe_admin_mode();
    //Update a recipe admin mode
    void update_recipe_admin_mode();
    //Show all recipes admin mode
    void show_recipes_admin_mode();


    ///USER MODE
    //Starts user mode of application
    void start_user_mode();
    //Prints user menu for application
    void print_user_menu();
    //Shows all recipes in watch list
    void show_recipes_watch_list();
    //Plays recipe with option to add to watch list
    bool play_recipe_for_adding_user_mode(Recipe recipe);
    //Adds a recipe to the watch list
    void add_recipe_to_watch_list_user_mode(Recipe& recipe);


    //Shows the recipes by a given chef and does what needs to be done afterwards:)
    void see_recipes_by_a_given_chef_user_mode();
    //Plays a recipe for the user in watch list
    void play_recipe_watch_list_user_mode(Recipe recipe);
    //Modifies the database and adds another like to the recipe given
    void modify_database_add_like_to_recipe(Recipe& recipe);
    //Plays watch list in user mode
    void play_watch_list_user_mode();
};



