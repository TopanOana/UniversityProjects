////
//// Created by Oana on 3/20/2022.
////
//
#include "Tests.h"
#include <cassert>
#include <iostream>
#include "validation/validator.h"

void Test_Recipe::test_constructor_empty() {
    Recipe r;
    assert(r.get_title() == "");
    assert(r.get_chef() == "");
    assert(r.get_link() == "");
    assert(r.get_number_of_likes()==0);
}

void Test_Recipe::test_constructor_full() {
    Recipe r = Recipe("A", "B", "C", 15, Duration(10, 0));
    assert(r.get_title() == "A");
    assert(r.get_chef() == "B");
    assert(r.get_link() == "C");
    assert(r.get_number_of_likes()==15);
    assert(r.get_duration().get_minutes()==10);
    assert(r.get_duration().get_seconds()==0);
}

void Test_Recipe::test_copy_constructor() {
    Recipe a = Recipe("A", "B", "C", 15, Duration(10, 0));
    Recipe r = a;
    assert(r.get_title() == "A");
    assert(r.get_chef() == "B");
    assert(r.get_link() == "C");
    assert(r.get_number_of_likes()==15);
    assert(r.get_duration().get_minutes()==10);
    assert(r.get_duration().get_seconds()==0);
}

void Test_Recipe::test_partial_constructor() {
    Recipe r = Recipe("A", "B", "C");
    assert(r.get_title() == "A");
    assert(r.get_chef() == "B");
    assert(r.get_link() == "C");
    assert(r.get_number_of_likes()==0);
}

void Test_Recipe::test_set_title() {
    Recipe r = Recipe("A", "B", "C");
    assert(r.get_title() == "A");
    r.set_title("B");
    assert(r.get_title()=="B");
}

void Test_Recipe::test_set_chef() {
    Recipe r = Recipe("A", "B", "C");
    assert(r.get_chef() == "B");
    r.set_chef("A");
    assert(r.get_title()=="A");
}

void Test_Recipe::test_set_link() {
    Recipe r = Recipe("A", "B", "C");
    assert(r.get_link() == "C");
    r.set_link("D");
    assert(r.get_link()=="D");
}

void Test_Recipe::test_set_duration() {
    Recipe r = Recipe("A", "B", "C", 15, Duration(10, 0));
    assert(r.get_duration().get_minutes()==10);
    assert(r.get_duration().get_seconds()==0);
    r.set_duration(Duration(5,8));
    assert(r.get_duration().get_minutes()==5);
    assert(r.get_duration().get_seconds()==8);
    Duration d = Duration(0,0);
    assert(d.get_seconds()==0);
    assert(d.get_minutes()==0);
    d.set_minutes(10);
    d.set_seconds(5);
    assert(d.get_seconds()==5);
    assert(d.get_minutes()==10);
}

void Test_Recipe::test_number_of_likes() {
    Recipe r = Recipe("A", "B", "C", 15, Duration(10, 0));
    assert(r.get_number_of_likes()==15);
    r.set_number_of_likes(10);
    assert(r.get_number_of_likes()==10);
}

void Test_Recipe::test_operator_overload_equal() {
    Recipe r = Recipe("A", "B", "C", 15, Duration(10, 0));
    Recipe p = Recipe("A", "B", "C", 15, Duration(10, 0));
    assert(r==p);
}

void Test_Recipe::test_recipe_string() {
    Recipe r = Recipe("A", "B", "C", 15, Duration(10, 0));
    assert(r.recipe_string() == "Title: A by chef B with 15 likes.\n");
    assert(r.recipe_long_string()== "Title: A by chef B. Duration: 10:0 with 15 likes.\n + link(C)\n");
}

void Test_Recipe::all_tests_recipe() {
    test_constructor_empty();
    test_constructor_full();
    test_copy_constructor();
    test_number_of_likes();
    test_operator_overload_equal();
    test_partial_constructor();
    test_recipe_string();
    test_set_chef();
    test_set_duration();
    test_set_link();
    test_set_title();
}

Test_Recipe::Test_Recipe() {

}

Test_Repository::Test_Repository() {

}

void Test_Repository::test_constructor() {
    Repository repository;
    assert(repository.get_number_of_recipes()==0);
}

void Test_Repository::test_get_all_recipes() {
    Repository repository;
    repository.add_recipe(Recipe("a", "b", "c"));
    std::vector<Recipe> all_recipes = repository.get_all_recipes();
    assert(all_recipes[0].get_title()=="a");
}

