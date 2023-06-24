package com.example.demo.httpcalls.models;

import jakarta.persistence.*;


@Entity
@Table(name = "Users")
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    //@Email(message = "Please provide a valid email address")
    private String email;
    @Column(name = "password",nullable = false)
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
/*
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}