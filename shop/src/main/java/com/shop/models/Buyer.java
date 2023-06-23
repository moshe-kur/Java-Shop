package com.shop.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends User{

}
