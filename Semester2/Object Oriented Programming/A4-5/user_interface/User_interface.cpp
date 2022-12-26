//
// Created by Oana on 3/20/2022.
//

#include "User_interface.h"
#include <iostream>
#include <string>

User_interface::User_interface(Repository repo){
    service = Service(repo);
    mode = -1;
}

void User_interface::start_application() {
    std::cout << "Welcome to the cooking tutorials \n We have video recipes you can watch.\n";
    service.initialise_repo();
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
    else if (mode == "exit") {std::cout<<"Goodbye!"; return;}
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
    if (number_of_likes_=="" || number_of_likes_.find_first_not_of("0123456789") != std::string::npos)
    {
        std::cout<<"Invalid number of likes.\n";
        return;
    }
    number_of_likes = std::stoi(number_of_likes_);
    if (number_of_likes < 0){
        std::cout<<"Invalid number of likes.\n";
        return;
    }
    std::cout<<"\nDuration:\nMinutes: ";
    std::getline(std::cin, minutes_);
    if (minutes_=="" || minutes_.find_first_not_of("0123456789") != std::string::npos)
    {
        std::cout<<"Invalid minutes.\n";
        return;
    }
    minutes = std::stoi(minutes_);
    if (minutes<0){
        std::cout<<"Invalid minutes.\n";
        return;
    }

    std::cout<<"\nSeconds: ";
    std::getline(std::cin, seconds_);
    if (seconds_=="" || seconds_.find_first_not_of("0123456789") != std::string::npos)
    {
        std::cout<<"Invalid seconds.\n";
        return;
    }
    seconds = std::stoi(seconds_);
    if (seconds<0 || seconds > 59)
    {
        std::cout<<"Invalid seconds.\n";
        return;
    }
    bool check = service.add_recipe_to_repo(title_, chef_, link_, Duration(minutes,seconds), number_of_likes);
    if (check)
        std::cout<<"\nRecipe added successfully.\n";
    else
        std::cout<<"\nError occurred. Recipe is already in the database.\n";
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
    bool check = service.delete_recipe_from_repo(title_, chef_, link_);
    if (check)
        std::cout<<"\nRecipe deleted successfully.\n";
    else
        std::cout<<"\nError occurred. Recipe was not in the database.\n";
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
    if (number_of_likes_final=="" || number_of_likes_final.find_first_not_of("0123456789") != std::string::npos)
    {
        std::cout<<"Invalid number of likes.\n";
        return;
    }
    number_of_likes = std::stoi(number_of_likes_final);
    if (number_of_likes < 0){
        std::cout<<"Invalid number of likes.\n";
        return;
    }
    std::cout<<"\nFinal duration:\nMinutes: ";
    std::getline(std::cin, minutes_final);
    if (minutes_final=="" || minutes_final.find_first_not_of("0123456789") != std::string::npos)
    {
        std::cout<<"Invalid minutes.\n";
        return;
    }
    minutes = std::stoi(minutes_final);
    if (minutes<0){
        std::cout<<"Invalid minutes.\n";
        return;
    }

    std::cout<<"\nSeconds: ";
    std::getline(std::cin, seconds_final);
    if (seconds_final=="" || seconds_final.find_first_not_of("0123456789") != std::string::npos)
    {
        std::cout<<"Invalid seconds.\n";
        return;
    }
    seconds = std::stoi(seconds_final);
    if (seconds<0 || seconds > 59)
    {
        std::cout<<"Invalid seconds.\n";
        return;
    }
    bool check = service.update_recipe_in_repo(title_initial,chef_initial,link_initial, title_final,chef_final,link_final,Duration(minutes, seconds), number_of_likes);
    if (check)
        std::cout<<"\nRecipe updated successfully.\n";
    else
        std::cout<<"\nError occurred. Initial recipe was not in the database.\n";
}

void User_interface::show_recipes_admin_mode() {
    std::cout<<"\nAll recipes in database: \n";
    Recipe* all_recipes = service.get_all_recipes();
    int number_of_recipes = service.get_number_of_recipes();
    for (int i=0;i<number_of_recipes;i++)
        std::cout<<all_recipes[i].recipe_long_string();
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
    Recipe* all_recipes_in_watch_list = service.get_watchlist_recipes();
    int number_of_recipes_in_watch_list = service.get_number_of_recipes_watchlist();
    for(int i=0;i<number_of_recipes_in_watch_list;i++)
        std::cout<<all_recipes_in_watch_list[i].recipe_long_string();
}

void User_interface::see_recipes_by_a_given_chef_user_mode() {
    std::cout<<"Choose a chef: ";
    std::string chef;
    std::cin.get();
    std::getline(std::cin, chef);
    Recipe* repo_with_chef;
    int number_of_recipes;
    if (!chef.empty())
        repo_with_chef = service.get_all_recipes_by_chef(chef, number_of_recipes);
    else {
        repo_with_chef = service.get_all_recipes();
        number_of_recipes = service.get_number_of_recipes();
    }
    for (int i=0;i<number_of_recipes;i++)
        //std::cout<<repo_with_chef[i].recipe_long_string();
        if(play_recipe_for_adding_user_mode(repo_with_chef[i]) == false)
            break;
        else
            if(i==number_of_recipes-1)
                i=-1;

}

bool User_interface::play_recipe_for_adding_user_mode(Recipe recipe) {
    std::cout<<recipe.recipe_long_string();
    recipe.play();
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
    bool check = service.add_recipe_to_watch_list(recipe);
    if (check)
        std::cout<<"Recipe added successfully to the watch list\n";
    else
        std::cout<<"Error occurred. You cannot add the same recipe multiple times.\n";
}



void User_interface::play_watch_list_user_mode() {
    std::cout<<"\nPlaying your watch list:\n";
    while (service.get_number_of_recipes_watchlist() > 0) {
        play_recipe_watch_list_user_mode(service.get_recipe_position_watch_list(0));
    }
}

void User_interface::play_recipe_watch_list_user_mode(Recipe recipe) {
    std::cout<<recipe.recipe_long_string();
    recipe.play();
    std::cout<<"You have finished watching this recipe.\n";
    std::cout<<"The recipe will be deleted from your watch list.\n";
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

