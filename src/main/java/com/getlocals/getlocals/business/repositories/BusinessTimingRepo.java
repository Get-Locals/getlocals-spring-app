package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.BusinessTiming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessTimingRepo extends JpaRepository<BusinessTiming, String> {

    Optional<BusinessTiming> getBusinessTimingsByBusiness_Id(String id);
}
