package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.BusinessImage;
import com.getlocals.getlocals.business.entities.BusinessReview;
import com.getlocals.getlocals.business.repositories.BusinessImageRepository;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.BusinessReviewRepository;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
public class BusinessReviewService {

    @Autowired
    private BusinessReviewRepository businessReviewRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessImageRepository imageRepository;


    public ResponseEntity<?> createBusinessReview(DTO.BusinessReviewDTO reviewDTO, String businessId) {
        BusinessImage image = null;
        if (reviewDTO.getImageId() != null) {
            image = imageRepository.getReferenceById(reviewDTO.getImageId());
        }
        var review = BusinessReview.builder()
                .email(reviewDTO.getEmail())
                .comment(reviewDTO.getComment())
                .fullName(reviewDTO.getFullName())
                .rating(reviewDTO.getRating())
                .phone(reviewDTO.getPhone())
                .employeeName(reviewDTO.getEmployeeName())
                .business(businessRepository.getReferenceById(businessId))
                .image(image)
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
        var businessReviews = businessReviewRepository.findAllByBusiness_IdOrderByCreatedAtDesc(businessId, pageable);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd' 'MMMM', 'yyyy", Locale.ENGLISH);

        return ResponseEntity.ok(businessReviews.stream().map(review -> DTO.BusinessReviewDTO.builder()
                .id(review.getId())
                .fullName(review.getFullName())
                .email(review.getEmail())
                .comment(review.getComment())
                .rating(review.getRating())
                .imageId(review.getImage() != null ? review.getImage().getId() : null)
                .phone(review.getPhone())
                .date(dateFormat.format(review.getCreatedAt()))
                .build()));
    }
}
