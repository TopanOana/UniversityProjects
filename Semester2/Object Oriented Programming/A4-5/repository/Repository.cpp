//
// Created by Oana on 3/20/2022.
//

#include "Repository.h"


Repository::Repository() {

}

Recipe *Repository::get_all_recipes() const {
    return arr.get_all_elems();
}

bool Repository::add_recipe(const Recipe &new_tutorial) {
    int nr_elements = arr.get_size();
    //arr.add_element(new_tutorial);
    //arr.operator+(new_tutorial);
    arr.add_element(new_tutorial);
    if (arr.get_size()-1 == nr_elements)
        return true;
    else
        return false;
}

bool Repository::delete_recipe(const Recipe &to_delete) {
    int nr_elements = arr.get_size();
    arr.delete_element(to_delete);
    if(nr_elements-1 == arr.get_size())
        return true;
    else
        return false;
}

bool Repository::update_recipe(const Recipe &initial, const Recipe &final) {
    int pos = arr.position_element(initial);
    if (pos==-1)
        return false;
    arr.update_element(initial, final);
    return true;
}

int Repository::get_number_of_recipes() const {
    return arr.get_size();
}

Recipe Repository::get_recipe_from_position(int position) const {
    return arr.get_element_from_position(position);
}

Repository::~Repository() {

}

