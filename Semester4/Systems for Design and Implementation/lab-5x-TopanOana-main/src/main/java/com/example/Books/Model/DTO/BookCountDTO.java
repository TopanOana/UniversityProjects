package com.example.Books.Model.DTO;

public class BookCountDTO {
    private Long bookID;
    private String title;
    private String author;
    private int nrPages;
    private double rating;
    private String genre;
    private int count;
    private String username;

    public BookCountDTO(Long bookID, String title, String author, int nrPages, double rating, String genre, int count, String username) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.nrPages = nrPages;
        this.rating = rating;
        this.genre = genre;
        this.count = count;
        this.username = username;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
