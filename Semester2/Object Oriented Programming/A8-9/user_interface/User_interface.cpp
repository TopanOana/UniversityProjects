//
// Created by Oana on 3/20/2022.
//

#include "User_interface.h"
#include <iostream>
#include <string>
#include <algorithm>
#include <fstream>
#include "validation/validator.h"
#include "repository/Watch_list_csv.h"
#include "repository/Watch_list_html.h"

User_interface::User_interface(){
    //service = Service(repo);
    //mode = -1;
}

void User_interface::start_application() {
    std::cout << "Welcome to the cooking tutorials \n We have video recipes you can watch.\n";
    //service.initialise_repo();
    std::cout<<"What kind of file should you save the watchlist in?\n 1. CSV \n2. HTML\n";
    int option;
    std::cin>>option;
    Watch_list* w;
    if (option==1){
        w = new Watch_list_csv("C:/Users/Oana/Documents/GitHub/oop-work/a8-9-917-Topan-Oana-Maria/watchlist.csv");
    }
    else if (option==2){
        w = new Watch_list_html("C:/Users/Oana/Documents/GitHub/oop-work/a8-9-917-Topan-Oana-Maria/watchlist.html");
    }
    const Repository & repo = Repository();
    service = Service(repo, w);
    read_recipes_from_file_admin_mode();
    choose_mode();
}

void User_interface::choose_mode() {
    std::cout << "Choose a mode: admin or user.\n";
    std::string mode;
    std::cin>>mode;
    if (mode == "admin")
    {
        this->mode = 0;
        start_admin_mode();
    }
    else if (mode == "user") {start_user_mode();}
    else if (mode == "exit") {std::cout<<"Goodbye!"; save_all_recipes_to_file_admin_mode(); service.save_watch_list_to_file();return;}
    else std::cout<<"Mode unavailable.\n";
    choose_mode();
}

void User_interface::start_admin_mode() {
    print_admin_menu();
    std::cout<<"Choose an option: ";
    std::string choice;
    std::cin>>choice;
    if(choice.find_first_not_of("0123456789") == std::string::npos)
    {
        int choice_integer = std::stoi(choice);
        switch(choice_integer){
            case 1:
                add_recipe_admin_mode();
                break;
            case 2:
                delete_recipe_admin_mode();
                break;
            case 3:
                update_recipe_admin_mode();
                break;
            case 4:
                show_recipes_admin_mode();
                break;
            case 5:
                return;
            case 6:
                save_all_recipes_to_file_admin_mode();
                break;
            case 7:
                read_recipes_from_file_admin_mode();
                break;
            default:
                std::cout<<"Option does not exist.\n";
        }
    }
    else
        std::cout<<"Option does not exist.\n";
    start_admin_mode();
}

void User_interface::print_admin_menu() {
    std::cout<<"Admin menu:\n";
    std::cout<<"1. Add a recipe.\n";
    std::cout<<"2. Delete a recipe.\n";
    std::cout<<"3. Update a recipe.\n";
    std::cout<<"4. Show all recipes.\n";
    std::cout<<"5. Exit application.\n";
    std::cout<<"6. Save to file.\n";
    std::cout<<"7. Read from file.\n";

}

void User_interface::add_recipe_admin_mode() {
    std::cout<<"\nOk so you can add a recipe.\n";
    std::string title_, chef_, link_, number_of_likes_, minutes_, seconds_;
    int number_of_likes, minutes, seconds;
    std::cin.get();
    std::cout<<"Title of video: ";
    std::getline(std::cin, title_);
    std::cout<<"\nChef's name: ";
    std::getline(std::cin, chef_);
    std::cout<<"\nLink to video: ";
    std::getline(std::cin, link_);
    std::cout<<"\nNumber of likes: ";
    std::getline(std::cin, number_of_likes_);
    if (number_of_likes_.empty() || number_of_likes_.find_first_not_of("-0123456789") != std::string::npos)
    {
        std::cout<<"Invalid number of likes.\n";
        return;
    }
    number_of_likes = std::stoi(number_of_likes_);
    std::cout<<"\nDuration:\nMinutes: ";
    std::getline(std::cin, minutes_);
    if (minutes_.empty() || minutes_.find_first_not_of("-0123456789") != std::string::npos)
    {
        std::cout<<"Invalid minutes.\n";
        return;
    }
    minutes = std::stoi(minutes_);


    std::cout<<"\nSeconds: ";
    std::getline(std::cin, seconds_);
    if (seconds_.empty() || seconds_.find_first_not_of("-0123456789") != std::string::npos)
    {
        std::cout<<"Invalid seconds.\n";
        return;
    }
    seconds = std::stoi(seconds_);
    try{
        service.add_recipe_to_repo(title_, chef_,link_,Duration(minutes,seconds), number_of_likes);
        std::cout<<"Recipe added successfully.\n";
    }
    catch (Validation_exception& ex){
        std::cout<<"Errors occurred:\n";
        std::cout<<ex.get_message()<<"\n";
    }
}

