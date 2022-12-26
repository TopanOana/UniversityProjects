#include <QApplication>
#include <QPushButton>
#include <QVBoxLayout>
#include <QLineEdit>
#include <QLabel>
#include "repository/Repository.h"
#include "service/Service.h"
#include "recipegui.h"
//#include "gui.h"



int main(int argc, char *argv[]) {

    QApplication a(argc, argv);
    Repository r;
    Service service(r);
   // service.initialise_repo();
    //gui ui(service);
    //ui.show();
    RecipeGui gui{service};
    gui.show();
    return a.exec();
}
