package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.ContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRequestRepository extends JpaRepository<ContactRequest, String> {

    List<ContactRequest> getAllByBusiness_Id(String id);
}
