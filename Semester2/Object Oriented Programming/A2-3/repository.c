//
// Created by Oana on 3/6/2022.
//
#include "repository.h"
#include "country.h"
#include <stdlib.h>
#include <assert.h>
#include <string.h>


/*
 * create_repo()
 * returns/creates a repo pointer
 */
CountryRepo* create_repo(int capacity)
{
    CountryRepo* repo = malloc(sizeof(CountryRepo));
    if (repo == NULL)
        return NULL;
    repo->length = 0;
    repo->capacity = capacity;
    repo->countries = malloc(repo->capacity * sizeof(Country));
    return repo;
}

/*
 * destroy_repo(repo)
 * frees up the memory allocated for the repo
 */
void destroy_repo(CountryRepo* repo)
{
    if (repo == NULL)
        return;
    for (int i=repo->length-1;i>=0;i--)
       destroy_country(&repo->countries[i]);
    free(repo->countries);
    //free(repo);
}

/*
 * resize_repo(repo)
 * resizes the repo given so that its capacity is twice its original size
 */
void resize_repo(CountryRepo* repo)
{
    if (repo == NULL)
        return;
    repo->capacity *=2;
    Country* aux = realloc(repo->countries, repo->capacity* sizeof(Country));
    repo->countries = aux;
}



/*
 * find_country(repo, c)
 * returns 1 if the country has been found in the repo
 * returns 0 otherwise
 * returns -1 if the repo is NULL
 */
int find_country(CountryRepo* repo, Country* c)
{
    if (repo == NULL)
        return -1;
    for (int i=0;i<repo->length;i++)
        if (equal_countries(c, &repo->countries[i])==1)
            return 1;
    return 0;
}

/*
 * add_country(repo, c);
 * returns 1 if the country was added successfully
 * returns 0 if the country was already in the repo
 */
int add_country(CountryRepo* repo, Country* c)
{
    if (repo == NULL)
        return -1;
    if (find_country(repo,c)==1)
        return 0;
    if (repo->length==repo->capacity)
        resize_repo(repo);
    repo->countries[repo->length] = *c;
    repo->length++;
    return 1;
}

/*
 * get_position(repo, c)
 * returns the position on which the country c is
 * returns -1 if the country is not in the repository/the repo is null
 */
int get_position(CountryRepo* repo, Country* c)
{
    if (repo==NULL)
        return -1;
    for (int i=0;i<repo->length;i++)
        if (strcmp(c->name, repo->countries[i].name)==0)
            return i;
    return -1;
}

/*
 * delete_country(repo, c)
 * returns 1 if the country was deleted successfully
 * returns 0 if the country didn't exist in the repo
 */
int delete_country(CountryRepo* repo, Country* c)
{
    if (find_country(repo,c)==0)
        return 0;
    int position = get_position(repo, c);
    destroy_country(&repo->countries[position]);
    repo->countries[position] = repo->countries[repo->length-1];
    repo->length-=1;
    return 1;
}

/*
 * update_country(repo, c)
 * returns 1 if the country was updated successfully
 * returns 0 if the country didn't exist in the repo or the new population is <0
 */
int update_country(CountryRepo* repo, Country* c, int new_population)
{
    if (repo == NULL)
        return -1;
    if (find_country(repo,c)==0)
        return 0;
    if (new_population<0)
        return 0;
    int position = get_position(repo, c);
    modify_population(&repo->countries[position], new_population);
    return 1;
}

int get_length(CountryRepo* repo)
{
    if (repo == NULL)
        return -1;
    return repo->length;
}

Country* get_country_with_index(CountryRepo* repo, int index)
{
    if (repo == NULL)
        return NULL;
    if (index<0 || index>=get_length(repo))
        return NULL;
    return &repo->countries[index];
}

/*
 * copy_repo(repo)
 * returns an address to the copy of the repo given
 */
CountryRepo* copy_repo(CountryRepo* repo)
{
    if(repo==NULL)
        return NULL;
    CountryRepo* new_repo = create_repo(repo->capacity);

    for(int i=0;i<repo->length;i++)
        add_country(new_repo, copy_country(&repo->countries[i]));

    return new_repo;
}



/*
 * sort_repo_with_function_pointers(repo, function_pointer)
 * sorts inefficiently the repo using the function that is pointed to with the function_pointer
 */
void sort_repo_with_function_pointers(CountryRepo* repo, int (*function_pointer)(Country*, Country*))
{
    if (repo == NULL)
        return;
    int length= get_length(repo);
    for(int i=0;i<length-1;i++)
        for(int j=i+1;j<length;j++)
            if (function_pointer(get_country_with_index(repo, i), get_country_with_index(repo, j))>0)
            {
                Country aux = *get_country_with_index(repo, i);
                repo->countries[i] = *get_country_with_index(repo, j);
                repo->countries[j] = aux;
            }
}


///Tests for repo:

void init_countryRepo_for_tests(CountryRepo* repo)
{
    Country* c = create_country("Romania", "Europe", 19);
    add_country(repo, c);
}

void test_add_country()
{
    CountryRepo* repo = create_repo(5);
    init_countryRepo_for_tests(repo);
    assert(get_length(repo)==1);

    Country* c = create_country("Italy", "Europe", 60);
    assert(add_country(repo, c)==1);
    assert(get_length(repo)==2);

    assert(add_country(repo, c)==0);

    destroy_repo(repo);
}

void test_delete_country()
{
    CountryRepo* repo = create_repo(5);
    init_countryRepo_for_tests(repo);
    assert(get_length(repo)==1);

    Country* c = create_country("Italy", "Europe", 60);
    assert(add_country(repo, c)==1);

    assert(delete_country(repo, c)==1);
    assert(get_length(repo)==1);

    assert(delete_country(repo, c)==0);

    destroy_repo(repo);
}


void test_update_country()
{
    CountryRepo* repo = create_repo(5);
    init_countryRepo_for_tests(repo);
    assert(get_length(repo)==1);

    Country* c = create_country("Italy", "Europe", 60);
    assert(add_country(repo, c)==1);

    int new_population=50;
    assert(update_country(repo, c, new_population)==1);
    assert(get_population(&repo->countries[1]) == new_population);

    destroy_repo(repo);
}

void test_sort_repo_with_function_pointers()
{
    CountryRepo* repo = create_repo(5);
    init_countryRepo_for_tests(repo);
    Country* c1 = create_country("Italy", "Europe", 60);
    Country* c2 = create_country("France", "Europe", 80);

    add_country(repo, c1);
    add_country(repo, c2);

    assert(get_length(repo)==3);

    sort_repo_with_function_pointers(repo, comparator_descending);
    assert(equal_countries(get_country_with_index(repo, 0), c2)==1);

    sort_repo_with_function_pointers(repo, comparator_ascending);
    assert(strcmp(get_name(get_country_with_index(repo,0)), "Romania")==0);

    destroy_repo(repo);

}

void test_all_repo()
{
    test_add_country();
    test_delete_country();
    test_update_country();
    test_sort_repo_with_function_pointers();
}