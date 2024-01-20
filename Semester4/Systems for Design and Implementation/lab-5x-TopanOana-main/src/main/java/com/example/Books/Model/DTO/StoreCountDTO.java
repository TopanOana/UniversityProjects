package com.example.Books.Model.DTO;

public class StoreCountDTO {
    private Long storeID;

    private String storeName;
    private String address;
    private String contactNumber;
    private int openingHour;
    private int closingHour;
    private String username;
    private int nrBooks;
    private Long nrEmployees;

    public StoreCountDTO(Long storeID, String storeName, String address, String contactNumber, int openingHour, int closingHour, String username, int nrBooks, Long nrEmployees) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.username = username;
        this.nrBooks = nrBooks;
        this.nrEmployees = nrEmployees;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StoreCountDTO() {
    }



    public Long getStoreID() {
        return storeID;
    }

    public void setStoreID(Long storeID) {
        this.storeID = storeID;
    }

    public int getNrBooks() {
        return nrBooks;
    }

    public void setNrBooks(int nrBooks) {
        this.nrBooks = nrBooks;
    }

    public Long getNrEmployees() {
        return nrEmployees;
    }

    public void setNrEmployees(Long nrEmployees) {
        this.nrEmployees = nrEmployees;
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

}
