package com.github.mcordemans.contactmanager.resources.company;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyResource {
    private UUID id;
    private String name;
    private String vatNumber;
}
