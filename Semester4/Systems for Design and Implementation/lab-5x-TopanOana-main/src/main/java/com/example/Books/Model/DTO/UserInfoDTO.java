package com.example.Books.Model.DTO;

import jakarta.validation.constraints.Positive;

public class UserInfoDTO {
    private String username;
    private String email;
    private String bio;
    private String location;

    private int age;


    public UserInfoDTO(String username, String email, String bio, String location, int age) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.location = location;
        this.age = age;
    }

    public UserInfoDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
