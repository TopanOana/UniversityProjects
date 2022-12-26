////
//// Created by Oana on 3/20/2022.
////
//
#pragma once
#include "repository/Repository.h"
#include "domain/Recipe.h"

//class Watch_list {
//protected:
//    Repository recipes;
//public:
//    //Constructor
//    Watch_list();
//    ~Watch_list();
//
//    //Adds a recipe to the watch list
//    void add_recipe(const Recipe& recipe);
//    //Deletes a recipe from the watch list
//    void delete_recipe(const Recipe& recipe);
//
//    //Gets all recipes in watch list
//    std::vector<Recipe> get_all_recipes()const;
//
//    //Gets length of watch list
//    int get_number_of_recipes() const;
//
//    //Gets the recipe at the position specified
//    Recipe get_recipe_from_position_watch_list(int position) const;
//};
//
//




class Watch_list {
protected:
    Repository recipes;
public:
    ~Watch_list();

    //Adds a recipe to the watch list
    void add_recipe(const Recipe& recipe);
    //Deletes a recipe from the watch list
    void delete_recipe(const Recipe& recipe);

    //Gets all recipes in watch list
    std::vector<Recipe> get_all_recipes()const;

    //Gets length of watch list
    int get_number_of_recipes() const;

    //Gets the recipe at the position specified
    Recipe get_recipe_from_position_watch_list(int position) const;

    //saves to file
    virtual void save_watchlist_to_file() const = 0;

    virtual void show_watchlist_from_file() const = 0;
};
