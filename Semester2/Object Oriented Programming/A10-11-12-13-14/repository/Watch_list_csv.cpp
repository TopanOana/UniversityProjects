////
//// Created by Oana on 4/17/2022.
////
//
#include <fstream>
#include "Watch_list_csv.h"

Watch_list_csv::Watch_list_csv(std::string file_path) {
    this->file_path = file_path;
}

void Watch_list_csv::save_watchlist_to_file() const {
//    Watch_list::save_watchlist_to_file();
    std::ofstream f(file_path);
    if (!f.is_open())
        return;
    //std::cout<<"saving\n";
    for (const auto& r: recipes.get_all_recipes())
    {
        f << r;
    }
    f.close();
}

void Watch_list_csv::show_watchlist_from_file() const {
    const char* fp = file_path.c_str();
    //ShellExecute(NULL, "open",fp, NULL,NULL,SW_SHOWNORMAL);
    system(fp);
}