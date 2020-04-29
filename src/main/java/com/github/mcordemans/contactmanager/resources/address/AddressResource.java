package com.github.mcordemans.contactmanager.resources.address;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressResource {
    private UUID id;
    private String street;
    private String number;
    private String city;
    private String zipCode;
    private String country;
    private boolean mainAddress;
}
