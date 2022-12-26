//
// Created by Oana on 3/20/2022.
//

#include "Repository.h"
#include "../domain/Recipe.h"
#include <algorithm>
#include <fstream>


Repository::Repository() {

}

std::vector<Recipe> Repository::get_all_recipes() const {
    return arr;
}

void Repository::add_recipe(const Recipe &new_tutorial) {
    arr.push_back(new_tutorial);
}

void Repository::delete_recipe(const Recipe &to_delete) {
    auto position = std::find(arr.begin(), arr.end(), to_delete);
    arr.erase(position);
}

void Repository::update_recipe(const Recipe &initial, const Recipe &final) {
    auto position = std::find(arr.begin(), arr.end(), initial);
    int pos = position - arr.begin();
    arr[pos] = final;
}

int Repository::get_number_of_recipes() const {
    return arr.size();
}

Recipe Repository::get_recipe_from_position(int position) const {
    return arr[position];
}

Repository::~Repository() {

}

void Repository::read_recipes_from_file() {
    std::ifstream f("C:/Users/Oana/CLionProjects/untitled6/recipes.txt");
    if (!f.is_open())
        return;
    Recipe input{};
    while (f>>input)
    {
        add_recipe(input);
    }
    f.close();

}

void Repository::save_recipes_to_file() {
    std::ofstream f("C:/Users/Oana/Documents/GitHub/oop-work/a8-9-917-Topan-Oana-Maria/recipes.txt");
    if (!f.is_open())
        return;
    for (auto r: this->arr)
    {
        f<<r;
    }
    f.close();
}
