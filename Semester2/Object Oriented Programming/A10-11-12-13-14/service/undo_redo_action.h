//
// Created by Oana on 5/19/2022.
//

#pragma once


#include "../domain/Recipe.h"
#include "../repository/Repository.h"

class abstract_undo{
public:
    virtual void execute_undo() = 0;
    virtual void execute_redo() = 0;
};

class undo_add : public abstract_undo{
private:
    Recipe recipe;
    Repository* repo;

public:
    undo_add(Recipe r, Repository* rp){recipe=r;repo=rp;}

    undo_add(){recipe=Recipe();repo= nullptr;}

    void execute_undo() override {
        repo->delete_recipe(recipe);
    }

    void execute_redo() override {
        repo->add_recipe(recipe);
    }

};

class undo_delete : public abstract_undo{
private:
    Recipe recipe;
    Repository* repo;

public:
    undo_delete(Recipe r, Repository* rp){recipe=r;repo=rp;}

    undo_delete(){recipe=Recipe();repo= nullptr;}

    void execute_undo() override {
        repo->add_recipe(recipe);
    }

    void execute_redo() override {
        repo->delete_recipe(recipe);
    }

};

class undo_update : public abstract_undo{
private:
    Recipe recipe_new, recipe_old;
    Repository* repo;

public:
    undo_update(Recipe ro, Recipe rn, Repository* rp){recipe_new=rn;recipe_old=ro;repo=rp;}

    undo_update(){recipe_old=Recipe();recipe_new=Recipe();repo=nullptr;}

    void execute_undo() override {
        repo->update_recipe(recipe_new,recipe_old);
    }

    void execute_redo() override {
        repo->update_recipe(recipe_old, recipe_new);
    }

};