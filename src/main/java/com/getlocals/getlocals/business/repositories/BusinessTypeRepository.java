package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessTypeRepository extends JpaRepository<BusinessType, String> {
}
