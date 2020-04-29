package com.github.mcordemans.contactmanager.mappers;

import com.github.mcordemans.contactmanager.domain.Contact;
import com.github.mcordemans.contactmanager.domain.ContactType;
import com.github.mcordemans.contactmanager.resources.contact.ContactModificationResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactTypeResource;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactResource toResource(Contact contact) {
        ContactResource resource = new ContactResource();
        resource.setId(contact.getId());
        resource.setFirstName(contact.getFirstName());
        resource.setLastName(contact.getLastName());
        resource.setType(ContactTypeResource.valueOf(contact.getType().name()));
        resource.setVatNumber(contact.getVatNumber());
        return resource;
    }


    public Contact toEntity(ContactModificationResource resource) {
        ContactType contactType = ContactType.valueOf(resource.getType().name());
        return new Contact(resource.getFirstName(), resource.getLastName(), contactType, resource.getVatNumber());
    }

    public Contact toEntity(ContactModificationResource resource, Contact contact) {
        contact.setLastName(resource.getLastName());
        contact.setFirstName(resource.getFirstName());
        contact.setType(ContactType.valueOf(contact.getType().name()));
        contact.setVatNumber(resource.getVatNumber());
        return contact;
    }

}
