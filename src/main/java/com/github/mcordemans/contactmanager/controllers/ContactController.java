package com.github.mcordemans.contactmanager.controllers;

import com.github.mcordemans.contactmanager.domain.Address;
import com.github.mcordemans.contactmanager.domain.Contact;
import com.github.mcordemans.contactmanager.mappers.AddressMapper;
import com.github.mcordemans.contactmanager.mappers.CompanyMapper;
import com.github.mcordemans.contactmanager.mappers.ContactMapper;
import com.github.mcordemans.contactmanager.repositories.ContactRepository;
import com.github.mcordemans.contactmanager.resources.address.AddressResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactModificationResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/contacts", produces = {APPLICATION_JSON_VALUE})
public class ContactController {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    private final AddressMapper addressMapper;

    private final CompanyMapper companyMapper;

    public ContactController(ContactRepository contactRepository,
                             ContactMapper contactMapper,
                             AddressMapper addressMapper,
                             CompanyMapper companyMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.addressMapper = addressMapper;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    private List<ContactResource> getAll() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toResource)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private ContactResource getById(@PathVariable UUID id) {
        return contactRepository.findById(id)
                .map(contactMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable UUID id) {
        final Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        contactRepository.delete(contact);
    }

    @PostMapping
    private ContactResource create(@Valid @RequestBody ContactModificationResource resource) {
        final Contact contact = contactMapper.toEntity(resource);
        final Address address = addressMapper.toEntity(resource.getAddress());
        contact.setAddress(address);
        return contactMapper.toResource(contactRepository.save(contact));
    }

    @PutMapping("/{id}")
    private ContactResource update(@PathVariable UUID id, @Valid @RequestBody ContactModificationResource resource) {
        return contactRepository.findById(id)
                .map(contact -> contactMapper.toEntity(resource, contact))
                .map(contact -> {
                    contact.setAddress(addressMapper.toEntity(resource.getAddress()));
                    return contact;
                })
                .map(contactRepository::save)
                .map(contactMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/address")
    private AddressResource getAddress(@PathVariable UUID id) {
        return contactRepository.findById(id)
                .map(Contact::getAddress)
                .map(addressMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/companies")
    private List<CompanyResource> getCompanies(@PathVariable UUID id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return contact.getCompanies().stream()
                .map(companyMapper::toResource)
                .collect(Collectors.toList());
    }
}
