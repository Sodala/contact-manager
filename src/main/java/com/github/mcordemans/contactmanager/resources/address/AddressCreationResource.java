package com.github.mcordemans.contactmanager.resources.address;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressCreationResource {
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String country;
    private boolean mainAddress;
}
