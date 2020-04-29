package com.github.mcordemans.contactmanager.resources.company;

import com.github.mcordemans.contactmanager.resources.address.AddressCreationResource;
import com.github.mcordemans.contactmanager.resources.company.validator.ValidCompanyModificationResource;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ValidCompanyModificationResource
public class CompanyModificationResource {
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    @Size(min = 1, max = 255)
    private String vatNumber;
    @NotNull
    List<AddressCreationResource> addresses;
}