void User_interface::delete_recipe_admin_mode() {
    std::cout<<"\nSo you can delete a recipe:\n";
    std::string title_, chef_, link_;
    std::cin.get();
    std::cout<<"Title of video: ";
    std::getline(std::cin, title_);
    std::cout<<"\nChef's name: ";
    std::getline(std::cin, chef_);
    std::cout<<"\nLink to video: ";
    std::getline(std::cin, link_);
    try{
        service.delete_recipe_from_repo(title_,chef_,link_);
        std::cout<<"Recipe deleted successfully.\n";
    }
    catch (Validation_exception& ex){
        std::cout<<"Errors occurred:\n";
        std::cout<<ex.get_message()<<"\n";
    }
}

void User_interface::update_recipe_admin_mode() {
    std::string title_initial, chef_initial, link_initial, number_of_likes_final, minutes_final, seconds_final;
    int number_of_likes, minutes, seconds;
    std::cin.get();
    std::cout<<"Initial title of video: ";
    std::getline(std::cin, title_initial);
    std::cout<<"\nInitial chef's name: ";
    std::getline(std::cin, chef_initial);
    std::cout<<"\nInitial link to video: ";
    std::getline(std::cin, link_initial);
    std::string title_final, chef_final, link_final;
    std::cout<<"\nFinal title of video: ";
    std::getline(std::cin, title_final);
    std::cout<<"\nFinal chef's name: ";
    std::getline(std::cin, chef_final);
    std::cout<<"\nFinal link to video: ";
    std::getline(std::cin, link_final);
    std::cout<<"\nFinal number of likes: ";
    std::getline(std::cin, number_of_likes_final);
    if (number_of_likes_final.empty() || number_of_likes_final.find_first_not_of("-0123456789") != std::string::npos)
    {
        std::cout<<"Invalid number of likes.\n";
        return;
    }
    number_of_likes = std::stoi(number_of_likes_final);
    std::cout<<"\nFinal duration:\nMinutes: ";
    std::getline(std::cin, minutes_final);
    if (minutes_final.empty() || minutes_final.find_first_not_of("-0123456789") != std::string::npos)
    {
        std::cout<<"Invalid minutes.\n";
        return;
    }
    minutes = std::stoi(minutes_final);

    std::cout<<"\nSeconds: ";
    std::getline(std::cin, seconds_final);
    if (seconds_final.empty() || seconds_final.find_first_not_of("-0123456789") != std::string::npos)
    {
        std::cout<<"Invalid seconds.\n";
        return;
    }
    seconds = std::stoi(seconds_final);

    try{
        service.update_recipe_in_repo(title_initial,chef_initial,link_initial,title_final,chef_final,link_final,Duration(minutes,seconds),number_of_likes);
        std::cout<<"Recipe updated successfully.\n";
    }
    catch (Validation_exception& ex)
    {
        std::cout<<"Errors occurred:\n";
        std::cout<<ex.get_message()<<"\n";
    }
}

void User_interface::show_recipes_admin_mode() {
    std::cout<<"\nAll recipes in database: \n";
    std::vector<Recipe> all_recipes = service.get_all_recipes();
    int number_of_recipes = service.get_number_of_recipes();
//    for (int i=0;i<number_of_recipes;i++)
//        std::cout<<all_recipes[i].recipe_long_string();
    auto print = [](const Recipe& r){std::cout<<r.recipe_long_string()<<"\n";};
    std::for_each(all_recipes.begin(), all_recipes.end(), print);
}

void User_interface::start_user_mode() {
    std::cout<<"Welcome user!\n";
    print_user_menu();
    std::string choice;
    std::cin>>choice;
    if(choice.find_first_not_of("0123456789") == std::string::npos)
    {
        int choice_integer = std::stoi(choice);
        switch(choice_integer){
            case 1:
                see_recipes_by_a_given_chef_user_mode();
                break;
            case 2:
                show_recipes_watch_list();
                break;
            case 3:
                play_watch_list_user_mode();
                break;
            case 4:
                return;
            default:
                std::cout<<"Option does not exist.\n";
        }
    }
    else
        std::cout<<"Option does not exist.\n";
    start_user_mode();
}

void User_interface::print_user_menu() {
    std::cout<<"User menu:\n";
    std::cout<<"1. See recipes by a given chef.\n";
    std::cout<<"2. See watch list.\n";
    std::cout<<"3. Play watch list.\n";
    std::cout<<"4. Exit application.\n";
}