void Test_Repository::test_add_recipe() {
    Repository repository;
    assert(repository.get_number_of_recipes()==0);
    repository.add_recipe(Recipe("a", "b", "c"));
    assert(repository.get_number_of_recipes()==1);
    try {
        Repo_validator::validate_add(repository,Recipe("a","b","c"));
        repository.add_recipe(Recipe("a", "b", "c"));
        assert(false);
    }
    catch(Validation_exception& ve)
    {
        assert(true);
    }
    assert(repository.get_number_of_recipes()==1);


}

void Test_Repository::test_delete_recipe() {
    Repository repository;
    assert(repository.get_number_of_recipes()==0);
    repository.add_recipe(Recipe("a", "b", "c"));
    assert(repository.get_number_of_recipes()==1);
    repository.add_recipe(Recipe("A", "B", "C"));
    repository.delete_recipe(Recipe("a", "b", "c"));
    assert(repository.get_number_of_recipes()==1);
    try {
        Repo_validator::validate_delete(repository,Recipe("a", "b", "c"));
        repository.delete_recipe(Recipe("a", "b", "c"));
        assert(false);
    }
    catch(Validation_exception& ve){
        assert(true);
    }

}

void Test_Repository::test_update_recipe() {
    Repository repository;
    assert(repository.get_number_of_recipes()==0);
    repository.add_recipe(Recipe("a", "b", "c"));
    assert(repository.get_number_of_recipes()==1);
    repository.update_recipe(Recipe("a", "b", "c"),Recipe("d", "e", "f"));
    assert(repository.get_number_of_recipes()==1);
    try{
        Repo_validator::validate_update(repository, Recipe("a", "b", "c"));
        repository.update_recipe(Recipe("a", "b", "c"),Recipe("d", "e", "f"));
        assert(false);
    }
    catch(Validation_exception& ve)
    {
        assert(true);
    }

}

void Test_Repository::test_get_number_of_recipes() {
    Repository repository;
    assert(repository.get_number_of_recipes()==0);
    repository.add_recipe(Recipe("a", "b", "c"));
    assert(repository.get_number_of_recipes()==1);
}

void Test_Repository::test_all_repository() {
    test_constructor();
    test_get_all_recipes();
    test_add_recipe();
    test_delete_recipe();
    test_update_recipe();
    test_get_number_of_recipes();
}

Test_Service::Test_Service() {

}

void Test_Service::test_constructor1() {
//    Service service;
//    assert(service.get_number_of_recipes()==0);
}

void Test_Service::test_constructor2() {
//    Repository repository;
//    repository.add_recipe(Recipe("a", "b", "c"));
//    Service service = Service(repository);
//    assert(service.get_number_of_recipes()==1);
}

void Test_Service::test_copy_constructor() {
//    Repository repository;
//    repository.add_recipe(Recipe("a", "b", "c"));
//    Service service = Service(repository);
//    assert(service.get_number_of_recipes()==1);
//    Service copy = service;
//    assert(copy.get_number_of_recipes()==1);

}

void Test_Service::test_add_recipe_to_repo() {
//    Repository repository;
//    repository.add_recipe(Recipe("aaaa", "bbbb", "cccc"));
//    Service service = Service(repository);
//    assert(service.get_number_of_recipes()==1);
//    service.add_recipe_to_repo("AAAA","BAAB", "CAAC",Duration(10,0), 7);
//    assert(service.get_number_of_recipes()==2);
//    try{
//        service.add_recipe_to_repo("aaaa","bbbb","cccc", Duration(0,0), 0);
//        assert(false);
//    }
//    catch(Validation_exception& ve)
//    {
//        assert(true);
//    }
//    assert(service.get_number_of_recipes()==2);

}



void Test_Service::test_delete_recipe_from_repo() {
//    Repository repository;
//    repository.add_recipe(Recipe("a", "b", "c"));
//    Service service = Service(repository);
//    assert(service.get_number_of_recipes()==1);
//    service.delete_recipe_from_repo("a", "b", "c");
//    assert(service.get_number_of_recipes()==0);
//    try {
//
//        service.delete_recipe_from_repo("AA","BB", "CC");
//        assert(false);
//    }
//    catch (Validation_exception& ve)
//    {
//        assert(true);
//    }
//    assert(service.get_number_of_recipes()==0);
}

