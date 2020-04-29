package com.github.mcordemans.contactmanager.repositories;

import com.github.mcordemans.contactmanager.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
