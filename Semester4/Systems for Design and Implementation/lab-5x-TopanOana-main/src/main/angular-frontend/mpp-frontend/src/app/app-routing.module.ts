import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./common/home/home.component";
import {OverviewBooksComponent} from "./features/books/components/overview-books/overview-books.component";
import {BookDetailsComponent} from "./features/books/components/book-details/book-details.component";
import {AddBookComponent} from "./features/books/components/add-book/add-book.component";
import {
  OverviewEmployeesComponent
} from "./features/employees/components/overview-employees/overview-employees.component";
import {AddEmployeeComponent} from "./features/employees/components/add-employee/add-employee.component";
import {EmployeeDetailsComponent} from "./features/employees/components/employee-details/employee-details.component";
import {OverviewStoresComponent} from "./features/stores/components/overview-stores/overview-stores.component";
import {AddStoreComponent} from "./features/stores/components/add-store/add-store.component";
import {StoreDetailsComponent} from "./features/stores/components/store-details/store-details.component";
import {StatisticPageComponent} from "./features/statistics/components/statistic-page/statistic-page.component";
import {
  BookStockStatisticComponent
} from "./features/statistics/components/book-stock-statistic/book-stock-statistic.component";
import {
  StoreStockStatisticComponent
} from "./features/statistics/components/store-stock-statistic/store-stock-statistic.component";
import {UpdateStockComponent} from "./features/stocks/components/update-stock/update-stock.component";
import {AddStockComponent} from "./features/stocks/components/add-stock/add-stock.component";
import {LoginComponent} from "./features/auth/login/login.component";
import {RegisterComponent} from "./features/auth/register/register.component";
import {UserProfileComponent} from "./features/users/user-profile/user-profile.component";
import {OverviewUsersComponent} from "./features/admin/overview-users/overview-users.component";
import {UpdateUserComponent} from "./features/admin/update-user/update-user.component";
import {SqlScriptsComponent} from "./features/admin/sql-scripts/sql-scripts.component";

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "books",
    component: OverviewBooksComponent
  },
  {
    path: "books/add",
    component: AddBookComponent
  },
  {
    path: "books/:id",
    component: BookDetailsComponent
  },
  {
    path:"employees",
    component: OverviewEmployeesComponent
  },
  {
    path: "employees/add",
    component: AddEmployeeComponent
  },
  {
    path: "employees/:id",
    component: EmployeeDetailsComponent
  },
  {
    path: "stores",
    component: OverviewStoresComponent
  },
  {
    path: "stores/add",
    component: AddStoreComponent
  },
  {
    path: "stores/:id",
    component: StoreDetailsComponent
  },
  {
    path: "stats",
    component: StatisticPageComponent
  },
  {
    path: "statistics/bookStockStat",
    component: BookStockStatisticComponent
  },
  {
    path: "statistics/storeStockStat",
    component: StoreStockStatisticComponent
  },
  {
    path: "stores/:id/update-stock/:idS",
    component: UpdateStockComponent
  },
  {
    path: "stores/:id/add-stock",
    component: AddStockComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path:"register",
    component:RegisterComponent
  },
  {
    path:"userprofile/:username",
    component: UserProfileComponent
  },
  {
    path: "admin/users",
    component: OverviewUsersComponent
  },
  {
    path: "admin/users/:id",
    component: UpdateUserComponent
  },
  {
    path: "admin/sql_scripts",
    component: SqlScriptsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