void Test_Service::test_update_recipe_in_repo() {
//    Repository repository;
//    repository.add_recipe(Recipe("aaaa", "bbbb", "cccc"));
//    Service service = Service(repository);
//    assert(service.get_number_of_recipes()==1);
//    service.update_recipe_in_repo("aaaa","bbbb","cccc","AAAA", "BBBB", "CCCC", Duration(0,0), 0);
//    assert(service.get_number_of_recipes()==1);
//    try{
//        service.update_recipe_in_repo("1111","2222", "3434", "EHs", "EHs", "EHs", Duration(0,0),0);
//        assert(false);
//    }
//    catch(Validation_exception& ve)
//    {
//        assert(true);
//    }


}

void Test_Service::test_get_all_recipes() {
//    Repository repository;
//    repository.add_recipe(Recipe("a", "b", "c"));
//    Service service = Service(repository);
//    assert(service.get_number_of_recipes()==1);
//    service.add_recipe_to_repo("AAA","BBB", "CCC",Duration(0,0), 7);
//    assert(service.get_number_of_recipes()==2);
//    try{
//        service.add_recipe_to_repo("a","b","c", Duration(0,0), 0);
//        assert(false);
//    }
//    catch (Validation_exception& ve)
//    {
//        assert(true);
//    }
//    assert(service.get_number_of_recipes()==2);
//    std::vector<Recipe> all_recipes = service.get_all_recipes();
//    assert(all_recipes[0].get_title()=="a");
//    assert(all_recipes[1].get_title()=="AAA");
}

void Test_Service::test_get_number_of_recipes() {
//    Repository repository;
//    repository.add_recipe(Recipe("test1", "test1", "test1"));
//    Service service = Service(repository);
//    assert(service.get_number_of_recipes()==1);
//    service.add_recipe_to_repo("AAA","BBB", "CCC",Duration(0,0), 7);
//    assert(service.get_number_of_recipes()==2);
//    try{
//        service.add_recipe_to_repo("test1","test1","test1", Duration(0,0), 0);
//        assert(false);
//    }
//    catch (Validation_exception& ve)
//    {
//        assert(true);
//    }
//    assert(service.get_number_of_recipes()==2);
}

void Test_Service::test_initialise_repo() {
//    Service service;
//    service.initialise_repo();
//    assert(service.get_number_of_recipes()==10);
//    service.add_recipe_to_repo("Aaa", "Bbb", "Ccc", Duration(0,0), 10);
//    assert(service.get_number_of_recipes()==11);

}

void Test_Service::test_all_service() {
    test_constructor1();
    test_constructor2();
    test_copy_constructor();
    test_add_recipe_to_repo();
    test_delete_recipe_from_repo();
    test_update_recipe_in_repo();
    test_get_all_recipes();
    test_get_number_of_recipes();
    test_initialise_repo();
    test_get_watchlist_recipes();
    test_get_number_of_recipes_watchlist();
    test_add_recipe_to_watch_list();
    test_delete_recipe_from_watch_list();
    test_get_recipe_position_watch_list();
    test_get_all_recipes_by_chef();
}

void Test_Service::test_get_watchlist_recipes() {
//    Service s = Service();
//    Recipe r = Recipe("a", "b", "c");
//    s.add_recipe_to_watch_list(r);
//    std::vector<Recipe> all = s.get_watchlist_recipes();
//    assert(all[0].get_title()=="a");
}

void Test_Service::test_get_number_of_recipes_watchlist() {
//    Service s = Service();
//    Recipe r = Recipe("a", "b", "c");
//    s.add_recipe_to_watch_list(r);
//    assert(s.get_number_of_recipes_watchlist()==1);
}

void Test_Service::test_add_recipe_to_watch_list() {
//    Service s = Service();
//    Recipe r = Recipe("a", "b", "c");
//    assert(s.get_number_of_recipes_watchlist()==0);
//    s.add_recipe_to_watch_list(r);
//    assert(s.get_number_of_recipes_watchlist()==1);
//    try{
//       s.add_recipe_to_watch_list(r);
//       assert(false);
//    }
//    catch(Validation_exception& ve)
//    {
//        assert(true);
//    }
//    assert(s.get_number_of_recipes_watchlist()==1);
}

void Test_Service::test_delete_recipe_from_watch_list() {
//    Service s = Service();
//    Recipe r = Recipe("a", "b", "c");
//    assert(s.get_number_of_recipes_watchlist()==0);
//    s.add_recipe_to_watch_list(r);
//    assert(s.get_number_of_recipes_watchlist()==1);
//    s.delete_recipe_from_watch_list(r);
//    assert(s.get_number_of_recipes_watchlist()==0);
//    try{
//        s.delete_recipe_from_watch_list(r);
//        assert(false);
//    }
//    catch (Validation_exception& ve)
//    {
//        assert(true);
//    }
//    assert(s.get_number_of_recipes_watchlist()==0);
}

