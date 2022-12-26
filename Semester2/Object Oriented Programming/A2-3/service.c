//
// Created by Oana on 3/6/2022.
//
#include "service.h"
#include "repository.h"
#include "country.h"
#include "undo_redo.h"
#include <stdlib.h>
#include <assert.h>
#include <string.h>

/*
 * create_service(repo)
 * returns the newly created service
 */
CountryService* create_service(CountryRepo* repo)
{
    CountryService* service = malloc(sizeof(CountryService));
    if (service==NULL)
        return NULL;
    service->repo = repo;
    service->undo_redo_repo = create_undo_redo_repo();
    return service;
}

/*
 * destroy_service(service)
 * frees up the memory allocated for the service
 */
void destroy_service(CountryService* service)
{
    if (service==NULL)
        return;
    destroy_undo_redo_repo(service->undo_redo_repo);
    destroy_repo(service->repo);
    free(service);
}

/*
 * add_country_service(service, name, continent, population)
 * returns 1 if the country was added successfully to the repo
 * returns 0 otherwise
 */
int add_country_service(CountryService* service, char* name, char* continent, int population)
{
    Country* c = create_country(name, continent, population);
    if (population<0)
        return 0;
    int result = add_country(service->repo, c);
    if (result==0)
        destroy_country(c);
    return result;
}

/*
 * delete_country_service(service, name)
 * returns 1 if the country was successfully deleted from the repo
 * returns 0 otherwise
 */
int delete_country_service(CountryService* service, char* name)
{
    Country* c = create_country(name,"",0);
    int result = delete_country(service->repo, c);
    destroy_country(c);
    return result;
}

/*
 * update_country_service(service, name, new_population)
 * returns 1 if the country was updated
 * returns 0 otherwise
 */
int update_country_service(CountryService* service, char* name, int new_population)
{
    Country* c = create_country(name, "", new_population);
    int result = update_country(service->repo, c, new_population);
    return result;
}

/*
 * migration_service(service, name_of_origin, population_displaced, name_of_destination)
 * returns 1 if the migration was successful
 * returns 0 otherwise
 */
int migration_service(CountryService* service, char* name_of_origin, int population_displaced, char* name_of_destination)
{
    Country* c_origin = create_country(name_of_origin,"",0);
    Country* c_destination = create_country(name_of_destination, "", 0);
    if (find_country(service->repo, c_origin)!=1 || find_country(service->repo, c_destination)!=1)
        return 0;
    int position_origin = get_position(service->repo, c_origin);
    int position_destination = get_position(service->repo, c_destination);
    int new_population_origin = get_population(&service->repo->countries[position_origin])-population_displaced;
    int new_population_destination = get_population(&service->repo->countries[position_destination])+population_displaced;
    int origin_result = update_country(service->repo, c_origin, new_population_origin);
    int destination_result = update_country(service->repo, c_destination, new_population_destination);
    if (origin_result==1 && destination_result==1)
        return 1;
    return 0;
}


void initialise_service_for_main(CountryService* service)
{
    add_country_service(service,"Romania", "Europe", 19);
    add_country_service(service,"Canada", "America", 30);
    add_country_service(service,"Japan", "Asia", 60);
    add_country_service(service,"Brasil", "America", 40);
    add_country_service(service,"France", "Europe", 80);
    add_country_service(service,"Egypt", "Africa", 45);
    add_country_service(service,"Australia", "Australia", 25);
    add_country_service(service,"New Zealand", "Australia", 15);
    add_country_service(service,"Vietnam", "Asia", 35);
    add_country_service(service,"Germany", "Europe", 40);
}

/*
 *  search_for_countries_with_string(service, search)
 *  returns a new countryrepo that has all the countries whose name contains the string in search
 */
CountryRepo* search_for_countries_with_string(CountryService* service, char* search)
{
    CountryRepo* result = create_repo(service->repo->length);
    for (int i=0;i< get_length(service->repo);i++)
    {
        if (strstr(get_name(get_country_with_index(service->repo, i)), search)!=NULL)
            add_country(result, copy_country(get_country_with_index(service->repo, i)));
            //add_country(result, get_country_with_index(service->repo, i));
    }
    return result;
}

