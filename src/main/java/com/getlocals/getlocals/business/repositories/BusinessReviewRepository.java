package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.BusinessReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessReviewRepository extends JpaRepository<BusinessReview, String> {

    Page<BusinessReview> findAllByBusiness_IdOrderByCreatedAtDesc(String businessId, Pageable pageable);
}
