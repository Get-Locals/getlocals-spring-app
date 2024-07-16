package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.BusinessTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessTemplateRepo extends JpaRepository<BusinessTemplate, String> {

    Optional<BusinessTemplate> getBusinessTemplateByBusiness_BusinessUsername(String username);
}
