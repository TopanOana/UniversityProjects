////
//// Created by Oana on 4/12/2022.
////

#include <algorithm>
#include "validator.h"


Validation_exception::Validation_exception(std::string _message): message{_message}
{
}

std::string Validation_exception::get_message() const
{
    return this->message;
}

void Recipe_validator::validate(const Recipe &r) {
    std::string errors;
    if (r.get_title().size()<3)
        errors+="Title is too short.\n";
    if (r.get_chef().size()<3)
        errors+="Chef's name is too short.\n";
    if (r.get_duration().get_minutes()<0)
        errors+="Minutes are negative.\n";
    if (r.get_duration().get_seconds()<0)
        errors+="Seconds are negative.\n";
    if (r.get_duration().get_seconds()>59)
        errors+="Seconds incompatible.\n";
    if (r.get_number_of_likes()<0)
        errors+="Likes are negative.\n";

    if(errors.size()>0)
        throw Validation_exception(errors);

}

void Repo_validator::validate_add(const Repository &repo, const Recipe &r) {
    std::vector<Recipe> arr = repo.get_all_recipes();
    auto position = std::find(arr.begin(), arr.end(), r);
    std::string errors;
    if (position!=arr.end())
        errors+="Recipe already exists in the repository.\n";
    if (!errors.empty())
        throw Validation_exception(errors);

}

void Repo_validator::validate_delete(const Repository &repo, const Recipe &r) {
    std::vector<Recipe> arr = repo.get_all_recipes();
    auto position = std::find(arr.begin(), arr.end(), r);
    std::string errors;
    if (position==arr.end())
        errors+="Recipe doesn't exist in the repository.\n";
    if (!errors.empty())
        throw Validation_exception(errors);
}

void Repo_validator::validate_update(const Repository &repo,  const Recipe& init) {
    std::vector<Recipe> arr = repo.get_all_recipes();
    auto position = std::find(arr.begin(), arr.end(), init);
    std::string errors;
    if (position==arr.end())
        errors+="Recipe doesn't exist in the repository.\n";
    if (!errors.empty())
        throw Validation_exception(errors);
}
