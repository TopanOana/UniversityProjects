//
// Created by Oana on 3/6/2022.
//
#pragma once



typedef struct {
    char *name;
    char *continent;
    int population;

}Country;

Country* create_country(char* name, char* continent, int population);

void destroy_country(Country* c);

void modify_population(Country* c,int new_population);

char* get_name(Country* c);

char* get_continent(Country* c);

int get_population(Country* c);

int equal_countries(Country* c1, Country* c2);

char* get_country_string(Country* c);

int comparator_ascending_by_name(Country* c1, Country* c2);

Country* copy_country(Country* c);

void test_all_domain();

int comparator_ascending(Country* c1, Country* c2);

int comparator_descending(Country* c1, Country* c2);




