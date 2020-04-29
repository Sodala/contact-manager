package com.github.mcordemans.contactmanager;

import com.github.mcordemans.contactmanager.domain.Contact;
import com.github.mcordemans.contactmanager.domain.ContactType;
import com.github.mcordemans.contactmanager.repositories.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ContactRepositoryIT {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void testFindById() {
        Contact contact = new Contact("firstName", "lastNAme", ContactType.SALARY, null);

        entityManager.persist(contact);

        Optional<Contact> contactInDB = contactRepository.findById(contact.getId());

        assertThat(contactInDB).isPresent();
        assertThat(contactInDB.get().getFirstName()).isEqualTo("firstName");
        assertThat(contactInDB.get().getLastName()).isEqualTo("lastNAme");
    }

}
