//
// Created by Oana on 3/20/2022.
//

#include "Service.h"

Service::Service(Repository r) {
    this->repo = r;
    watch_list = Watch_list();
}

Service::Service() {

}



bool Service::add_recipe_to_repo(std::string title, std::string presenter, std::string link, Duration duration,
                                 int number_of_likes) {
    Recipe to_add = Recipe(title, presenter, link, number_of_likes, duration);
    return repo.add_recipe(to_add);
}

bool Service::delete_recipe_from_repo(std::string title, std::string presenter, std::string link) {
    Recipe to_delete = Recipe(title, presenter, link);
    return repo.delete_recipe(to_delete);
}

bool
Service::update_recipe_in_repo(std::string initial_title, std::string initial_presenter, std::string initial_link,
                               std::string final_title, std::string final_presenter, std::string final_link,
                               Duration final_duration, int final_number_of_likes) {
    Recipe initial = Recipe(initial_title, initial_presenter, initial_link);
    Recipe final = Recipe(final_title, final_presenter, final_link, final_number_of_likes, final_duration);
    return repo.update_recipe(initial, final);
}

Recipe *Service::get_all_recipes() const {
    return repo.get_all_recipes();
}

int Service::get_number_of_recipes() const{
    return repo.get_number_of_recipes();
}

Service::Service(const Service &s) {
    this->repo = s.repo;
    this->watch_list = s.watch_list;
}

void Service::initialise_repo() {
    add_recipe_to_repo("Chocolate chip muffins", "John Kanell","https://www.youtube.com/watch?v=aEFvNsBDCWs",Duration(7,30), 10);
    add_recipe_to_repo("Butter cookies", "John Kanell", "https://www.youtube.com/watch?v=CMrkchcWHNo", Duration(6, 15), 9);
    add_recipe_to_repo("Baked Feta Pasta", "Feelgoodfoodie", "https://www.youtube.com/watch?v=LuroDGsJkMA", Duration(4,58), 10);
    add_recipe_to_repo("The Ultimate Vegetable Lasagna", "Jamie Oliver", "https://www.youtube.com/watch?v=yMJw-X6Ea3w", Duration(4,54), 9);
    add_recipe_to_repo("Pizza", "Joshua Weissman", "https://www.youtube.com/watch?v=_jOMIdietUQ", Duration(15, 46), 6);
    add_recipe_to_repo("Eggs Benedict with Crispy Parma Ham", "Gordon Ramsay", "https://www.youtube.com/watch?v=gBJjRYk0yC0", Duration(4,35), 10);
    add_recipe_to_repo("Classic Carbonara", "Jamie Oliver", "https://www.youtube.com/watch?v=D_2DBLAt57c", Duration(4,40), 9);
    add_recipe_to_repo("Ground Beef Tacos", "Philip Lemoine", "https://www.youtube.com/watch?v=qL6ml7x56p4", Duration(7,41), 7);
    add_recipe_to_repo("Oreo Fudge", "Fitwaffle", "https://www.youtube.com/watch?v=2LEwTuDELsg", Duration(1,26), 8);
    add_recipe_to_repo("Garlic Bread", "Joshua Weissman","https://www.youtube.com/watch?v=FkV_fU5GoXM", Duration(8,6), 9);
}

Recipe *Service::get_watchlist_recipes() const {
    Recipe* recipes_in_watchlist = watch_list.get_all_recipes();
    return recipes_in_watchlist;
}

int Service::get_number_of_recipes_watchlist() const {
    return watch_list.get_number_of_recipes();
}

Recipe* Service::get_all_recipes_by_chef(std::string chef, int &nr_recipes) const{
    Recipe* result  = new Recipe[repo.get_number_of_recipes()];
    nr_recipes = 0;
    Recipe* recipes = repo.get_all_recipes();
    for(int i=0;i<repo.get_number_of_recipes();i++)
        if (recipes[i].get_chef()==chef)
            result[nr_recipes++]=recipes[i];
    return result;
}

bool Service::add_recipe_to_watch_list(Recipe& to_add) {
    return watch_list.add_recipe(to_add);
}

bool Service::delete_recipe_from_watch_list(Recipe& to_delete) {
    return watch_list.delete_recipe(to_delete);
}

Recipe Service::get_recipe_position_watch_list(int position) {
    return watch_list.get_recipe_from_position_watch_list(position);
}




