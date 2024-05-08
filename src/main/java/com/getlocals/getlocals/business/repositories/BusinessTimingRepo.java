package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.BusinessTimings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessTimingRepo extends JpaRepository<BusinessTimings, String> {

    Optional<BusinessTimings> getBusinessTimingsByBusiness_Id(String id);
}