/*
 * countries_on_continent_with_population(service, continent_chosen, minimum_population)
 * input:
 * returns a countryrepo with all the countries from a continent with the minimum population specified
 */
CountryRepo* countries_on_continent_with_population(CountryService* service, char* continent_chosen, int minimum_population)
{
    CountryRepo* result = create_repo(service->repo->length);
    int length = get_length(service->repo);
    for(int i=0;i<length;i++)
    {
        Country ci = *get_country_with_index(service->repo, i);
        if (strcmp(get_continent(&ci),continent_chosen)==0 && get_population(&ci)>minimum_population)
            add_country(result, copy_country(&ci));
            //add_country(result, &ci);
    }
    return result;
}

/*
 * countries_with_population(service, minimum population)
 * returns a repo with all the countries with a population greater than the minimum population specified
 */
CountryRepo* countries_with_population(CountryService* service, int minimum_population)
{
    CountryRepo* result = create_repo(service->repo->length);
    int length = get_length(service->repo);
    for(int i=0;i<length;i++)
    {
        Country* ci = get_country_with_index(service->repo, i);
        if(get_population(ci)>minimum_population)
            add_country(result, copy_country(ci));
            //add_country(result, &ci);
    }
    return result;
}

/*
 * sort_countries_with_function_pointers(repo, sorting_mechanism)
 * sorts the given repo in ascending//descending order
 */
void sort_countries_with_function_pointers(CountryRepo* repo, int sorting_mechanism)
{
    if (sorting_mechanism==1)
        sort_repo_with_function_pointers(repo,comparator_ascending);
       // qsort(repo, repo->length, sizeof(Country), comparator_ascending);
    else
        sort_repo_with_function_pointers(repo, comparator_descending);
        // qsort(repo, repo->length, sizeof(Country), *comparator_descending);
}

/*
 * countries_in_an_interval(service, a,b)
 * returns a repo with all the countries a population in the interval (a,b)
 */
CountryRepo* countries_in_an_interval(CountryService* service, int a, int b)
{
    CountryRepo* result = create_repo(service->repo->length);
    int length= get_length(service->repo);
    for(int i=0;i<length;i++)
    {
        Country* c= get_country_with_index(service->repo,i);
        if(get_population(c)>a && get_population(c)<b)
            add_country(result, copy_country(c));
            //add_country(result, &c);
    }
    return result;
}

CountryRepo* countries_sorted_by_name(CountryService* service)
{
    CountryRepo* result = countries_with_population(service, 0);
    sort_repo_with_function_pointers(result, comparator_ascending_by_name);
    return result;
}

CountryRepo* get_repo(CountryService* service)
{
    return service->repo;
}


int undo_last_action(CountryService* service)
{
    if (service==NULL)
        return -1;
    if (service->undo_redo_repo->position==0)
        return -1;
    service->undo_redo_repo->position--;
    service->repo = get_current_repo(service->undo_redo_repo);
    return 1;
}

int redo_last_action(CountryService* service)
{
    if (service==NULL)
        return -1;
    if (service->undo_redo_repo->position==service->undo_redo_repo->length-1)
        return -1;
    service->undo_redo_repo->position++;
    service->repo = get_current_repo(service->undo_redo_repo);
    return 1;
}

int add_repo_to_history(CountryService* service)
{
    if(service==NULL)
        return -1;
    add_repo_position(service->undo_redo_repo, service->repo);
    return 1;
}

///Tests for service
void init_service_for_tests(CountryService* service)
{
    add_country_service(service, "Romania", "Europe", 19);
}


void test_add_country_service()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(get_length(service->repo)==2);

    assert(add_country_service(service, "Italy", "Europe", 60)==0);

    assert(add_country_service(service, "France", "Europe", -20)==0);

    destroy_service(service);
}

