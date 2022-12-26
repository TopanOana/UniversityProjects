//
// Created by Oana on 5/15/2022.
//

#ifndef UNTITLED6_RECIPEGUI_H
#define UNTITLED6_RECIPEGUI_H

#include <QWidget>
#include <QStringListModel>
#include <QTableView>
#include "service/Service.h"
#include "WatchlistTableModel.h"


QT_BEGIN_NAMESPACE
namespace Ui { class RecipeGui; }
QT_END_NAMESPACE

class RecipeGui : public QWidget {
Q_OBJECT

public:
    explicit RecipeGui(Service& s,QWidget *parent = nullptr);

    ~RecipeGui() override;

private:
    Service& serv;
    Ui::RecipeGui *ui;
    WatchlistTableModel* model;
    QTableView* table_user;

    void choose_a_file_type_for_watch_list();
    void connect_signals_and_slots();

    void switch_to_user_mode();
    void switch_to_admin_mode();

    void populate_list_for_admin();

    void add_recipe_admin_mode();
    void delete_recipe_admin_mode();
    void update_recipe_admin_mode();
    void save_to_file_admin_mode();
    void read_from_file_admin_mode();

    void undo_action_admin_mode();
    void redo_action_admin_mode();

    int get_selected_index_admin_mode();


    void populate_list_for_user();

    void get_all_recipes_by_chef();

    void play_recipe_for_adding_user_mode(Recipe& r);
    void play_watchlist_user_mode();
    void play_watchlist_recipe_user_mode(Recipe& r);
    void rate_recipe_user_mode(Recipe& r);

    void show_watchlist_user_mode();

    void open_table_model();



};


#endif //UNTITLED6_RECIPEGUI_H
