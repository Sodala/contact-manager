package com.github.mcordemans.contactmanager.services;

import com.github.mcordemans.contactmanager.domain.Address;
import com.github.mcordemans.contactmanager.domain.Company;
import com.github.mcordemans.contactmanager.domain.Contact;
import com.github.mcordemans.contactmanager.domain.ContactType;
import com.github.mcordemans.contactmanager.mappers.AddressMapper;
import com.github.mcordemans.contactmanager.mappers.CompanyMapper;
import com.github.mcordemans.contactmanager.mappers.ContactMapper;
import com.github.mcordemans.contactmanager.repositories.CompanyRepository;
import com.github.mcordemans.contactmanager.repositories.ContactRepository;
import com.github.mcordemans.contactmanager.resources.address.AddressCreationResource;
import com.github.mcordemans.contactmanager.resources.address.AddressResource;
import com.github.mcordemans.contactmanager.resources.company.CompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactAddCompanyResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactModificationResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactResource;
import com.github.mcordemans.contactmanager.resources.contact.ContactTypeResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @InjectMocks
   private ContactService target;

    @Mock
    private ContactRepository contactRepository;
    @Mock
    private CompanyRepository companyRepository;

    @Spy
    private ContactMapper contactMapper;
    @Spy
    private AddressMapper addressMapper;
    @Spy
    private CompanyMapper companyMapper;


    @Test
    void getAll() {
        Contact contact = new Contact("a", "b", ContactType.SALARY, null);
        List<Contact> contacts = List.of(contact);
        when(contactRepository.findAll()).thenReturn(contacts);
        List<ContactResource> all = target.getAll();

        assertThat(all).hasSize(1);
        assertThat(all.get(0).getFirstName()).isEqualTo("a");
        assertThat(all.get(0).getLastName()).isEqualTo("b");
        assertThat(all.get(0).getType()).isEqualTo(ContactTypeResource.SALARY);
        assertThat(all.get(0).getVatNumber()).isNull();

        verify(contactRepository).findAll();
    }

    @Test
    void getById() {

        final Contact contact = new Contact("a", "b", ContactType.SALARY, null);
        final UUID uuid = UUID.fromString("0027da8c-8aaf-11ea-bc55-0242ac130003");
        contact.setId(uuid);
        when(contactRepository.findById(uuid)).thenReturn(Optional.of(contact));

        ContactResource resource = target.getById(uuid);

        assertThat(resource.getId()).isEqualTo(uuid);
        assertThat(resource.getFirstName()).isEqualTo("a");
        assertThat(resource.getLastName()).isEqualTo("b");
        assertThat(resource.getType()).isEqualTo(ContactTypeResource.SALARY);
        assertThat(resource.getVatNumber()).isNull();

        verify(contactRepository).findById(uuid);
    }

    @Test
    void deleteById() {
        final UUID uuid = UUID.fromString("0027da8c-8aaf-11ea-bc55-0242ac130003");
        final Contact contact = new Contact("a", "b", ContactType.SALARY, null);
        contact.setId(uuid);
        when(contactRepository.findById(uuid)).thenReturn(Optional.of(contact));
        doNothing().when(contactRepository).delete(contact);

        target.deleteById(uuid);

        verify(contactRepository).findById(uuid);
        verify(contactRepository).delete(contact);
    }

    @Test
    void create() {

        ContactModificationResource resourceInput = new ContactModificationResource();
        resourceInput.setFirstName("a");
        resourceInput.setLastName("b");
        resourceInput.setType(ContactTypeResource.SALARY);
        AddressCreationResource addressInput = new AddressCreationResource();
        addressInput.setCity("a");
        addressInput.setCountry("b");
        addressInput.setNumber("4");
        addressInput.setZipCode("4");
        addressInput.setStreet("rue");
        resourceInput.setAddress(addressInput);

        when(contactRepository.save(any())).thenAnswer(returnsFirstArg());
        ContactResource resourceOutput = target.create(resourceInput);

        assertThat(resourceOutput.getId()).isNotNull();
        assertThat(resourceOutput.getFirstName()).isEqualTo("a");
        assertThat(resourceOutput.getLastName()).isEqualTo("b");
        assertThat(resourceOutput.getType()).isEqualTo(ContactTypeResource.SALARY);
        assertThat(resourceOutput.getVatNumber()).isNull();

        verify(contactRepository).save(any());

    }

    @Test
    void update() {
        final UUID uuid = UUID.fromString("0027da8c-8aaf-11ea-bc55-0242ac130003");

        Contact contactInDb = new Contact("c", "d", ContactType.FREELANCE, "something");
        contactInDb.setId(uuid);

        ContactModificationResource resourceInput = new ContactModificationResource();
        resourceInput.setFirstName("a");
        resourceInput.setLastName("b");
        resourceInput.setType(ContactTypeResource.SALARY);
        AddressCreationResource addressInput = new AddressCreationResource();
        addressInput.setCity("a");
        addressInput.setCountry("b");
        addressInput.setNumber("4");
        addressInput.setZipCode("4");
        addressInput.setStreet("rue");
        resourceInput.setAddress(addressInput);
        when(contactRepository.findById(uuid)).thenReturn(Optional.of(contactInDb));
        when(contactRepository.save(any())).thenAnswer(returnsFirstArg());

        ContactResource resourceOutput = target.update(uuid, resourceInput);

        assertThat(resourceOutput.getId()).isNotNull();
        assertThat(resourceOutput.getFirstName()).isEqualTo("a");
        assertThat(resourceOutput.getLastName()).isEqualTo("b");
        assertThat(resourceOutput.getType()).isEqualTo(ContactTypeResource.SALARY);
        assertThat(resourceOutput.getVatNumber()).isNull();

        verify(contactRepository).findById(uuid);
        verify(contactRepository).save(any());
    }

    @Test
    void getAddress() {
        final UUID uuid = UUID.fromString("0027da8c-8aaf-11ea-bc55-0242ac130003");
        final UUID addressUuid = UUID.fromString("2b045f16-8ab2-11ea-bc55-0242ac130003");

        Contact contactInDb = new Contact("c", "d", ContactType.FREELANCE, "something");
        contactInDb.setId(uuid);

        Address addressInDb = new Address("rue", "4", "a", "4", "b",true);
        addressInDb.setId(addressUuid);
        contactInDb.setAddress(addressInDb);

        when(contactRepository.findById(uuid)).thenReturn(Optional.of(contactInDb));

        AddressResource resourceOutput = target.getAddress(uuid);

        assertThat(resourceOutput.getId()).isEqualTo(addressUuid);
        assertThat(resourceOutput.getCity()).isEqualTo("a");
        assertThat(resourceOutput.getStreet()).isEqualTo("rue");
        assertThat(resourceOutput.getZipCode()).isEqualTo("4");
        assertThat(resourceOutput.getCountry()).isEqualTo("b");
    }

    @Test
    void getCompanies() {
        final UUID uuid = UUID.fromString("0027da8c-8aaf-11ea-bc55-0242ac130003");

        Contact contactInDb = new Contact("c", "d", ContactType.FREELANCE, "something");
        Company companyInDb = new Company("name", "vatNrComapany");

        contactInDb.setCompanies(Set.of(companyInDb));

        when(contactRepository.findById(uuid)).thenReturn(Optional.of(contactInDb));

        List<CompanyResource> resourceOutput = target.getCompanies(uuid);
        assertThat(resourceOutput).hasSize(1);
        assertThat(resourceOutput.get(0).getName()).isEqualTo("name");
        assertThat(resourceOutput.get(0).getVatNumber()).isEqualTo("vatNrComapany");
    }

    @Test
    void addCompany() {
        final UUID uuid = UUID.fromString("0027da8c-8aaf-11ea-bc55-0242ac130003");
        final UUID companyUuid = UUID.fromString("a0ec1a38-8ab3-11ea-bc55-0242ac130003");

        Contact contactInDb = new Contact("c", "d", ContactType.FREELANCE, "something");
        contactInDb.setId(uuid);
        Company companyInDb = new Company("name", "vatNrComapany");
        companyInDb.setId(companyUuid);


        when(contactRepository.findById(uuid)).thenReturn(Optional.of(contactInDb));
        when(companyRepository.findById(companyUuid)).thenReturn(Optional.of(companyInDb));
        when(companyRepository.save(any())).thenAnswer(returnsFirstArg());

        ContactAddCompanyResource contactAddCompanyResource = new ContactAddCompanyResource();
        contactAddCompanyResource.setCompanyId(companyUuid);
        CompanyResource resourceOutput = target.addCompany(uuid, contactAddCompanyResource);
        assertThat(resourceOutput.getName()).isEqualTo("name");
        assertThat(resourceOutput.getVatNumber()).isEqualTo("vatNrComapany");
    }

    @Test
    void deleteCompany() {
        final UUID uuid = UUID.fromString("0027da8c-8aaf-11ea-bc55-0242ac130003");
        final UUID companyUuid = UUID.fromString("a0ec1a38-8ab3-11ea-bc55-0242ac130003");

        Contact contactInDb = new Contact("c", "d", ContactType.FREELANCE, "something");
        contactInDb.setId(uuid);
        Company companyInDb = new Company("name", "vatNrComapany");
        companyInDb.setId(companyUuid);
        companyInDb.addContact(contactInDb);

        when(contactRepository.findById(uuid)).thenReturn(Optional.of(contactInDb));
        when(companyRepository.findById(companyUuid)).thenReturn(Optional.of(companyInDb));

        when(companyRepository.save(any())).thenAnswer(returnsFirstArg());

        target.deleteCompany(uuid, companyUuid);

        assertThat(contactInDb.getCompanies()).isEmpty();

    }
}