////
//// Created by Oana on 4/17/2022.
////

#pragma once
#include "repository/Watch_list.h"

class Watch_list_csv: public Watch_list {
private:
    std::string file_path;
public:
    Watch_list_csv(std::string file_path);

    void save_watchlist_to_file() const override;

    void show_watchlist_from_file() const override;

};

