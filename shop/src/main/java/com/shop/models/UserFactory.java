package com.shop.models;

public class UserFactory {
    public User createUser(boolean isManaget) {
        if (isManaget) {
            return new Manager();
        } else {
            return new Buyer();
        }
    }
}
