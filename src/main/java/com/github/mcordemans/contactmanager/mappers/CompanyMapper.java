package com.github.mcordemans.contactmanager.mappers;

import com.github.mcordemans.contactmanager.domain.Company;
import com.github.mcordemans.contactmanager.resources.company.CompanyModificationResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyResource;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {



    public CompanyResource toResource(Company company) {
        CompanyResource companyResource = new CompanyResource();
        companyResource.setId(company.getId());
        companyResource.setName(company.getName());
        companyResource.setVatNumber(company.getVatNumber());
        return companyResource;
    }

    public Company toEntity(CompanyModificationResource resource) {
        return new Company(resource.getName(), resource.getVatNumber());
    }

    public Company toEntity(CompanyModificationResource resource, Company company) {
        company.setName(resource.getName());
        company.setVatNumber(resource.getVatNumber());
        return company;
    }
}
