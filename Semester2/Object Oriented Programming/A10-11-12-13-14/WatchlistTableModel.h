//
// Created by Oana on 5/20/2022.
//

#pragma once

#include "repository/Watch_list.h"
#include <QAbstractTableModel>

class WatchlistTableModel : public QAbstractTableModel{
private:
    Watch_list* watch_list;

public:
    WatchlistTableModel(Watch_list* w, QObject* parent = nullptr);
    ~WatchlistTableModel(){}

    int rowCount(const QModelIndex &parent = QModelIndex{}) const override;

    int columnCount(const QModelIndex &parent = QModelIndex{}) const override;

    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const override;

    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

    void addRecipe(const Recipe& r);


//    bool setData(const QModelIndex & index, const QVariant & value, int role = Qt::EditRole) override;
//
//    Qt::ItemFlags flags(const QModelIndex & index) const override;
};

