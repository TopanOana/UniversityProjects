//
// Created by Oana on 3/20/2022.
//

#include "Recipe.h"
#include <string>
#include <vector>
#include <sstream>

Recipe::Recipe(std::string _title, std::string _chef, std::string _link, int _nr_of_likes,
               Duration _duration) {

    title=_title;
    chef= _chef;
    link = _link;
    number_of_likes = _nr_of_likes;
    duration = _duration;
}

Recipe::Recipe() {
    title="";
    chef="";
    duration= Duration();
    link = "";
    number_of_likes=0;
}

Recipe::Recipe(const Recipe &copy) {
    this->title = copy.title;
    this->chef= copy.chef;
    this->link = copy.link;
    this->number_of_likes = copy.number_of_likes;
    this->duration = copy.duration;
}

std::string Recipe::get_title() const {
    return this->title;
}

std::string Recipe::get_chef() const {
    return this->chef;
}

std::string Recipe::get_link() const {
    return this->link;
}

int Recipe::get_number_of_likes() const {
    return this->number_of_likes;
}

Duration Recipe::get_duration() const {
    return this->duration;
}

void Recipe::set_chef(std::string _chef) {
    this->chef = _chef;
}

void Recipe::set_link(std::string _link) {
    this->link  = _link;
}

void Recipe::set_title(std::string _title) {
    this->title = _title;
}

void Recipe::set_duration(Duration _duration) {
    this->duration = _duration;
}

void Recipe::set_number_of_likes(int _nr_likes) {
    this->number_of_likes = _nr_likes;
}

bool Recipe::operator==(const Recipe &t) {
    if (title != t.title || t.chef != chef || t.link != link)
        return false;
    return true;
}

Recipe::Recipe(std::string _title, std::string _chef, std::string _link) {
    title = _title;
    chef = _chef;
    link = _link;
    duration = Duration();
    number_of_likes = 0;
}

std::string Recipe::recipe_string() const{
    std::string recipe = "Title: " + get_title() + " by chef " + get_chef() + " with " + std::to_string(number_of_likes) + " likes.\n";
    return recipe;
}

std::string Recipe::recipe_long_string() const {
    std::string recipe = "Title: "+ get_title() + " by chef " +get_chef() + ". Duration: "+std::to_string(duration.get_minutes())+":"+std::to_string(duration.get_seconds())+" with "+std::to_string(number_of_likes)+" likes.\n + link("+link+")\n";
    return recipe;
}

void Recipe::play() const {
    ShellExecuteA(NULL, NULL, "chrome.exe", this->get_link().c_str(), NULL, SW_SHOWMAXIMIZED);
}

/*
	Tokenizes a string.
	Input:	str - the string to be tokenized.
			delimiter - char - the delimiter used for tokenization
	Output: a vector of strings, containing the tokens
*/
std::vector<std::string> tokenize(std::string str, char delimiter)
{
    std::vector <std::string> result;
    std::stringstream ss(str);
    std::string token;
    while (getline(ss, token, delimiter))
        result.push_back(token);

    return result;
}


std::istream &operator>>(std::istream &is, Recipe &r) {
    std::string line;
    getline(is, line);

    std::vector<std::string> tokens = tokenize(line, ',');
    if (tokens.size() != 6) // make sure all the starship data was valid
        return is;
    r.title = tokens[0];
    r.chef = tokens[1];
    r.link = tokens[2];
    int minutes,seconds,likes;
    minutes = std::stoi(tokens[3]);
    seconds = std::stoi(tokens[4]);
    likes = std::stoi(tokens[5]);
    r.duration=Duration(minutes, seconds);
    r.number_of_likes = likes;
    return is;
}

std::ostream &operator<<(std::ostream &os, const Recipe &r) {
    os << r.title <<","<<r.chef<<","<<r.link<<","<<r.duration.get_minutes()<<","<<r.duration.get_seconds()<<","<<r.number_of_likes<<"\n";
    return os;
}





