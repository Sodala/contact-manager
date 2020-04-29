package com.github.mcordemans.contactmanager;

import com.github.mcordemans.contactmanager.domain.Address;
import com.github.mcordemans.contactmanager.domain.Company;
import com.github.mcordemans.contactmanager.domain.Contact;
import com.github.mcordemans.contactmanager.domain.ContactType;
import com.github.mcordemans.contactmanager.repositories.AddressRepository;
import com.github.mcordemans.contactmanager.repositories.CompanyRepository;
import com.github.mcordemans.contactmanager.repositories.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactManagerApplication.class, args);
	}


	@Bean
    public CommandLineRunner init (
            CompanyRepository companyRepository,
            ContactRepository contactRepository) {
	    return args -> {
            Company c1 = new Company("c1", "c1vat");
            Company c2 = new Company("c2", "c2vat");

            Contact contact = new Contact("me", "doe", ContactType.SALARY, null);

            c2.addContact(contact);

            Address a1 = new Address(
                    "avenue des evaux",
                    "45",
                    "ottignies",
                    "1341",
                    "BE",
                    false)
                    ;

            Address a2 = new Address(
                    "avenue des evaux",
                    "41",
                    "ottignies",
                    "1341",
                    "BE",
                    false)
                    ;

            Address a3 = new Address(
                    "avenue des evaux",
                    "41",
                    "ottignies",
                    "1341",
                    "BE",
                    false)
                    ;



            c1.addAddress(a1);
            c1.addAddress(a2);
            contact.setAddress(a3);
            contactRepository.save(contact);
            companyRepository.saveAll(List.of(c1, c2));
        };
    }

}
