//
// Created by Oana on 3/12/2022.
//
#pragma once
#include "repository.h"

typedef struct{
    CountryRepo history[100];
    int position;
    int length;
    int capacity;
}UndoRedoRepo;

UndoRedoRepo* create_undo_redo_repo();

void destroy_undo_redo_repo(UndoRedoRepo* undo_redo_repo);

int add_repo(UndoRedoRepo* undo_redo_repo, CountryRepo* repo);

CountryRepo* get_current_repo(UndoRedoRepo* undo_redo_repo);

int add_repo_position(UndoRedoRepo* undo_redo_repo, CountryRepo* repo);



