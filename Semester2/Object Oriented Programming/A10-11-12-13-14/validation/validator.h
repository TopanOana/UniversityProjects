////
//// Created by Oana on 4/12/2022.
////
#pragma once
#include <string>
#include "../domain/Recipe.h"
#include "../repository/Repository.h"

class Validation_exception{
private:
    std::string message;
public:
    Validation_exception(std::string _message);
    std::string get_message() const;

};


class Recipe_validator{
public:
    static void validate(const Recipe& r);
};

class Repo_validator{
public:
    static void validate_add(const Repository& repo, const Recipe& r);
    static void validate_delete(const Repository& repo, const Recipe& r);
    static void validate_update(const Repository& repo, const Recipe& init);
};

