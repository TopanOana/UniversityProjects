//
// Created by Oana on 3/6/2022.
//

#include "service.h"
#include "repository.h"
#include "country.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <crtdbg.h>

void print_main_menu()
{
    printf("1. Work on repository.\n");
    printf("2. Filter countries using certain conditions.\n");
    printf("3. Display countries on a certain continent with a minimum population(sorted).\n");
    printf("4. Undo/Redo.\n");
    printf("5. Exit application.\n");
    printf("6. Run tests.\n");
}

void print_repo_menu()
{
    printf("1. Add a country.\n");
    printf("2. Delete a country.\n");
    printf("3. Update a country.\n");
    printf("4. Migration from one country to another.\n");
    printf("5. Return to main menu.\n");
}

void add_a_country(CountryService* service)
{
    printf("\n");
    printf("Country name:");
    char *name = malloc(20*sizeof(char));
    getchar();
    gets(name);
    printf("\nContinent: ");
    char *continent = malloc(20* sizeof(char));
    getchar();
    gets(continent);
    printf("\nPopulation: ");
    int population = 0;
    scanf_s("%d", &population);
    int result = add_country_service(service, name, continent, population);
    if (result != 1)
        printf("An error has occurred. The country has already been added or the population<0.\n");
    else add_repo_to_history(service);

}

void delete_a_country(CountryService* service)
{
    printf("\n");
    printf("Country name:");
    char *name = malloc(100*sizeof(char));
    scanf_s("%s", name);
    int result = delete_country_service(service, name);
    if (result!=1)
        printf("An error has occurred. The country isn't in the system.\n");
    else
        add_repo_to_history(service);
}

void update_a_country(CountryService* service)
{
    printf("\n");
    printf("Country name:");
    char *name = malloc(100*sizeof(char));
    scanf_s("%s", name);
    int new_population;
    printf("New population:");
    scanf_s("%d", &new_population);
    int result = update_country_service(service, name, new_population);
    if (result!=1)
        printf("An error has occurred. The country isn't in the system or the new population is invalid\n");
    else
        add_repo_to_history(service);
}

void migration_of_populations(CountryService* service)
{
    printf("\n");
    printf("Country of origin name:");
    char *name_of_origin = malloc(100*sizeof(char));
    scanf_s("%s", name_of_origin);
    printf("\n");
    printf("Country of destination name:");
    char *name_of_destination = malloc(100*sizeof(char));
    scanf_s("%s", name_of_destination);
    int population_displaced;
    printf("Population displaced:");
    scanf_s("%d", &population_displaced);
    int result = migration_service(service,name_of_origin, population_displaced, name_of_destination);
    if (result!=1)
        printf("An error has occurred. At least one of the countries is not in the system or the new population is invalid\n");
    else
        add_repo_to_history(service);
}


void repo_menu(CountryService* service)
{
    print_repo_menu();
    printf("Please choose an option:");
    int option=0;
    scanf_s("%d", &option);
    if (option==1)
        add_a_country(service);
    else
        if (option==2)
            delete_a_country(service);
        else
            if(option==3)
                update_a_country(service);
            else
                if(option==4)
                    migration_of_populations(service);
                else
                    if (option==5)
                        return;
                    else
                        printf("Option is unavailable\n");

}

void list_all_countries(CountryService* service)
{
    for (int i=0;i<service->repo->length;i++)
        printf("%s", get_country_string(&service->repo->countries[i]));
}


void run_all_tests()
{
    printf("Starting tests...\n");
    test_all_domain();
    test_all_repo();
    test_all_service();
    printf("Tests finished\n");

}

void print_country_repo(CountryRepo* repo)
{
    int length = get_length(repo);
    for(int i=0;i<length;i++)
        printf("%s", get_country_string(get_country_with_index(repo, i)));
}

void print_display_countries_menu()
{
    printf("1. Filter countries with a string.\n");
    printf("2. Display countries with a population in an interval.\n");
}

