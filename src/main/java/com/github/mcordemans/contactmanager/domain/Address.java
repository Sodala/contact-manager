package com.github.mcordemans.contactmanager.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = "ADDRESSES")
@ToString(exclude = { "contact", "company"})
public class Address {
    @Id
    private UUID id;
    private String street;
    private String number;
    private String city;
    private String zipCode;
    private String country;
    private boolean mainAddress;

    protected Address() {
        id = UUID.randomUUID();
    }

    public Address(String street,
                   String number,
                   String city,
                   String zipCode,
                   String country,
                   boolean mainAddress) {
        this();
        this.street = street;
        this.number = number;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.mainAddress = mainAddress;
    }

    @OneToOne
    private Contact contact;
}
