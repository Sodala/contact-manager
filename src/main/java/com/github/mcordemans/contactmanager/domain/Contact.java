package com.github.mcordemans.contactmanager.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Contacts")
@ToString(exclude = { "address", "companies"})
@Data
public class Contact {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;
//    private Address address;
    @Column(name = "first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;

    @Column(name ="type")
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @Column(name ="vat_number")
    private String vatNumber;

    @ManyToMany(mappedBy = "contacts")
    private Set<Company> companies = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "contact")
    private Address address;

    protected Contact() {
        this.id = UUID.randomUUID();
    }

    public Contact(String firstName, String lastName, ContactType type, String vatNumber) {
        this();
        Assert.notNull(type, "type cannot be null");
        if(type == ContactType.FREELANCE && Strings.isBlank(vatNumber)){
            throw new IllegalArgumentException("contact cannot be freelance without vatNumber");
        }
        if(type == ContactType.SALARY && vatNumber != null){
            throw new IllegalArgumentException("contact cannot be salary with vat number");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.vatNumber = vatNumber;
    }

    public void setAddress(Address address){
        if (address != null) {
            address.setContact(this);
        }
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
