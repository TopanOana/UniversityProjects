//
// Created by Oana on 3/20/2022.
//

#pragma once
#include "../domain/Recipe.h"
#include <vector>

class Repository {
private:
    std::vector<Recipe> arr;
public:
    Repository();
    ~Repository();
    //Gets all the recipes in the repo
    std::vector<Recipe> get_all_recipes() const;
    //Adds a recipe to the repo
    void add_recipe(const Recipe& new_tutorial);
    //Deletes a recipe from the repo
    void delete_recipe(const Recipe& to_delete);
    //Updates a recipe in the repo
    void update_recipe(const Recipe& initial, const Recipe& final);
    //Gets the number of recipes in the repo
    int get_number_of_recipes()const;
    //Gets the recipe from a certain position
    Recipe get_recipe_from_position(int position) const;

    void read_recipes_from_file();

    void save_recipes_to_file();

};