void test_delete_country_service()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(get_length(service->repo)==2);

    assert(delete_country_service(service, "Italy")==1);
    assert(get_length(service->repo)==1);

    assert(delete_country_service(service, "Italy")==0);

    destroy_service(service);
}

void test_update_country_service()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(get_length(service->repo)==2);

    assert(update_country_service(service, "Italy", 70)==1);
    assert(update_country_service(service, "Romania", -20)==0);
    assert(update_country_service(service, "France", 70)==0);

    destroy_service(service);
}

void test_migration_service()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(get_length(service->repo)==2);

    assert(migration_service(service, "Romania", 15, "Italy")==1);
    assert(migration_service(service, "France", 20, "England")==0);
    assert(migration_service(service, "Romania", 20, "Italy")==0);

    destroy_service(service);
}

void test_search_for_countries_with_string()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(get_length(service->repo)==2);

    char* search = malloc(5* sizeof(char));
    strcpy(search, "a");
    CountryRepo* result1 = search_for_countries_with_string(service, search);
    assert(get_length(result1)==2);
    strcpy(search, "q");
    CountryRepo* result2 = search_for_countries_with_string(service, search);
    assert(get_length(result2)==0);
    free(search);
    destroy_service(service);
}

void test_countries_on_continent_with_population()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(add_country_service(service, "Egypt", "Africa", 50)==1);

    CountryRepo* result = countries_on_continent_with_population(service,"Europe", 15);
    assert(get_length(result)==2);
    assert(strcmp(get_name(get_country_with_index(result, 1)),"Italy")==0);

    destroy_service(service);

}

void test_countries_with_population()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(add_country_service(service, "Egypt", "Africa", 50)==1);

    CountryRepo* result = countries_with_population(service, 20);
    assert(get_length(result)==2);
    assert(strcmp(get_name(get_country_with_index(result, 0)),"Italy")==0);
    assert(strcmp(get_name(get_country_with_index(result, 1)),"Egypt")==0);

    destroy_service(service);
}

void test_sort_countries_with_function_pointers()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(add_country_service(service, "Egypt", "Africa", 50)==1);

    CountryRepo* result = countries_with_population(service, 20);
    assert(get_length(result)==2);
    assert(strcmp(get_name(get_country_with_index(result, 0)),"Italy")==0);
    assert(strcmp(get_name(get_country_with_index(result, 1)),"Egypt")==0);

    sort_countries_with_function_pointers(result, 1);
    assert(strcmp(get_name(get_country_with_index(result, 1)),"Italy")==0);
    assert(strcmp(get_name(get_country_with_index(result, 0)),"Egypt")==0);

    destroy_service(service);

}

void test_countries_in_an_interval()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);

    assert(get_length(service->repo)==1);

    assert(add_country_service(service, "Italy", "Europe", 60)==1);
    assert(add_country_service(service, "Egypt", "Africa", 50)==1);

    CountryRepo* result = countries_in_an_interval(service, 25, 75);
    assert(get_length(result)==2);
    assert(strcmp(get_name(get_country_with_index(result, 0)),"Italy")==0);
    assert(strcmp(get_name(get_country_with_index(result, 1)),"Egypt")==0);

    destroy_service(service);

}

void test_undo_redo()
{
    CountryRepo* repo = create_repo(5);
    CountryService* service = create_service(repo);
    init_service_for_tests(service);
    add_repo(service->undo_redo_repo, service->repo);

    int undo = undo_last_action(service);
    assert(undo!=1);
    int redo = redo_last_action(service);
    assert(redo!=1);

    add_country_service(service, "Moldova", "Europe", 5);
    add_repo_to_history(service);

    undo = undo_last_action(service);
    assert(undo==1);
    redo = redo_last_action(service);
    assert(redo==1);

    destroy_service(service);
}

void test_all_service()
{
    test_add_country_service();
    test_delete_country_service();
    test_update_country_service();
    test_migration_service();
    test_search_for_countries_with_string();
    test_countries_on_continent_with_population();
    test_countries_with_population();
    test_sort_countries_with_function_pointers();
    test_countries_in_an_interval();
    test_undo_redo();
}