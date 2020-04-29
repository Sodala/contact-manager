package com.github.mcordemans.contactmanager.resources.contact;

import com.github.mcordemans.contactmanager.resources.address.AddressCreationResource;
import com.github.mcordemans.contactmanager.resources.contact.validator.ValidContact;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ValidContact
public class ContactModificationResource {
    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 100)
    private String lastName;
    @NotNull
    private ContactTypeResource type;
    private String vatNumber;
    @Valid
    @NotNull
    private AddressCreationResource address;
}
