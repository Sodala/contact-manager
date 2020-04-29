package com.github.mcordemans.contactmanager.resources.company.validator;

import com.github.mcordemans.contactmanager.resources.address.AddressCreationResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyModificationResource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompanyCreationValidator implements ConstraintValidator<ValidCompanyModificationResource, CompanyModificationResource> {

    /**
     * an company should have one main address.
     */
    public boolean isValid(CompanyModificationResource company, ConstraintValidatorContext context) {
        final var numberOfMainAddress = company.getAddresses().stream()
                .filter(AddressCreationResource::isMainAddress)
                .count();

        if(numberOfMainAddress == 0) {
            context.buildConstraintViolationWithTemplate("company should have at least one main address")
                    .addConstraintViolation();
            return false;
        }
        if(numberOfMainAddress > 1){
            context.buildConstraintViolationWithTemplate("company should have only one main address")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}