package com.example.demo.httpcalls.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {
   public Manager(String name, String email, String password){
       super(name,email,password);
   }
   public Manager(){}
}
