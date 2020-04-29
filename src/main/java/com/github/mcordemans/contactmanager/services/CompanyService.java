package com.github.mcordemans.contactmanager.services;

import com.github.mcordemans.contactmanager.domain.Address;
import com.github.mcordemans.contactmanager.domain.Company;
import com.github.mcordemans.contactmanager.mappers.AddressMapper;
import com.github.mcordemans.contactmanager.mappers.CompanyMapper;
import com.github.mcordemans.contactmanager.mappers.ContactMapper;
import com.github.mcordemans.contactmanager.repositories.AddressRepository;
import com.github.mcordemans.contactmanager.repositories.CompanyRepository;
import com.github.mcordemans.contactmanager.resources.address.AddressCreationResource;
import com.github.mcordemans.contactmanager.resources.address.AddressResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyModificationResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Component
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final AddressMapper addressMapper;

    private final AddressRepository addressRepository;

    private final ContactMapper contactMapper;

    public CompanyService(CompanyRepository companyRepository,
                          CompanyMapper companyMapper,
                          AddressMapper addAddress,
                          AddressRepository addressRepository,
                          ContactMapper contactMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.addressMapper = addAddress;
        this.addressRepository = addressRepository;
        this.contactMapper = contactMapper;
    }

    public List<CompanyResource> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toResource)
                .collect(Collectors.toList());
    }

    public CompanyResource getById(@PathVariable UUID id) {
        return companyRepository.findById(id)
                .map(companyMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @Transactional
    public CompanyResource create(@Valid @RequestBody CompanyModificationResource resource) {
        final Company company = companyMapper.toEntity(resource);
        addAddresses(resource, company);
        return companyMapper.toResource(companyRepository.save(company));
    }

    private Company addAddresses(CompanyModificationResource resource, Company company) {
        final Collection<Address> addresses = resource.getAddresses().stream()
                .map(addressMapper::toEntity)
                .collect(Collectors.toList());
        company.clearAllAddresses();
        company.addAllAddresses(addresses);
        return company;
    }

    @Transactional
    public CompanyResource update(@PathVariable UUID id, @Valid @RequestBody CompanyModificationResource resource) {
        return companyRepository.findById(id)
                .map(company -> companyMapper.toEntity(resource, company))
                .map(company -> addAddresses(resource, company))
                .map(companyRepository::save)
                .map(companyMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public AddressResource addAddress(@PathVariable UUID id, @Valid @RequestBody AddressCreationResource resource) {
        final Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final Address newAddress = addressMapper.toEntity(resource);
        company.addAddress(newAddress);

        if (resource.isMainAddress()) {
            for (Address address : company.getAddresses()) {
                address.setMainAddress(address.equals(newAddress));
            }
        }
        companyRepository.save(company);
        return addressMapper.toResource(newAddress);
    }

    @Transactional
    public void removeAddress(@PathVariable UUID id, @PathVariable UUID idAddress) {
        final Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final Address address = addressRepository.findById(idAddress)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        company.removeAddress(address);
        companyRepository.save(company);
    }

    public List<AddressResource> getAddresses(@PathVariable UUID id) {
        final Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return company.getAddresses().stream()
                .map(addressMapper::toResource)
                .collect(Collectors.toList());
    }

    public List<ContactResource> getContacts(@PathVariable UUID id) {
        final Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return company.getContacts().stream()
                .map(contactMapper::toResource)
                .collect(Collectors.toList());
    }
}
