//
// Created by Oana on 5/20/2022.
//

#include "WatchlistTableModel.h"

WatchlistTableModel::WatchlistTableModel(Watch_list *w, QObject *parent) : QAbstractTableModel{ parent }, watch_list{w}{

}

int WatchlistTableModel::rowCount(const QModelIndex &parent) const {
    return watch_list->get_number_of_recipes();
}

int WatchlistTableModel::columnCount(const QModelIndex &parent) const {
    return 5;
}

QVariant WatchlistTableModel::data(const QModelIndex &index, int role) const {
    int row = index.row();
    int column = index.column();
    Recipe r = watch_list->get_recipe_from_position_watch_list(row);
    if (role == Qt::DisplayRole)
    {
        switch (column)
        {
            case 0:
                return QString::fromStdString(r.get_title());
            case 1:
                return QString::fromStdString(r.get_chef());
            case 2:
                return QString::fromStdString(r.get_link());
            case 3:
                return QString::fromStdString(std::to_string(r.get_duration().get_minutes())+":"+std::to_string(r.get_duration().get_seconds()));
            case 4:
                return QString::number(r.get_number_of_likes());
            default: break;
        }
    }

    return QVariant();
}

QVariant WatchlistTableModel::headerData(int section, Qt::Orientation orientation, int role) const {
    if (role == Qt::DisplayRole)
    {
        if (orientation == Qt::Horizontal)
        {
            switch (section)
            {
                case 0:
                    return QString{ "Title" };
                case 1:
                    return QString{ "Chef" };
                case 2:
                    return QString{ "Link" };
                case 3:
                    return QString{ "Duration" };
                case 4:
                    return QString{ "Number of likes" };
                default:
                    break;
            }
        }
    }
    return QVariant{};
}

void WatchlistTableModel::addRecipe(const Recipe &r) {
    int n = this->watch_list->get_number_of_recipes();
    this->beginInsertRows(QModelIndex{}, n, n);
    this->watch_list->add_recipe(r);
    this->endInsertRows();

}

//
//bool WatchlistTableModel::setData(const QModelIndex &index, const QVariant &value, int role) {
//    if (!index.isValid() || role != Qt::EditRole)
//        return false;
//
//    // set the new data to the gene
//    int recipeIndex = index.row();
//
//    // get the genes
//    std::vector<Recipe> recipes = this->watch_list->get_all_recipes();
//
//    // emit the dataChanged signal
//    emit dataChanged(index, index);
//
//    return true;
//}

//Qt::ItemFlags WatchlistTableModel::flags(const QModelIndex &index) const {
//    return Qt::ItemIsEnabled ;
//}
