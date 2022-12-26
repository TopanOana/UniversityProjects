//
// Created by Oana on 3/20/2022.
//
#pragma once
#include <string>
#include <windows.h>
#include <iostream>
#include "domain/Duration.h"


class Recipe {
private:
    std::string title;
    std::string chef;
    std::string link;
    int number_of_likes;
    Duration duration;
public:
    ///constructors
    Recipe();
    Recipe(std::string _title, std::string _chef, std::string _link, int _nr_of_likes, Duration _duration);
    Recipe(const Recipe& copy);
    Recipe(std::string _title, std::string _chef, std::string _link);

    ///getters
    std::string get_title() const;
    std::string get_chef() const;
    std::string get_link() const;
    int get_number_of_likes() const;
    Duration get_duration() const;

    ///setters
    void set_title(std::string _title);
    void set_chef(std::string _chef);
    void set_link(std::string _link);
    void set_duration(Duration _duration);
    void set_number_of_likes(int _nr_likes);

    ///equality checker
    bool operator==(const Recipe& t);

    ///to string
    std::string recipe_string() const;
    std::string recipe_long_string() const;

    ///play recipe
    void play() const;

    friend std::istream& operator>>(std::istream& is, Recipe& r);
    friend std::ostream& operator<<(std::ostream& os, const Recipe& r);

};


