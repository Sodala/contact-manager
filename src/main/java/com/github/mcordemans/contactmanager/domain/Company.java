package com.github.mcordemans.contactmanager.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Companies")
@ToString(exclude = { "addresses", "contacts"})
@Data
public class Company {
    @Id
    private UUID id;
    private String name;
    private String vatNumber;

    @ManyToMany
    @JoinTable(name = "companies_contacts",
            joinColumns = @JoinColumn(name="company_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="contact_id", referencedColumnName="id"))
    private Set<Contact> contacts = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Set<Address> addresses = new HashSet<>();

    public void addAddress(Address address) {
        if (address != null) {
            this.addresses.add(address);
        }
    }

    public void addAllAddresses(Collection<Address> address) {
        if (address != null) {
            this.addresses.addAll(address);
        }
    }

    public void clearAllAddresses(){
        this.addresses.clear();
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }


    protected Company() {
        id = UUID.randomUUID();
    }

    public Company(String name, String vatNumber) {
       this();
       this.name = name;
       this.vatNumber = vatNumber;
    }


    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }
}
