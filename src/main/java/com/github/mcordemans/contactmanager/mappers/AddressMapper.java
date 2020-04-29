package com.github.mcordemans.contactmanager.mappers;

import com.github.mcordemans.contactmanager.domain.Address;
import com.github.mcordemans.contactmanager.resources.address.AddressCreationResource;
import com.github.mcordemans.contactmanager.resources.address.AddressResource;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressCreationResource resource) {
        return new Address(
                resource.getStreet(),
                resource.getNumber(),
                resource.getCity(),
                resource.getZipCode(),
                resource.getCountry(),
                resource.isMainAddress()
        );
    }

    public AddressResource toResource(Address address) {
        final AddressResource addressResource = new AddressResource();
        addressResource.setId(address.getId());
        addressResource.setStreet(address.getStreet());
        addressResource.setNumber(address.getNumber());
        addressResource.setCity(address.getCity());
        addressResource.setZipCode(address.getZipCode());
        addressResource.setCountry(address.getCountry());
        addressResource.setMainAddress(address.isMainAddress());
        return addressResource;
    }
}
