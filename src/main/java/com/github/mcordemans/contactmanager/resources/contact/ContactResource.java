package com.github.mcordemans.contactmanager.resources.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResource {
    private UUID id;
    private String firstName;
    private String lastName;
    private ContactTypeResource type;
    private String vatNumber;
}
