package com.github.mcordemans.contactmanager.resources.contact;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@Data
public class ContactAddCompanyResource {
    @NotNull
    private UUID companyId;
}
