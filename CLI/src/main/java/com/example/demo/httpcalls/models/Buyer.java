package com.example.demo.httpcalls.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends User{
    public Buyer(String name, String email, String password){
        super(name,email,password);
    }
    public Buyer(){}

}
