package com.github.mcordemans.contactmanager.services;

import com.github.mcordemans.contactmanager.domain.Address;
import com.github.mcordemans.contactmanager.domain.Company;
import com.github.mcordemans.contactmanager.domain.Contact;
import com.github.mcordemans.contactmanager.mappers.AddressMapper;
import com.github.mcordemans.contactmanager.mappers.CompanyMapper;
import com.github.mcordemans.contactmanager.mappers.ContactMapper;
import com.github.mcordemans.contactmanager.repositories.CompanyRepository;
import com.github.mcordemans.contactmanager.repositories.ContactRepository;
import com.github.mcordemans.contactmanager.resources.address.AddressResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactAddCompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactModificationResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Component
public class ContactService {

    private final ContactRepository contactRepository;

    private final CompanyRepository companyRepository;

    private final ContactMapper contactMapper;

    private final AddressMapper addressMapper;

    private final CompanyMapper companyMapper;

    public ContactService(ContactRepository contactRepository,
                             CompanyRepository companyRepository,
                             ContactMapper contactMapper,
                             AddressMapper addressMapper,
                             CompanyMapper companyMapper) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.contactMapper = contactMapper;
        this.addressMapper = addressMapper;
        this.companyMapper = companyMapper;
    }

    public List<ContactResource> getAll() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toResource)
                .collect(Collectors.toList());
    }

    public ContactResource getById(UUID id) {
        return contactRepository.findById(id)
                .map(contactMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void deleteById(UUID id) {
        final Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        contactRepository.delete(contact);
    }

    @Transactional
    public ContactResource create(ContactModificationResource resource) {
        final Contact contact = contactMapper.toEntity(resource);
        final Address address = addressMapper.toEntity(resource.getAddress());
        contact.setAddress(address);
        return contactMapper.toResource(contactRepository.save(contact));
    }

    @Transactional
    public ContactResource update(UUID id, ContactModificationResource resource) {
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

    public AddressResource getAddress(UUID id) {
        return contactRepository.findById(id)
                .map(Contact::getAddress)
                .map(addressMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<CompanyResource> getCompanies(UUID id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return contact.getCompanies().stream()
                .map(companyMapper::toResource)
                .collect(Collectors.toList());
    }

    @Transactional
    public CompanyResource addCompany(UUID id, ContactAddCompanyResource contactAddCompanyResource) {
        Company company = companyRepository.findById(contactAddCompanyResource.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        company.addContact(contact);
        return companyMapper.toResource(companyRepository.save(company));
    }

    @Transactional
    public void deleteCompany(UUID id, UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        company.removeContact(contact);
        companyRepository.save(company);
    }
}
