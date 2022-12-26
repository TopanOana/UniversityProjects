//
// Created by Oana on 3/20/2022.
//

#pragma once
#include "DynamicVector.h"
#include "domain/Recipe.h"


class Repository {
private:
    DynamicVector<Recipe> arr;
public:
    Repository();
    ~Repository();
    //Gets all the recipes in the repo
    Recipe* get_all_recipes() const;
    //Adds a recipe to the repo
    bool add_recipe(const Recipe& new_tutorial);
    //Deletes a recipe from the repo
    bool delete_recipe(const Recipe& to_delete);
    //Updates a recipe in the repo
    bool update_recipe(const Recipe& initial, const Recipe& final);
    //Gets the number of recipes in the repo
    int get_number_of_recipes()const;
    //Gets the recipe from a certain position
    Recipe get_recipe_from_position(int position) const;

};