void filter_countries_with_interval(CountryService* service)
{
    int a,b;
    printf("a: ");
    scanf_s("%d", &a);
    printf("b: ");
    scanf_s("%d", &b);
    if (a>b)
    {
        printf("Invalid interval.\n");
        return;
    }
    CountryRepo* result = countries_in_an_interval(service,a,b);
    print_country_repo(result);
}


void filter_countries_with_string(CountryService* service)
{
    char* search = malloc(20* sizeof(char));
    printf("Search for countries with the string: ");
    getchar();
    gets(search);
    if (strcmp(search, "")==0)
        print_country_repo(service->repo);
    else
    {
        CountryRepo* result = search_for_countries_with_string(service, search);
        print_country_repo(result);
    }
}

void display_countries(CountryService* service)
{
    print_display_countries_menu();
    printf("Choose option: ");
    int option;
    scanf_s("%d", &option);
    if(option==1)
        filter_countries_with_string(service);
    else
        if(option==2)
            filter_countries_with_interval(service);
        else
            printf("Option unavailable.\n");

}

void print_sorting_menu()
{
    printf("1. Descending order by population.\n");
    printf("2. Ascending order by population.\n");
}

void print_continents()
{
    printf("1. Europe\n");
    printf("2. Asia\n");
    printf("3. America\n");
    printf("4. Africa\n");
    printf("5. Australia\n");
}

void sorting_section(CountryService* service)
{

    print_sorting_menu();
    printf("Choose an option (1 or 2):");
    int option_sorting;
    scanf_s("%d", &option_sorting);
    printf("Continent: ");
    char* continent_chosen = malloc(20* sizeof(char));
    getchar();
    gets(continent_chosen);
    if (option_sorting<1 || option_sorting>2)
    {
        printf("Option for sorting hasn't been correctly selected.\n");
    }
    option_sorting-=1;
    int minimum_population;
    printf("Minimum population:");
    scanf_s("%d", &minimum_population);
    CountryRepo* result = malloc(sizeof(CountryRepo));
    if (continent_chosen == NULL)
        return;
    if (strcmp(continent_chosen,"")==0)
        result = countries_with_population(service,minimum_population);
    else
        result = countries_on_continent_with_population(service,continent_chosen,minimum_population);
    sort_countries_with_function_pointers(result, option_sorting);
    print_country_repo(result);

}

void undo_main(CountryService* service)
{
    int undo = undo_last_action(service);
    if (undo != 1)
        printf("Undo not possible.\n");
    else
        printf("Undo success\n");
}

void redo_main(CountryService* service)
{
    int redo = redo_last_action(service);
    if (redo != 1)
        printf("Redo not possible.\n");
    else
        printf("REdo success\n");
}


void undo_redo_select(CountryService* service)
{
    printf("Choose an option: 1 undo, 2 redo: ");
    int option;
    scanf("%d", &option);
    if (option == 1)
        undo_main(service);
    else
        if (option == 2)
            redo_main(service);
}


int main() {
    _CrtSetReportMode(_CRT_WARN, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_WARN, _CRTDBG_FILE_STDOUT);
    _CrtSetReportMode(_CRT_ERROR, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_ERROR, _CRTDBG_FILE_STDOUT);
    _CrtSetReportMode(_CRT_ASSERT, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_ASSERT, _CRTDBG_FILE_STDOUT);
    printf("Welcome to the World Population Monitoring!\n");
    CountryRepo* repo = create_repo(20);
    CountryService* service = create_service(repo);
    initialise_service_for_main(service);
    add_repo(service->undo_redo_repo, service->repo);
    while (1)
    {
        printf("\n");
        print_main_menu();
        int option;
        printf("Please choose an option: ");
        scanf_s("%d", &option);
        printf("\n");
        if (option == 5)
            break;
        else
            if (option == 1)
                repo_menu(service);
            else
                if (option == 2)
                    display_countries(service);
                else
                    if (option == 6)
                        run_all_tests();
                    else
                        if (option == 3)
                            sorting_section(service);
                        else
                            if (option == 4)
                                undo_redo_select(service);
                            else 
                                printf("Option is not available.\n");
    }

    destroy_service(service);
    printf("%d",_CrtDumpMemoryLeaks());
    return 0;
}