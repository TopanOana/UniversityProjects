//
// Created by Oana on 5/15/2022.
//

// You may need to build the project (run Qt uic code generator) to get "ui_RecipeGui.h" resolved

#include "recipegui.h"
#include "ui_RecipeGui.h"
#include "repository/Watch_list_csv.h"
#include "repository/Watch_list_html.h"
#include "validation/validator.h"
#include <QDialog>
#include <QLineEdit>
#include <QMessageBox>
#include <QPainter>
#include <QColor>
#include <QShortcut>
#include <QAction>
#include <QStringListModel>



RecipeGui::RecipeGui(Service& s, QWidget *parent) :
        QWidget(parent), ui(new Ui::RecipeGui), serv{ s } {
    ui->setupUi(this);
    this->ui->tabWidget->setCurrentIndex(0);




    serv.read_recipes_from_file();
    connect_signals_and_slots();
}

RecipeGui::~RecipeGui() {
    //this->serv.save_recipes_to_file();
    delete ui;
}

void RecipeGui::connect_signals_and_slots() {
    QObject::connect(this->ui->filetypeWatchlistButton, &QPushButton::clicked, this, &RecipeGui::choose_a_file_type_for_watch_list);
    QObject::connect(this->ui->userModeButton, &QPushButton::clicked, this, &RecipeGui::switch_to_user_mode);
    QObject::connect(this->ui->adminModeButton, &QPushButton::clicked, this, &RecipeGui::switch_to_admin_mode);
    QObject::connect(this->ui->addRecipeButton, &QPushButton::clicked, this, &RecipeGui::add_recipe_admin_mode);
    QObject::connect(this->ui->deleteRecipeButton, &QPushButton::clicked, this, &RecipeGui::delete_recipe_admin_mode);
    QObject::connect(this->ui->updateRecipeButton, &QPushButton::clicked, this, &RecipeGui::update_recipe_admin_mode);
    QObject::connect(this->ui->readFileButton, &QPushButton::clicked, this, &RecipeGui::read_from_file_admin_mode);
    QObject::connect(this->ui->saveFileButton, &QPushButton::clicked, this, &RecipeGui::save_to_file_admin_mode);
    QObject::connect(this->ui->seeChefButton, &QPushButton::clicked, this, &RecipeGui::get_all_recipes_by_chef);
    QObject::connect(this->ui->playWatchlistButton, &QPushButton::clicked, this, &RecipeGui::play_watchlist_user_mode);
    QObject::connect(this->ui->showWatchlistButton, &QPushButton::clicked, this, &RecipeGui::show_watchlist_user_mode);
    QObject::connect(this->ui->undoButton, &QPushButton::clicked, this, &RecipeGui::undo_action_admin_mode);
    QObject::connect(this->ui->redoButton, &QPushButton::clicked, this, &RecipeGui::redo_action_admin_mode);
    QShortcut *undo_shortcut = new QShortcut(QKeySequence(tr("CTRL+Z")), this);
    QShortcut *redo_shortcut = new QShortcut(QKeySequence(tr("CTRL+Y")), this);
    QObject::connect(undo_shortcut,    &QShortcut::activated, this,&RecipeGui::undo_action_admin_mode);
    QObject::connect(redo_shortcut,    &QShortcut::activated, this,&RecipeGui::redo_action_admin_mode);
    QObject::connect(this->ui->openWatchlistButton, &QPushButton::clicked, this, &RecipeGui::open_table_model);
}

void RecipeGui::choose_a_file_type_for_watch_list() {
    Watch_list* w;
    if (this->ui->CSVradioButton->isChecked())
        w = new Watch_list_csv("C:/Users/Oana/CLionProjects/untitled6/watchlist.csv");
    if (this->ui->HTMLradioButton->isChecked())
        w = new Watch_list_html("C:/Users/Oana/CLionProjects/untitled6/watchlist.html");
    serv.get_watchlist_type(w);
    this->ui->filetypeWatchlistButton->hide();

    model = new WatchlistTableModel(serv.get_watchlist());
    this->ui->tableView->setModel(model);


}

