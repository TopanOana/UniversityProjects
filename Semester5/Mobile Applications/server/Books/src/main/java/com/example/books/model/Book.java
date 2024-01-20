package com.example.books.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    @NotEmpty
    String title;
    @Column
    @NotEmpty
    String author;
    @Column
    @Positive
    int nrPages;
    @Column
    Status status;
    @Column
    @NotEmpty
    String genre;

}
