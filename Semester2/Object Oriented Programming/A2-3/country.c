//
// Created by Oana on 3/6/2022.
//
#include "country.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

Country* create_country(char* name, char* continent, int population)
{
    Country* c = malloc(sizeof(Country));
    if (c == NULL)
        return NULL;
    c->name = malloc(sizeof(char)*(strlen(name)+1));
    if (c->name != NULL)
        strcpy(c->name, name);
    c->continent= malloc(sizeof(char)*(strlen(continent)+1));
    if (c->continent !=NULL)
        strcpy(c->continent, continent);
    c->population=population;
    return c;
}

void destroy_country(Country *c)
{
    if (c == NULL)
        return;

    free(c->name);
    free(c->continent);
    //free(c);
}

void modify_population(Country *c,int new_population)
{
    if (c==NULL)
        return;
    c->population = new_population;
}

Country* copy_country(Country* c)
{
    Country* new_c = create_country(c->name, c->continent, c->population);
    return new_c;
}

char* get_name(Country *c)
{
    if (c == NULL)
        return NULL;
    return c->name;
}

char* get_continent(Country *c)
{
    if (c == NULL)
        return NULL;
    return c->continent;
}

int get_population(Country *c)
{
    if (c==NULL)
        return -1;
    return c->population;
}

int equal_countries(Country* c1, Country* c2)
{
    if (c1 == NULL || c2 ==NULL)
        return -1;
    if (strcmp(c1->name, c2->name)==0)
        return 1;
    return 0;
}

char* get_country_string(Country* c)
{
    if(c == NULL)
        return "";
    char* string = malloc(250* sizeof(char));
    sprintf(string,"Country: %s on the continent: %s with the population: %d\n", c->name, c->continent, c->population);
    return string;
}

int comparator_ascending(Country* c1, Country* c2)
{
    return get_population(c1)- get_population(c2);
}

int comparator_descending(Country* c1, Country* c2)
{
    return (-1)*(get_population(c1)- get_population(c2));
}


int comparator_ascending_by_name(Country* c1, Country* c2)
{
    return strcmp(c1->name,c2->name);
}


///Tests for the domain

Country* init_domain_for_tests()
{
    Country* c = create_country("Romania", "Europe", 19);
    return c;
}

void test_getters()
{
    Country* c = init_domain_for_tests();
    assert(get_population(c)==c->population);
    assert(strcmp(get_continent(c),"Europe")==0);
    assert(strcmp(get_name(c),"Romania")==0);
    assert(strcmp(get_country_string(c),"Country: Romania on the continent: Europe with the population: 19\n")==0);
    destroy_country(c);
}

void test_setters()
{
    Country* c = init_domain_for_tests();
    assert(get_population(c)==19);
    modify_population(c, 20);
    assert(get_population(c)==20);

    destroy_country(c);
}

void test_equality()
{
    Country* c1 = init_domain_for_tests();
    Country* c2 = init_domain_for_tests();

    assert(equal_countries(c1, c2)==1);

    Country* c3 = create_country("France", "Europe", 80);
    assert(equal_countries(c1,c3)==0);

    destroy_country(c1);
    destroy_country(c2);
    destroy_country(c3);
}

void test_comparators()
{
    Country* c1 = init_domain_for_tests();
    Country* c2 = init_domain_for_tests();

    assert(comparator_descending(c1,c2)==0);
    assert(comparator_ascending(c1,c2)==0);

    Country* c3 = create_country("France", "Europe", 80);
    assert(comparator_ascending(c1,c3)<0);
    assert(comparator_descending(c1,c3)>0);

    assert(comparator_ascending(c3,c1)>0);
    assert(comparator_descending(c3,c1)<0);

    destroy_country(c1);
    destroy_country(c2);
    destroy_country(c3);
}

void test_all_domain()
{
    test_getters();
    test_setters();
    test_equality();
    test_comparators();
}