void RecipeGui::switch_to_user_mode() {
    this->ui->tabWidget->setCurrentIndex(2);
    populate_list_for_user();
}

void RecipeGui::switch_to_admin_mode(){
    this->ui->tabWidget->setCurrentIndex(1);
    populate_list_for_admin();
}

void RecipeGui::populate_list_for_admin(){
    this->ui->adminListWidget->clear();

    std::vector<Recipe> all = serv.get_all_recipes();

    for (Recipe& r: all){
        this->ui->adminListWidget->addItem(QString::fromStdString(r.recipe_long_string()));
    }
}

void RecipeGui::add_recipe_admin_mode() {
    QDialog* dialog = new QDialog(this);
    dialog->setModal(true);
    QVBoxLayout* dialog_layout = new QVBoxLayout;
    dialog->setLayout(dialog_layout);
    dialog->setAttribute(Qt::WA_DeleteOnClose);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);

    QLineEdit* title_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Title: "), title_txt);

    QLineEdit* chef_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Chef: "), chef_txt);

    QLineEdit* link_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Link: "), link_txt);

    QLineEdit* number_likes_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Number of likes: "), number_likes_txt);

    QLineEdit* minutes_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Minutes: "), minutes_txt);

    QLineEdit* seconds_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Seconds: "), seconds_txt);

    QPushButton* add = new QPushButton("Add");

    dialog_layout->addWidget(form);
    dialog_layout->addWidget(add);

    QObject::connect(add, &QPushButton::clicked, [&](){
                         int number_of_likes,seconds,minutes ;
                         std::string title, chef, link;
                         try{

                             title = title_txt->text().toStdString();
                             chef = chef_txt->text().toStdString();
                             link = link_txt->text().toStdString();
                             number_of_likes = number_likes_txt->text().toInt();
                             seconds = seconds_txt->text().toInt();
                             minutes = minutes_txt->text().toInt();

                             this->serv.add_recipe_to_repo(title,chef,link, Duration(minutes,seconds),number_of_likes);
                             populate_list_for_admin();
                             dialog->close();

                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );
    dialog->show();
    dialog->exec();

}

void RecipeGui::delete_recipe_admin_mode() {
    int selected_index = this->get_selected_index_admin_mode();
    if (selected_index<0){
        QMessageBox::critical(this, "Error", "No recipe was selected.");
        return;
    }
    Recipe r = this->serv.get_all_recipes()[selected_index];
    this->serv.delete_recipe_from_repo(r.get_title(),r.get_chef(), r.get_link(),r.get_duration(),r.get_number_of_likes());
    this->populate_list_for_admin();
}

void RecipeGui::update_recipe_admin_mode() {
    QDialog* dialog = new QDialog(this);
    dialog->setModal(true);
    QVBoxLayout* dialog_layout = new QVBoxLayout;
    dialog->setLayout(dialog_layout);
    dialog->setAttribute(Qt::WA_DeleteOnClose);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);

    QLineEdit* title_old_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Title old: "), title_old_txt);

    QLineEdit* chef_old_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Chef old: "), chef_old_txt);

    QLineEdit* link_old_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Link old: "), link_old_txt);

    QLineEdit* title_new_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Title new: "), title_new_txt);

    QLineEdit* chef_new_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Chef new: "), chef_new_txt);

    QLineEdit* link_new_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Link new: "), link_new_txt);

    QLineEdit* number_likes_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Number of likes new: "), number_likes_txt);

    QLineEdit* minutes_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Minutes new: "), minutes_txt);

    QLineEdit* seconds_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Seconds new: "), seconds_txt);

    QPushButton* add = new QPushButton("Update");

    dialog_layout->addWidget(form);
    dialog_layout->addWidget(add);

    QObject::connect(add, &QPushButton::clicked, [&](){
                         int number_of_likes,seconds,minutes ;
                         std::string title_old, chef_old, link_old, title_new, chef_new, link_new;
                         try{

//
                             title_old = title_old_txt->text().toStdString();
                             chef_old = chef_old_txt->text().toStdString();
                             link_old = link_old_txt->text().toStdString();
                             title_new = title_new_txt->text().toStdString();
                             chef_new = chef_new_txt->text().toStdString();
                             link_new = link_new_txt->text().toStdString();
                             number_of_likes = number_likes_txt->text().toInt();
                             seconds = seconds_txt->text().toInt();
                             minutes = minutes_txt->text().toInt();
                             this->serv.update_recipe_in_repo(title_old, chef_old, link_old, title_new, chef_new, link_new, Duration(minutes, seconds), number_of_likes);

                             populate_list_for_admin();
                             dialog->close();

                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );
    dialog->show();
    dialog->exec();

}

void RecipeGui::save_to_file_admin_mode() {
    this->serv.save_recipes_to_file();
}

void RecipeGui::read_from_file_admin_mode() {
    this->serv.read_recipes_from_file();
    this->populate_list_for_admin();
}

int RecipeGui::get_selected_index_admin_mode() {
    QModelIndexList selected_indexes = this->ui->adminListWidget->selectionModel()->selectedIndexes();
    if (selected_indexes.size() == 0){
        return -1;
    }
    int selected_index = selected_indexes.at(0).row();
    return selected_index;
}

void RecipeGui::populate_list_for_user() {
//    this->ui->userListView->reset();
//    QStringList watchlist;
//    auto all = this->serv.get_watchlist_recipes();
//
//    for (Recipe& r: all){
//        watchlist << QString::fromStdString(r.recipe_long_string());
//    }
//    //model->setStringList(watchlist);
//    this->ui->userListView->setModel(model);
}

void RecipeGui::get_all_recipes_by_chef() {
    QDialog* dialog = new QDialog(this);
    dialog->setModal(true);
    QVBoxLayout* dialog_layout = new QVBoxLayout;
    dialog->setLayout(dialog_layout);
    dialog->setAttribute(Qt::WA_DeleteOnClose);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);

    QLineEdit* chef_txt = new QLineEdit;
    form_layout->addRow(new QLabel("Chef: "), chef_txt);

    QPushButton* add = new QPushButton("Search");

    dialog_layout->addWidget(form);
    dialog_layout->addWidget(add);

    QObject::connect(add, &QPushButton::clicked, [&](){
                         std::string chef;
                         try{

                             chef = chef_txt->text().toStdString();

                             auto all = this->serv.get_all_recipes_by_chef(chef);

                             for (Recipe& r: all){
                                 this->play_recipe_for_adding_user_mode(r);
                             }
                             populate_list_for_user();
                             dialog->close();

                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );
    dialog->show();
    dialog->exec();


}

void RecipeGui::play_recipe_for_adding_user_mode(Recipe& r) {
    QDialog* dialog = new QDialog(this);
    dialog->setModal(true);
    QVBoxLayout* dialog_layout = new QVBoxLayout;
    dialog->setLayout(dialog_layout);
    dialog->setAttribute(Qt::WA_DeleteOnClose);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);
    QString label = QString::fromStdString(r.recipe_string());
    form_layout->addRow(new QLabel(label));


    QPushButton* add = new QPushButton("Add to watchlist.");
    QPushButton* next = new QPushButton("Next recipe.");

    dialog_layout->addWidget(form);
    dialog_layout->addWidget(add);
    dialog_layout->addWidget(next);

    QObject::connect(add, &QPushButton::clicked, [&](){
                         try{

                             //this->serv.add_recipe_to_watch_list(r);
                             this->model->addRecipe(r);
                             this->table_user->resizeColumnsToContents();
                             dialog->close();

                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );

    QObject::connect(next, &QPushButton::clicked, [&](){
                         try{
                             dialog->close();
                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );

    dialog->show();
    dialog->exec();

}

void RecipeGui::play_watchlist_user_mode() {
    auto watch = this->serv.get_watchlist_recipes();

    for (auto r: watch){
        r.play();
        this->play_watchlist_recipe_user_mode(r);
    }


}

void RecipeGui::play_watchlist_recipe_user_mode(Recipe& r) {
    QDialog* dialog = new QDialog(this);
    dialog->setModal(true);
    QVBoxLayout* dialog_layout = new QVBoxLayout;
    dialog->setLayout(dialog_layout);
    dialog->setAttribute(Qt::WA_DeleteOnClose);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);
    QString label = QString::fromStdString(r.recipe_string());
    form_layout->addRow(new QLabel(label));


    QPushButton* add = new QPushButton("Delete from watchlist.");
    QPushButton* next = new QPushButton("Next recipe.");

    dialog_layout->addWidget(form);
    dialog_layout->addWidget(add);
    dialog_layout->addWidget(next);

    QObject::connect(add, &QPushButton::clicked, [&](){
                         try{

                            this->rate_recipe_user_mode(r);
                            this->serv.delete_recipe_from_watch_list(r);
                            //populate_list_for_user();

                            dialog->close();

                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );

    QObject::connect(next, &QPushButton::clicked, [&](){
                         try{
                             dialog->close();
                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );

    dialog->show();
    dialog->exec();


}

void RecipeGui::rate_recipe_user_mode(Recipe &r) {
    QDialog* dialog = new QDialog(this);
    dialog->setModal(true);
    QVBoxLayout* dialog_layout = new QVBoxLayout;
    dialog->setLayout(dialog_layout);
    dialog->setAttribute(Qt::WA_DeleteOnClose);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);
    QString label = QString::fromStdString("Rate recipe?");
    form_layout->addRow(new QLabel(label));


    QPushButton* add = new QPushButton("Yes");
    QPushButton* next = new QPushButton("No");

    dialog_layout->addWidget(form);
    dialog_layout->addWidget(add);
    dialog_layout->addWidget(next);

    QObject::connect(add, &QPushButton::clicked, [&](){
                         try{
                             this->serv.update_recipe_in_repo(r.get_title(), r.get_chef(), r.get_link(), r.get_title(), r.get_chef(), r.get_link(), r.get_duration(), r.get_number_of_likes()+1);
                             this->populate_list_for_admin();
                             dialog->close();

                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );

    QObject::connect(next, &QPushButton::clicked, [&](){
                         try{
                             dialog->close();
                         }
                         catch(Validation_exception& ex){
                             QMessageBox::warning(dialog, "Warning", QString::fromStdString(ex.get_message()));
                         }

                     }
    );

    dialog->show();
    dialog->exec();

}

void RecipeGui::show_watchlist_user_mode() {
    this->serv.save_watch_list_to_file();
    this->serv.show_watch_list_from_file();

}

void RecipeGui::undo_action_admin_mode() {
    try{
        serv.undo_action();
        populate_list_for_admin();
        QMessageBox::warning(this, "Done", "Undo completed.");
    }
    catch(Validation_exception& ex){
        QMessageBox::warning(this, "Warning", QString::fromStdString(ex.get_message()));
    }
}

void RecipeGui::redo_action_admin_mode() {
    try{
        serv.redo_action();
        populate_list_for_admin();
        QMessageBox::warning(this, "Done", "Redo completed.");
    }
    catch(Validation_exception& ex){
        QMessageBox::warning(this, "Warning", QString::fromStdString(ex.get_message()));
    }
}

void RecipeGui::open_table_model() {
//    this->ui->tableView->setModel(model);
//    this->ui->tableView->show();
    auto* widget = new QWidget();
    QHBoxLayout* layout = new QHBoxLayout(widget);
    this->table_user = new QTableView();
    layout->addWidget(table_user);
    this->table_user->setModel(model);


    widget->show();

}


