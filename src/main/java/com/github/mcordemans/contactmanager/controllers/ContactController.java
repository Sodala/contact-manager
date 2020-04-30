package com.github.mcordemans.contactmanager.controllers;

import com.github.mcordemans.contactmanager.resources.address.AddressResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactAddCompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactModificationResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactResource;
import com.github.mcordemans.contactmanager.services.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/contacts", produces = {APPLICATION_JSON_VALUE})
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<ContactResource> getAll() {
        return contactService.getAll();
    }

    @GetMapping("/{id}")
    public ContactResource getById(@PathVariable UUID id) {
        return contactService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        contactService.deleteById(id);
    }

    @PostMapping
    public ContactResource create(@Valid @RequestBody ContactModificationResource resource) {
        return contactService.create(resource);
    }

    @PutMapping("/{id}")
    public ContactResource update(@PathVariable UUID id, @Valid @RequestBody ContactModificationResource resource) {
        return contactService.update(id, resource);
    }

    @GetMapping("/{id}/address")
    public AddressResource getAddress(@PathVariable UUID id) {
        return contactService.getAddress(id);
    }

    @GetMapping("/{id}/companies")
    public List<CompanyResource> getCompanies(@PathVariable UUID id) {
        return contactService.getCompanies(id);
    }

    @PostMapping("/{id}/companies")
    public CompanyResource addCompany(@PathVariable UUID id, @Valid ContactAddCompanyResource contactAddCompanyResource) {
        return contactService.addCompany(id, contactAddCompanyResource);
    }

    @PostMapping("/{id}/companies/{companyId}")
    public void deleteCompany(@PathVariable UUID id, @PathVariable UUID companyId) {
        contactService.deleteCompany(id, companyId);
    }
}
