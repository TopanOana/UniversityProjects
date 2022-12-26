//
// Created by Oana on 3/12/2022.
//

#include "undo_redo.h"
#include <stdlib.h>

UndoRedoRepo* create_undo_redo_repo()
{
    UndoRedoRepo* urr = malloc(sizeof(UndoRedoRepo));
    if (urr==NULL)
        return NULL;
    urr->length=0;
    urr->position=0;
    return urr;
}

void destroy_undo_redo_repo(UndoRedoRepo* undo_redo_repo)
{
    if(undo_redo_repo==NULL)
        return;
    for(int i= undo_redo_repo->length-1;i>=0;i--)
        destroy_repo(&undo_redo_repo->history[i]);
    //free(undo_redo_repo);
}


int add_repo(UndoRedoRepo* undo_redo_repo, CountryRepo* repo)
{
    if (undo_redo_repo==NULL || repo == NULL)
        return -1;
    undo_redo_repo->history[undo_redo_repo->length] = *copy_repo(repo);
    undo_redo_repo->length++;
    return 1;
}

int add_repo_position(UndoRedoRepo* undo_redo_repo, CountryRepo* repo)
{
    if (undo_redo_repo==NULL || repo == NULL)
        return -1;
    undo_redo_repo->position++;
    undo_redo_repo->history[undo_redo_repo->position] = *copy_repo(repo);
    undo_redo_repo->length=undo_redo_repo->position+1;
    return 1;
}

CountryRepo* get_current_repo(UndoRedoRepo* undo_redo_repo)
{
    CountryRepo* result = copy_repo(&undo_redo_repo->history[undo_redo_repo->position]);
    return result;
}
