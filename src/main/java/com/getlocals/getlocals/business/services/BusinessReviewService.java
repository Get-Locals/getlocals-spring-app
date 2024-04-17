package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.BusinessReview;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.BusinessReviewRepository;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusinessReviewService {

    @Autowired
    private BusinessReviewRepository businessReviewRepository;

    @Autowired
    private BusinessRepository businessRepository;


    public ResponseEntity<?> createBusinessReview(DTO.BusinessReviewDTO reviewDTO, String businessId) {
        var review = BusinessReview.builder()
                .email(reviewDTO.getEmail())
                .comment(reviewDTO.getComment())
                .fullName(reviewDTO.getFullName())
                .rating(reviewDTO.getRating())
                .business(businessRepository.getReferenceById(businessId))
                .build();
        try {
            businessReviewRepository.save(review);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(DTO.StringMessage.builder()
                .message("Thank you for the review")
                .build());

    }
    public ResponseEntity<?> getBusinessReviews(String businessId, Pageable pageable) {
        var businessReviews = businessReviewRepository.findAllByBusiness_Id(businessId, pageable);

        return ResponseEntity.ok(businessReviews);
    }
}
