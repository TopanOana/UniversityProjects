//
// Created by Oana on 3/20/2022.
//

#pragma once
#include "repository/Repository.h"
#include "repository/Watch_list.h"
#include "repository/Watch_list_csv.h"

class Service {
private:
    Repository repo;
    Watch_list* watch_list;
public:
    Service(Repository r, Watch_list* w);

    Service();
    Service(const Service& s);

    ///ADMINISTRATOR MODE

    //Adds a new recipe to the repo
    void add_recipe_to_repo(std::string title, std::string presenter, std::string link, Duration duration, int number_of_likes);

    //Deletes a recipe from the repo
    void delete_recipe_from_repo(std::string title, std::string presenter, std::string link);

    //Updates a recipe in the repo
    void update_recipe_in_repo(std::string initial_title, std::string initial_presenter, std::string initial_link, std::string final_title, std::string final_presenter, std::string final_link, Duration final_duration, int final_number_of_likes);

    //Gets all the recipes in the repo
    std::vector<Recipe> get_all_recipes() const;

    //Gets number of recipes in the repo
    int get_number_of_recipes() const;

    //Initialize repo with 10 recipes
    void initialise_repo();

    ///USER MODE

    //Gets all the recipes in the watchlist
    std::vector<Recipe> get_watchlist_recipes() const;

    //Gets the length of the watchlist
    int get_number_of_recipes_watchlist() const;

    //Gets a repository with all the recipes by a given chef
    std::vector<Recipe> get_all_recipes_by_chef(std::string chef) const;

    //Adds a recipe to the watch list
    void add_recipe_to_watch_list(Recipe& to_add);

    //Deletes a recipe from the watch list
    void delete_recipe_from_watch_list(Recipe& to_delete);

    //Returns a recipe from the position in the watch list
    Recipe get_recipe_position_watch_list(int position);

    //Clears the repository and the watchlist:
    void clear_repo_and_watchlist();

    //Saves the watchlist to the file
    void save_watch_list_to_file();

    //view watchlist in file
    void show_watch_list_from_file();
};