void User_interface::show_recipes_watch_list() {
    std::cout<<"\nAll recipes in the watch list:\n";
//    std::vector<Recipe> all_recipes_in_watch_list = service.get_watchlist_recipes();
//    auto print = [](const Recipe& r){std::cout<<r.recipe_long_string()<<"\n";};
//    std::for_each(all_recipes_in_watch_list.begin(), all_recipes_in_watch_list.end(), print);
    service.save_watch_list_to_file();
    service.show_watch_list_from_file();

}

void User_interface::see_recipes_by_a_given_chef_user_mode() {
    std::cout<<"Choose a chef: ";
    std::string chef;
    std::cin.get();
    std::getline(std::cin, chef);
    std::vector<Recipe> repo_with_chef;
    if (!chef.empty())
        repo_with_chef = service.get_all_recipes_by_chef(chef);
    else {
        repo_with_chef = service.get_all_recipes();
    }
    for (auto r : repo_with_chef)
        //std::cout<<repo_with_chef[i].recipe_long_string();
        if(!play_recipe_for_adding_user_mode(r))
            break;


}

bool User_interface::play_recipe_for_adding_user_mode(Recipe recipe) {
    std::cout<<recipe.recipe_long_string();
    //recipe.play();
    std::string answer;
    do {
        std::cout<<"Do you like the recipe?\n";
        std::getline(std::cin, answer);
    }
    while(answer!="yes" && answer!="no");
    if (answer=="yes")
    {
        do {
            std::cout<<"Would you like to add it to your watch list?\n";
            std::getline(std::cin, answer);
        }
        while(answer!="yes" && answer!="no");
        if (answer == "yes")
            add_recipe_to_watch_list_user_mode(recipe);
    }
    do {
        std::cout<<"\nWould you like to skip to the next recipe?\n";
        std::getline(std::cin, answer);
    }
    while(answer!="yes" && answer!="no");
    if (answer=="yes")
        return true;
    else
        return false;
}

void User_interface::add_recipe_to_watch_list_user_mode(Recipe& recipe) {
    try {
        service.add_recipe_to_watch_list(recipe);
        std::cout << "Recipe added to watch list.\n";
    }
    catch (Validation_exception& ve)
    {
        std::cout<<"Errors occurred:\n";
        std::cout<<ve.get_message()<<"\n";
    }

}



void User_interface::play_watch_list_user_mode() {
    std::cout<<"\nPlaying your watch list:\n";
    for (auto r : service.get_watchlist_recipes())
        play_recipe_watch_list_user_mode(r);
}

void User_interface::play_recipe_watch_list_user_mode(Recipe recipe) {
    std::cout<<recipe.recipe_long_string();
    //recipe.play();
    std::cout<<"You have finished watching this recipe.\n";
    std::cout<<"Would you like to delete the tutorial?\n";
    std::string delete_it="";
    while (delete_it!="no" && delete_it!="yes")
        std::cin>>delete_it;
    if (delete_it=="no")
        return;

    std::cout<<"Would you like to rate the tutorial? (Give it a like)\n";
    std::string answer;
    std::getchar();
    std::getline(std::cin, answer);
    if (answer=="yes")
        modify_database_add_like_to_recipe(recipe);
    service.delete_recipe_from_watch_list(recipe);
    std::cout<<"The recipe has been deleted from your watch list.\n";
}

void User_interface::modify_database_add_like_to_recipe(Recipe &recipe) {
    int new_like_count = recipe.get_number_of_likes()+1;
    service.update_recipe_in_repo(recipe.get_title(), recipe.get_chef(), recipe.get_link(), recipe.get_title(), recipe.get_chef(), recipe.get_link(), recipe.get_duration(), new_like_count);
}

void User_interface::save_all_recipes_to_file_admin_mode() {
    std::cout<<"Saving recipes to file: recipes.txt\n";
    std::ofstream f("C:/Users/Oana/Documents/GitHub/oop-work/a8-9-917-Topan-Oana-Maria/recipes.txt");
    if (!f.is_open())
        return;
    std::vector<Recipe> all = service.get_all_recipes();
    for (auto r: all)
    {
        f<<r;
    }
    std::cout<<"Recipes saved to file.\n";
    f.close();
}

void User_interface::read_recipes_from_file_admin_mode() {
    std::cout<<"Reading recipes from file: recipes.txt\n";
    std::ifstream f("C:/Users/Oana/Documents/GitHub/oop-work/a8-9-917-Topan-Oana-Maria/recipes.txt");
    if (!f.is_open())
        return;
    service.clear_repo_and_watchlist();
    Recipe input{};
    while (f>>input)
    {
        service.add_recipe_to_repo(input.get_title(),input.get_chef(),input.get_link(),input.get_duration(),input.get_number_of_likes());
    }
    std::cout<<"Recipes have been read.\n";
    f.close();
}

