//
// Created by Oana on 3/6/2022.
//

#pragma once
#include "country.h"


typedef struct{
    Country* countries;
    int length;
    int capacity;
}CountryRepo;

CountryRepo* create_repo();

void destroy_repo(CountryRepo* repo);

int add_country(CountryRepo* repo, Country* c);

int delete_country(CountryRepo* repo, Country* c);

int update_country(CountryRepo* repo, Country* c, int new_population);

int get_length(CountryRepo* repo);

int find_country(CountryRepo* repo, Country* c);

int get_position(CountryRepo* repo, Country* c);

void test_all_repo();

Country* get_country_with_index(CountryRepo* repo, int index);

void sort_repo_with_function_pointers(CountryRepo* repo, int (*function_pointer)(Country*, Country*));

CountryRepo* copy_repo(CountryRepo* repo);

