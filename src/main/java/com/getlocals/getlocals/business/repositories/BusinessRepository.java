package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, String> {
}
