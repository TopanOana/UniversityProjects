package com.example.Books.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STORE")
public class Store {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Column
    @NotBlank
    private String storeName;

    @Column
    @NotBlank
    private String address;

    @Column
    @Size(min=10, max=10)
    private String contactNumber;

    @Column
    @Min(0)
    @Max(23)
    private int openingHour;

    @Column
    @Min(0)
    @Max(23)
    private int closingHour;

    @JsonIgnore
    @OneToMany(mappedBy = "store",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="userID")
    @JsonIgnore
    private UserInfo user;

    @JsonIgnore
    @OneToMany(mappedBy = "store",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

//    @JsonGetter
//    public int getnrBooks(){
//        return stocks.size();
//    }
//
//    @JsonGetter
//    public int getnrEmployees(){
//        return employees.size();
//    }

    public Store(String storeName, String address, String contactNumber, int openingHour, int closingHour) {
        this.storeName = storeName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public Long getId() {
        return id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Store() {
    }

    public Store addEmployeeToStore(Employee employee){
        employees.add(employee);
        employee.setStore(this);
        return this;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(int openingHour) {
        this.openingHour = openingHour;
    }

    public int getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(int closingHour) {
        this.closingHour = closingHour;
    }

    public void removeEmployeeFromStore(Long employeeID){
        this.employees.remove(employeeID);
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public Stock addStockToStore(Stock stock){
        stock.setStore(this);
        this.stocks.add(stock);
        return stock;
    }
    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", openingHour=" + openingHour +
                ", closingHour=" + closingHour +
                '}';
    }
}