void Test_Service::test_get_recipe_position_watch_list() {
//    Service s = Service();
//    Recipe r = Recipe("aaa", "bbbb", "ccc");
//    assert(s.get_number_of_recipes_watchlist()==0);
//    s.add_recipe_to_watch_list(r);
//    assert(s.get_number_of_recipes_watchlist()==1);
//   // assert(s.get_recipe_position_watch_list(0).get_title()=="aaa");
}

void Test_Service::test_get_all_recipes_by_chef() {
//    Service s = Service();
//    s.add_recipe_to_repo("aaaa","bbbb","cccc", Duration(), 19);
//    s.add_recipe_to_repo("ffff","bbbb", "eeee", Duration(), 18);
//    s.add_recipe_to_repo("pppp", "eeee", "pppp", Duration(), 17);
//
//    std::vector<Recipe> chefs = s.get_all_recipes_by_chef("bbbb");
//    assert(chefs.size()==2);
//    assert(chefs[0].get_title()=="aaaa");
//    assert(chefs[1].get_title()=="ffff");
}


Test_All::Test_All() {

}

void Test_All::tests_everything() {
    Test_Recipe t1;
    Test_Repository t2;
    Test_Service t3;
    Test_Watchlist t4;
    t1.all_tests_recipe();
    t2.test_all_repository();
    t3.test_all_service();
    t4.test_all_watch_list();
}

Test_Watchlist::Test_Watchlist() {

}

void Test_Watchlist::test_constructor() {
//    Watch_list w = Watch_list();
//    assert(w.get_number_of_recipes() == 0);
}

void Test_Watchlist::test_add_recipe() {
//    Watch_list w = Watch_list();
//    assert(w.get_number_of_recipes() == 0);
//    w.add_recipe(Recipe("a","b","c"));
//    assert(w.get_number_of_recipes()==1);
//    try{
//        w.add_recipe(Recipe("a","b","c"));
//        assert(false);
//    }
//    catch(Validation_exception& ve)
//    {
//        assert(true);
//    }
//    assert(w.get_number_of_recipes()==1);
}

void Test_Watchlist::test_delete_recipe() {
//    Watch_list w = Watch_list();
//    assert(w.get_number_of_recipes() == 0);
//    w.add_recipe(Recipe("a","b","c"));
//    assert(w.get_number_of_recipes()==1);
//    w.delete_recipe(Recipe("a","b","c"));
//    assert(w.get_number_of_recipes() == 0);
//    try{
//        w.delete_recipe(Recipe("a","b","c"));
//        assert(false);
//    }
//    catch(Validation_exception& ve)
//    {
//        assert(true);
//    }

}

void Test_Watchlist::test_get_all_recipes() {
//    Watch_list w = Watch_list();
//    assert(w.get_number_of_recipes() == 0);
//    w.add_recipe(Recipe("a","b","c"));
//    assert(w.get_number_of_recipes()==1);
//    std::vector<Recipe> all = w.get_all_recipes();
//    assert(all[0].get_title() == "a");
}

void Test_Watchlist::test_get_number_of_recipes() {
//    Watch_list w = Watch_list();
//    assert(w.get_number_of_recipes() == 0);
//    w.add_recipe(Recipe("a","b","c"));
//    assert(w.get_number_of_recipes()==1);
//    w.add_recipe(Recipe("d","e","f"));
//    assert(w.get_number_of_recipes()==2);
}

void Test_Watchlist::test_get_recipe_from_pos_watchlist() {
//    Watch_list w = Watch_list();
//    assert(w.get_number_of_recipes() == 0);
//    w.add_recipe(Recipe("a","b","c"));
//    assert(w.get_number_of_recipes()==1);
//    w.add_recipe(Recipe("d","e","f"));
//    assert(w.get_number_of_recipes()==2);
//    Recipe r = w.get_recipe_from_position_watch_list(0);
//    assert(r.get_title()=="a");
}

void Test_Watchlist::test_all_watch_list() {
    test_constructor();
    test_add_recipe();
    test_delete_recipe();
    test_get_all_recipes();
    test_get_number_of_recipes();
    test_get_recipe_from_pos_watchlist();
}

