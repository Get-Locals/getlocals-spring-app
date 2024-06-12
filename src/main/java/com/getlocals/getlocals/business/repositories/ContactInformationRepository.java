package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, String> {

    Optional<ContactInformation> getContactInformationByBusiness_Id(String businessId);
}
