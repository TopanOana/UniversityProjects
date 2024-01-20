package com.example.Books.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="USERINFO"
    ,
    uniqueConstraints = {
    @UniqueConstraint(name="usernameUnique", columnNames = "username")}
    )

public class UserInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;
    private String roles;
    @Email
    private String email;
    private String bio;
    private String location;
    @Positive
    private int age;

    private String confirmationCode;
    private boolean isVerified;

    private LocalDateTime confirmCodeSend;

//    @OneToMany(mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnore
//    private Set<Book> books;
//    @OneToMany(mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnore
//    private Set<Store> stores;
//
//    @OneToMany(mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnore
//    private Set<Stock> stocks;
//
//    @OneToMany(mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnore
//    private Set<Employee> employees;




    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public LocalDateTime getConfirmCodeSend() {
        return confirmCodeSend;
    }

    public void setConfirmCodeSend(LocalDateTime confirmCodeSend) {
        this.confirmCodeSend = confirmCodeSend;
    }

    public UserInfo(Long id, String username, String password, String roles, String email, String bio, String location, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.bio = bio;
        this.location = location;
        this.age = age;
    }

    public UserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
