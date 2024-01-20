package com.example.Books.Model.DTO;

public class BookStockDTO implements Comparable<BookStockDTO>{
    private Long bookID;
    private int quantity;
    private String title;
    private String author;
    private int nrPages;
    private double rating;
    private String genre;

    public BookStockDTO() {
    }

    public BookStockDTO(Long bookID, String title, String author, int nrPages, double rating, String genre, int quantity) {
        this.bookID = bookID;
        this.quantity = quantity;
        this.title = title;
        this.author = author;
        this.nrPages = nrPages;
        this.rating = rating;
        this.genre = genre;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNrPages() {
        return nrPages;
    }

    public void setNrPages(int nrPages) {
        this.nrPages = nrPages;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public int compareTo(BookStockDTO other){
        return (-1)*(this.getQuantity()- other.getQuantity());
    }
}
