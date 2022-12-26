//
// Created by Oana on 3/20/2022.
//

#pragma once
#include "repository/Repository.h"
#include "domain/Recipe.h"

class Watch_list {
private:
    Repository recipes;
    int current_index;
public:
    //Constructor
    Watch_list();

    //Adds a recipe to the watch list
    bool add_recipe(const Recipe& recipe);
    //Deletes a recipe from the watch list
    bool delete_recipe(const Recipe& recipe);

    //Gets all recipes in watch list
    Recipe* get_all_recipes()const;

    //Gets length of watch list
    int get_number_of_recipes() const;

    //Gets the recipe at the position specified
    Recipe get_recipe_from_position_watch_list(int position) const;
};


