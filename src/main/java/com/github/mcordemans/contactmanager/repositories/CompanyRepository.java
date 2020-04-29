package com.github.mcordemans.contactmanager.repositories;

import com.github.mcordemans.contactmanager.domain.Company;
import com.github.mcordemans.contactmanager.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
