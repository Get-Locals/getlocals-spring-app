package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.Business;
import com.getlocals.getlocals.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, String> {

    List<Business> getBusinessesByOwnerOrManager(User owner, User manager);
}
