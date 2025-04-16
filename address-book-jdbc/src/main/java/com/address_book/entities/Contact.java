package com.address_book.entities;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Contact {
    private String firstName, lastName, address, city, state, zip, phone, email, type;

    public Contact(String firstName, String lastName, String address, String city, String state,
                   String zip, String phone, String email, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.type = type;
    }

    @Override
    public String toString() {
        return firstName + ", " + lastName + ", " + city + ", " + state + ", " + phone;
    }
}
