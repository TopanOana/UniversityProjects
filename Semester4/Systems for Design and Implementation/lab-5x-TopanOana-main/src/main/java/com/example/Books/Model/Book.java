package com.example.Books.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOOK")
public class Book {
    ///Generated ID because that is the better way to work with data
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    ///title of book
    @Column
    @NotBlank
    private String title;
    //author of book
    @Column
    @NotBlank
    private String author;
    //number of pages
    @Column
    @Positive
    private int nrPages;
    //rating of book out of 5
    @Min(0)
    @Max(5)
    @Column
    private double rating;
    //genre of book
    @Column
    @NotBlank
    private String genre;

    @JsonIgnore
    @OneToMany(mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

//    @JsonGetter
//    public int getInStores(){
//        return stocks.size();
//    }

    @ManyToOne
    @JoinColumn(name="userID")
    @JsonIgnore
    private UserInfo user;


    //empty constructor
    public Book() {
    }

    //constructor that has all the attributes/fields
    public Book(String title, String author, int nrPages, double rating, String genre) {
        this.title = title;
        this.author = author;
        this.nrPages = nrPages;
        this.rating = rating;
        this.genre = genre;
        stocks = new ArrayList<>();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getNrPages() {
        return nrPages;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", nrPages=" + nrPages +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNrPages(int nrPages) {
        this.nrPages = nrPages;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void addStock(Stock stock){
        stock.setBook(this);
        this.stocks.add(stock);
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
