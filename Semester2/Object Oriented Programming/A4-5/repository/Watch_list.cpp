//
// Created by Oana on 3/20/2022.
//

#include "Watch_list.h"

Watch_list::Watch_list() {
    current_index = 0;
}

bool Watch_list::add_recipe(const Recipe& recipe) {
    return recipes.add_recipe(recipe);
}

bool Watch_list::delete_recipe(const Recipe &recipe) {
    return recipes.delete_recipe(recipe);
}

Recipe *Watch_list::get_all_recipes() const {
    return recipes.get_all_recipes();
}

int Watch_list::get_number_of_recipes() const {
    return recipes.get_number_of_recipes();
}

Recipe Watch_list::get_recipe_from_position_watch_list(int position) const {
    return recipes.get_recipe_from_position(position);
}



