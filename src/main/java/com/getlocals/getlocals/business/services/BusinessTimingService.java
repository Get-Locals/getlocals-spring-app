package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.BusinessTimings;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.BusinessTimingRepo;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusinessTimingService {

    @Autowired
    private BusinessTimingRepo businessTimingRepo;

    @Autowired
    private BusinessRepository businessRepository;


    public ResponseEntity<?> updateTimings(String businessId, DTO.BusinessTimingDTO timingDTO) {
        BusinessTimings timings = businessTimingRepo.getBusinessTimingsByBusiness_Id(businessId).orElse(new BusinessTimings());
        timings.setMonday(timingDTO.getMonday());
        timings.setTuesday(timingDTO.getTuesday());
        timings.setWednesday(timingDTO.getWednesday());
        timings.setThursday(timingDTO.getThursday());
        timings.setFriday(timingDTO.getFriday());
        timings.setSaturday(timingDTO.getSaturday());
        timings.setSunday(timingDTO.getSunday());

        if (timings.getBusiness() == null) {
            timings.setBusiness(businessRepository.getReferenceById(businessId));
        }
        businessTimingRepo.save(timings);
        return ResponseEntity.ok(DTO.StringMessage.builder()
                .message("Timings Updated successfully").build());
    }

    public ResponseEntity<?> getBusinessTimings(String businessId) {
        BusinessTimings timings = businessTimingRepo.getBusinessTimingsByBusiness_Id(businessId).get();
        return ResponseEntity.ok(DTO.BusinessTimingDTO.builder()
                .monday(timings.getMonday())
                .tuesday(timings.getTuesday())
                .wednesday(timings.getWednesday())
                .thursday(timings.getThursday())
                .friday(timings.getFriday())
                .saturday(timings.getSaturday())
                .sunday(timings.getSunday())
                .build());
    }
}
