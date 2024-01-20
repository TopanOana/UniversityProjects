import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './common/home/home.component';
import { OverviewBooksComponent } from './features/books/components/overview-books/overview-books.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTableModule} from "@angular/material/table";
import { HttpClientModule} from "@angular/common/http";
import {MatButtonModule} from "@angular/material/button";
import { BookDetailsComponent } from './features/books/components/book-details/book-details.component';
import { AddBookComponent } from './features/books/components/add-book/add-book.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSortModule} from "@angular/material/sort";
import { OverviewEmployeesComponent } from './features/employees/components/overview-employees/overview-employees.component';
import { AddEmployeeComponent } from './features/employees/components/add-employee/add-employee.component';
import { EmployeeDetailsComponent } from './features/employees/components/employee-details/employee-details.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatIconModule} from "@angular/material/icon";
import { OverviewStoresComponent } from './features/stores/components/overview-stores/overview-stores.component';
import { StoreDetailsComponent } from './features/stores/components/store-details/store-details.component';
import { AddStoreComponent } from './features/stores/components/add-store/add-store.component';
import {MatSelectModule} from "@angular/material/select";
import { StoreStockStatisticComponent } from './features/statistics/components/store-stock-statistic/store-stock-statistic.component';
import {
  BookStockStatisticComponent
} from "./features/statistics/components/book-stock-statistic/book-stock-statistic.component";
import {StatisticPageComponent} from "./features/statistics/components/statistic-page/statistic-page.component";
import {MatMenuModule} from "@angular/material/menu";
import { UpdateStockComponent } from './features/stocks/components/update-stock/update-stock.component';
import { AddStockComponent } from './features/stocks/components/add-stock/add-stock.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { UserProfileComponent } from './features/users/user-profile/user-profile.component';
import { OverviewUsersComponent } from './features/admin/overview-users/overview-users.component';
import { UpdateUserComponent } from './features/admin/update-user/update-user.component';
import { SqlScriptsComponent } from './features/admin/sql-scripts/sql-scripts.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    OverviewBooksComponent,
    BookDetailsComponent,
    AddBookComponent,
    OverviewEmployeesComponent,
    AddEmployeeComponent,
    EmployeeDetailsComponent,
    OverviewStoresComponent,
    StoreDetailsComponent,
    AddStoreComponent,
    BookStockStatisticComponent,
    StatisticPageComponent,
    StoreStockStatisticComponent,
    UpdateStockComponent,
    AddStockComponent,
    LoginComponent,
    RegisterComponent,
    UserProfileComponent,
    OverviewUsersComponent,
    UpdateUserComponent,
    SqlScriptsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatToolbarModule,
    MatTableModule,
    MatButtonModule,
    FormsModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatSortModule,
    MatAutocompleteModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    MatIconModule,
    MatSelectModule,
    MatMenuModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
