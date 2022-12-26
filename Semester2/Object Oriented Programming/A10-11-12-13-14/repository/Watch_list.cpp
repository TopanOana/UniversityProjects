///
//// Created by Oana on 3/20/2022.
////
//
#include "Watch_list.h"
#include "../validation/validator.h"

//Watch_list::Watch_list() = default;

void Watch_list::add_recipe(const Recipe& recipe) {
    Repo_validator::validate_add(this->recipes, recipe);
    recipes.add_recipe(recipe);
}

void Watch_list::delete_recipe(const Recipe &recipe) {
    Repo_validator::validate_delete(this->recipes, recipe);
    recipes.delete_recipe(recipe);
}

std::vector<Recipe> Watch_list::get_all_recipes() const {
    return recipes.get_all_recipes();
}

int Watch_list::get_number_of_recipes() const {
    return recipes.get_number_of_recipes();
}

Recipe Watch_list::get_recipe_from_position_watch_list(int position) const {
    return recipes.get_recipe_from_position(position);
}

Watch_list::~Watch_list() {

}

