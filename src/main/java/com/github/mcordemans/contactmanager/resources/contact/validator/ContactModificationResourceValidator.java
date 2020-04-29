package com.github.mcordemans.contactmanager.resources.contact.validator;

import com.github.mcordemans.contactmanager.resources.contact.ContactTypeResource;
import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ContactModificationResourceValidator implements ConstraintValidator<ValidContact, com.github.mcordemans.contactmanager.resources.contact.ContactModificationResource> {

    /**
     * Validate Freelance must have vat and Salary vat number should be empty
     */
    public boolean isValid(com.github.mcordemans.contactmanager.resources.contact.ContactModificationResource contact, ConstraintValidatorContext context) {
        if (contact.getType() == ContactTypeResource.FREELANCE && Strings.isNullOrEmpty(contact.getVatNumber())) {
            context.buildConstraintViolationWithTemplate("freelance should have vat number")
                    .addConstraintViolation();
            return false;
        }
        return contact.getType() != ContactTypeResource.SALARY || Strings.isNullOrEmpty(contact.getVatNumber());
    }

}
