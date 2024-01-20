package com.example.Books.Model.DTO;

public class UserStatDTO {
    private Long nrBooks;
    private Long nrEmployees;
    private Long nrStores;
    private Long nrStocks;

    public UserStatDTO(Long nrBooks, Long nrEmployees, Long nrStores, Long nrStocks) {
        this.nrBooks = nrBooks;
        this.nrEmployees = nrEmployees;
        this.nrStores = nrStores;
        this.nrStocks = nrStocks;
    }

    public UserStatDTO() {
    }

    public Long getNrBooks() {
        return nrBooks;
    }

    public void setNrBooks(Long nrBooks) {
        this.nrBooks = nrBooks;
    }

    public Long getNrEmployees() {
        return nrEmployees;
    }

    public void setNrEmployees(Long nrEmployees) {
        this.nrEmployees = nrEmployees;
    }

    public Long getNrStores() {
        return nrStores;
    }

    public void setNrStores(Long nrStores) {
        this.nrStores = nrStores;
    }

    public Long getNrStocks() {
        return nrStocks;
    }

    public void setNrStocks(Long nrStocks) {
        this.nrStocks = nrStocks;
    }
}
