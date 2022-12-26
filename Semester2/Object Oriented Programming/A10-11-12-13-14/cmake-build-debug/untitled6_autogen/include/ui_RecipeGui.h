/********************************************************************************
** Form generated from reading UI file 'recipegui.ui'
**
** Created by: Qt User Interface Compiler version 6.2.4
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_RECIPEGUI_H
#define UI_RECIPEGUI_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QRadioButton>
#include <QtWidgets/QTabWidget>
#include <QtWidgets/QTableView>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_RecipeGui
{
public:
    QHBoxLayout *horizontalLayout_4;
    QTabWidget *tabWidget;
    QWidget *MainMenu;
    QFormLayout *formLayout;
    QLabel *label;
    QHBoxLayout *horizontalLayout_2;
    QLabel *label_2;
    QPushButton *filetypeWatchlistButton;
    QVBoxLayout *verticalLayout;
    QRadioButton *CSVradioButton;
    QRadioButton *HTMLradioButton;
    QHBoxLayout *horizontalLayout;
    QPushButton *adminModeButton;
    QPushButton *userModeButton;
    QWidget *AdminMode;
    QListWidget *adminListWidget;
    QWidget *layoutWidget;
    QGridLayout *gridLayout;
    QPushButton *addRecipeButton;
    QPushButton *deleteRecipeButton;
    QPushButton *updateRecipeButton;
    QPushButton *saveFileButton;
    QPushButton *readFileButton;
    QPushButton *undoButton;
    QPushButton *redoButton;
    QWidget *UserMode;
    QVBoxLayout *verticalLayout_2;
    QHBoxLayout *horizontalLayout_5;
    QPushButton *openWatchlistButton;
    QTableView *tableView;
    QHBoxLayout *horizontalLayout_3;
    QPushButton *seeChefButton;
    QPushButton *showWatchlistButton;
    QPushButton *playWatchlistButton;

    void setupUi(QWidget *RecipeGui)
    {
        if (RecipeGui->objectName().isEmpty())
            RecipeGui->setObjectName(QString::fromUtf8("RecipeGui"));
        RecipeGui->resize(675, 545);
        horizontalLayout_4 = new QHBoxLayout(RecipeGui);
        horizontalLayout_4->setObjectName(QString::fromUtf8("horizontalLayout_4"));
        tabWidget = new QTabWidget(RecipeGui);
        tabWidget->setObjectName(QString::fromUtf8("tabWidget"));
        tabWidget->setStyleSheet(QString::fromUtf8(""));
        MainMenu = new QWidget();
        MainMenu->setObjectName(QString::fromUtf8("MainMenu"));
        formLayout = new QFormLayout(MainMenu);
        formLayout->setObjectName(QString::fromUtf8("formLayout"));
        label = new QLabel(MainMenu);
        label->setObjectName(QString::fromUtf8("label"));
        QFont font;
        font.setPointSize(14);
        label->setFont(font);

        formLayout->setWidget(0, QFormLayout::FieldRole, label);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName(QString::fromUtf8("horizontalLayout_2"));
        label_2 = new QLabel(MainMenu);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        QFont font1;
        font1.setPointSize(11);
        label_2->setFont(font1);

        horizontalLayout_2->addWidget(label_2);

        filetypeWatchlistButton = new QPushButton(MainMenu);
        filetypeWatchlistButton->setObjectName(QString::fromUtf8("filetypeWatchlistButton"));

        horizontalLayout_2->addWidget(filetypeWatchlistButton);


        formLayout->setLayout(1, QFormLayout::SpanningRole, horizontalLayout_2);

        verticalLayout = new QVBoxLayout();
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        CSVradioButton = new QRadioButton(MainMenu);
        CSVradioButton->setObjectName(QString::fromUtf8("CSVradioButton"));

        verticalLayout->addWidget(CSVradioButton);

        HTMLradioButton = new QRadioButton(MainMenu);
        HTMLradioButton->setObjectName(QString::fromUtf8("HTMLradioButton"));

        verticalLayout->addWidget(HTMLradioButton);


        formLayout->setLayout(2, QFormLayout::LabelRole, verticalLayout);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        adminModeButton = new QPushButton(MainMenu);
        adminModeButton->setObjectName(QString::fromUtf8("adminModeButton"));
        adminModeButton->setStyleSheet(QString::fromUtf8("background-color: qlineargradient(spread:reflect, x1:0, y1:0, x2:1, y2:1, stop:0 rgba(0, 0, 0, 255), stop:1 rgba(255, 255, 255, 255));"));

        horizontalLayout->addWidget(adminModeButton);

        userModeButton = new QPushButton(MainMenu);
        userModeButton->setObjectName(QString::fromUtf8("userModeButton"));

        horizontalLayout->addWidget(userModeButton);


        formLayout->setLayout(3, QFormLayout::FieldRole, horizontalLayout);

        tabWidget->addTab(MainMenu, QString());
        AdminMode = new QWidget();
        AdminMode->setObjectName(QString::fromUtf8("AdminMode"));
        adminListWidget = new QListWidget(AdminMode);
        adminListWidget->setObjectName(QString::fromUtf8("adminListWidget"));
        adminListWidget->setGeometry(QRect(20, 20, 471, 321));
        layoutWidget = new QWidget(AdminMode);
        layoutWidget->setObjectName(QString::fromUtf8("layoutWidget"));
        layoutWidget->setGeometry(QRect(31, 381, 437, 56));
        gridLayout = new QGridLayout(layoutWidget);
        gridLayout->setObjectName(QString::fromUtf8("gridLayout"));
        gridLayout->setContentsMargins(0, 0, 0, 0);
        addRecipeButton = new QPushButton(layoutWidget);
        addRecipeButton->setObjectName(QString::fromUtf8("addRecipeButton"));

        gridLayout->addWidget(addRecipeButton, 0, 0, 1, 1);

        deleteRecipeButton = new QPushButton(layoutWidget);
        deleteRecipeButton->setObjectName(QString::fromUtf8("deleteRecipeButton"));

        gridLayout->addWidget(deleteRecipeButton, 0, 1, 1, 1);

        updateRecipeButton = new QPushButton(layoutWidget);
        updateRecipeButton->setObjectName(QString::fromUtf8("updateRecipeButton"));

        gridLayout->addWidget(updateRecipeButton, 0, 2, 1, 1);

        saveFileButton = new QPushButton(layoutWidget);
        saveFileButton->setObjectName(QString::fromUtf8("saveFileButton"));

        gridLayout->addWidget(saveFileButton, 0, 3, 1, 1);

        readFileButton = new QPushButton(layoutWidget);
        readFileButton->setObjectName(QString::fromUtf8("readFileButton"));

        gridLayout->addWidget(readFileButton, 0, 4, 1, 1);

        undoButton = new QPushButton(layoutWidget);
        undoButton->setObjectName(QString::fromUtf8("undoButton"));

        gridLayout->addWidget(undoButton, 1, 3, 1, 1);

        redoButton = new QPushButton(layoutWidget);
        redoButton->setObjectName(QString::fromUtf8("redoButton"));

        gridLayout->addWidget(redoButton, 1, 4, 1, 1);

        tabWidget->addTab(AdminMode, QString());
        UserMode = new QWidget();
        UserMode->setObjectName(QString::fromUtf8("UserMode"));
        verticalLayout_2 = new QVBoxLayout(UserMode);
        verticalLayout_2->setObjectName(QString::fromUtf8("verticalLayout_2"));
        horizontalLayout_5 = new QHBoxLayout();
        horizontalLayout_5->setObjectName(QString::fromUtf8("horizontalLayout_5"));
        openWatchlistButton = new QPushButton(UserMode);
        openWatchlistButton->setObjectName(QString::fromUtf8("openWatchlistButton"));

        horizontalLayout_5->addWidget(openWatchlistButton);

        tableView = new QTableView(UserMode);
        tableView->setObjectName(QString::fromUtf8("tableView"));

        horizontalLayout_5->addWidget(tableView);


        verticalLayout_2->addLayout(horizontalLayout_5);

        horizontalLayout_3 = new QHBoxLayout();
        horizontalLayout_3->setObjectName(QString::fromUtf8("horizontalLayout_3"));
        seeChefButton = new QPushButton(UserMode);
        seeChefButton->setObjectName(QString::fromUtf8("seeChefButton"));

        horizontalLayout_3->addWidget(seeChefButton);

        showWatchlistButton = new QPushButton(UserMode);
        showWatchlistButton->setObjectName(QString::fromUtf8("showWatchlistButton"));

        horizontalLayout_3->addWidget(showWatchlistButton);

        playWatchlistButton = new QPushButton(UserMode);
        playWatchlistButton->setObjectName(QString::fromUtf8("playWatchlistButton"));

        horizontalLayout_3->addWidget(playWatchlistButton);


        verticalLayout_2->addLayout(horizontalLayout_3);

        tabWidget->addTab(UserMode, QString());

        horizontalLayout_4->addWidget(tabWidget);


        retranslateUi(RecipeGui);

        tabWidget->setCurrentIndex(2);


        QMetaObject::connectSlotsByName(RecipeGui);
    } // setupUi

    void retranslateUi(QWidget *RecipeGui)
    {
        RecipeGui->setWindowTitle(QCoreApplication::translate("RecipeGui", "RecipeGui", nullptr));
        label->setText(QCoreApplication::translate("RecipeGui", "Welcome to the Recipe Application", nullptr));
        label_2->setText(QCoreApplication::translate("RecipeGui", "Choose a file type for the watchlist:", nullptr));
        filetypeWatchlistButton->setText(QCoreApplication::translate("RecipeGui", "file type", nullptr));
        CSVradioButton->setText(QCoreApplication::translate("RecipeGui", "CSV", nullptr));
        HTMLradioButton->setText(QCoreApplication::translate("RecipeGui", "HTML", nullptr));
        adminModeButton->setText(QCoreApplication::translate("RecipeGui", "Admin Mode", nullptr));
        userModeButton->setText(QCoreApplication::translate("RecipeGui", "User Mode", nullptr));
        tabWidget->setTabText(tabWidget->indexOf(MainMenu), QCoreApplication::translate("RecipeGui", "MainMenu", nullptr));
        addRecipeButton->setText(QCoreApplication::translate("RecipeGui", "Add Recipe", nullptr));
        deleteRecipeButton->setText(QCoreApplication::translate("RecipeGui", "Delete Recipe", nullptr));
        updateRecipeButton->setText(QCoreApplication::translate("RecipeGui", "Update Recipe", nullptr));
        saveFileButton->setText(QCoreApplication::translate("RecipeGui", "Save to File", nullptr));
        readFileButton->setText(QCoreApplication::translate("RecipeGui", "Read from File", nullptr));
        undoButton->setText(QCoreApplication::translate("RecipeGui", "Undo action", nullptr));
        redoButton->setText(QCoreApplication::translate("RecipeGui", "Redo action", nullptr));
        tabWidget->setTabText(tabWidget->indexOf(AdminMode), QCoreApplication::translate("RecipeGui", "AdminMode", nullptr));
        openWatchlistButton->setText(QCoreApplication::translate("RecipeGui", "Open Watchlist in table view", nullptr));
        seeChefButton->setText(QCoreApplication::translate("RecipeGui", "See recipes by chef", nullptr));
        showWatchlistButton->setText(QCoreApplication::translate("RecipeGui", "Show Watchlist", nullptr));
        playWatchlistButton->setText(QCoreApplication::translate("RecipeGui", "Play Watchlist", nullptr));
        tabWidget->setTabText(tabWidget->indexOf(UserMode), QCoreApplication::translate("RecipeGui", "UserMode", nullptr));
    } // retranslateUi

};

namespace Ui {
    class RecipeGui: public Ui_RecipeGui {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_RECIPEGUI_H
