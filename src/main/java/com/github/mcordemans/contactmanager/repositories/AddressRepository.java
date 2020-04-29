package com.github.mcordemans.contactmanager.repositories;

import com.github.mcordemans.contactmanager.domain.Address;
import com.github.mcordemans.contactmanager.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
