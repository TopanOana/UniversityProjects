//
// Created by Oana on 4/18/2022.
//

#include "Watch_list_html.h"
#include <fstream>

Watch_list_html::Watch_list_html(std::string file_path) {
    this->file_path = file_path;
}

void Watch_list_html::save_watchlist_to_file() const {
    //Watch_list::save_watchlist_to_file();
    std::ofstream f(file_path);
    if (!f.is_open())
        return;
    f<<"<!DOCTYPE html>\n<html>\n\t<head>\n\t\t<title>Watchlist</title>\n\t</head>\n\t<body>\n\t\t<table>\n";
    f<<"\t\t<tr>\n\t\t\t<td>Title</td>\n\t\t\t<td>Chef</td>\n\t\t\t<td>Duration</td>\n\t\t\t<td>Link</td>\n\t\t\t<td>Likes</td>\n\t\t</tr>\n";
    for (auto r: recipes.get_all_recipes())
    {
        f<<"\t\t<tr>\n";
        f<<"\t\t\t<td>"<<r.get_title()<<"</td>\n";
        f<<"\t\t\t<td>"<<r.get_chef()<<"</td>\n";
        f<<"\t\t\t<td>"<<r.get_duration().get_minutes()<<":"<<r.get_duration().get_seconds()<<"</td>\n";
        f<<"\t\t\t<td><a href = \""<<r.get_link()<<"\">Link</a></td>\n";
        f<<"\t\t\t<td>"<<r.get_number_of_likes()<<"</td>\n";
        f<<"\t\t</tr>\n";
    }
    f<<"\t\t</table>\n\t</body>\n</html>\n";
    f.close();
}

void Watch_list_html::show_watchlist_from_file() const {
    const char* fp = file_path.c_str();
    //ShellExecute(NULL, "open",fp, NULL,NULL,SW_SHOWNORMAL);
    system(fp);
}