package com.github.mcordemans.contactmanager.controllers;

import com.github.mcordemans.contactmanager.resources.address.AddressCreationResource;
import com.github.mcordemans.contactmanager.resources.address.AddressResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyModificationResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyResource;
import com.github.mcordemans.contactmanager.services.CompanyService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/companies", produces = {APPLICATION_JSON_VALUE})
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping
    private List<CompanyResource> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    private CompanyResource getById(@PathVariable UUID id) {
        return companyService.getById(id);
    }

    @PostMapping
    private CompanyResource create(@Valid @RequestBody CompanyModificationResource resource) {
        return companyService.create(resource);
    }

    @PutMapping("/{id}")
    private CompanyResource update(@PathVariable UUID id, @Valid @RequestBody CompanyModificationResource resource) {
        return companyService.update(id, resource);
    }

    @PostMapping("/{id}/addresses")
    private AddressResource addAddress(@PathVariable UUID id, @Valid @RequestBody AddressCreationResource resource) {
        return companyService.addAddress(id, resource);
    }

    @DeleteMapping("/{id}/addresses/{idAddress}")
    private void removeAddress(@PathVariable UUID id, @PathVariable UUID idAddress) {
        companyService.removeAddress(id, idAddress);
    }

    @GetMapping("/{id}/addresses")
    private List<AddressResource> getAddresses(@PathVariable UUID id) {
        return companyService.getAddresses(id);
    }
}
