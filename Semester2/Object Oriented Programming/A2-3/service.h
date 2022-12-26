//
// Created by Oana on 3/6/2022.
//
#pragma once
#include "repository.h"
#include "country.h"
#include "undo_redo.h"

typedef struct{
    CountryRepo* repo;
    UndoRedoRepo* undo_redo_repo;
}CountryService;

CountryService* create_service(CountryRepo* repo);

void destroy_service(CountryService* service);

int add_country_service(CountryService* service, char* name, char* continent, int population);

int delete_country_service(CountryService* service, char* name);

int update_country_service(CountryService* service, char* name, int new_population);

int migration_service(CountryService* service, char* name_of_origin, int population_displaced, char* name_of_destination);

void test_all_service();

void initialise_service_for_main(CountryService* service);

CountryRepo* search_for_countries_with_string(CountryService* service, char* search);

CountryRepo* countries_on_continent_with_population(CountryService* service, char* continent_chosen, int minimum_population);

CountryRepo* countries_with_population(CountryService* service, int minimum_population);

void sort_countries_with_function_pointers(CountryRepo* repo, int sorting_mechanism);

CountryRepo* countries_in_an_interval(CountryService* service, int a, int b);

CountryRepo* countries_sorted_by_name(CountryService* service);

int add_repo_to_history(CountryService* service);

int redo_last_action(CountryService* service);

int undo_last_action(CountryService* service);

CountryRepo* get_repo(CountryService* service);

