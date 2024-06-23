package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.BusinessImage;
import com.getlocals.getlocals.business.entities.EmployeeInfo;
import com.getlocals.getlocals.business.repositories.BusinessImageRepository;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.EmployeeInfoRepository;
import com.getlocals.getlocals.utils.DTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeInfoService {

    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    private BusinessImageRepository imageRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessImageService businessImageService;


    public ResponseEntity<?> createEmployeeInfo(String businessId, DTO.EmployeeInfoDTO employeeInfoDTO) {
        BusinessImage image = null;
        if (employeeInfoDTO.getImageId() != null) {
            image = imageRepository.getReferenceById(employeeInfoDTO.getImageId());
        }
        var employeeInfo = EmployeeInfo.builder()
                .firstName(employeeInfoDTO.getFirstName())
                .lastName(employeeInfoDTO.getLastName())
                .email(employeeInfoDTO.getEmail())
                .phoneNo(employeeInfoDTO.getPhoneNo())
                .description(employeeInfoDTO.getDescription())
                .position(employeeInfoDTO.getPosition())
                .businessImage(image)
                .business(businessRepository.getReferenceById(businessId)).build();

        employeeInfoRepository.save(employeeInfo);
        return ResponseEntity.ok(
                DTO.StringMessage.builder()
                        .message("Successfully created employee")
                        .build()
        );
    }

    public ResponseEntity<?> updateEmployeeInfo(String businessId, DTO.EmployeeInfoDTO employeeInfoDTO) {
        EmployeeInfo info = employeeInfoRepository.findEmployeeInfoByIdAndBusiness_Id(employeeInfoDTO.getId(), businessId).orElseThrow();
        BusinessImage image = null;
        if (employeeInfoDTO.getImageId() != null) {
            image = imageRepository.getReferenceById(employeeInfoDTO.getImageId());
        } else {
            image = info.getBusinessImage();
        }

        info.setEmail(employeeInfoDTO.getEmail());
        info.setPhoneNo(employeeInfoDTO.getPhoneNo());
        info.setDescription(employeeInfoDTO.getDescription());
        info.setFirstName(employeeInfoDTO.getFirstName());
        info.setLastName(employeeInfoDTO.getLastName());
        info.setPosition(employeeInfoDTO.getPosition());
        info.setBusinessImage(image);

        employeeInfoRepository.save(info);
        return null;
    }

    public ResponseEntity<?> getEmployees(String businessId) {
        List<EmployeeInfo> employees = employeeInfoRepository.findAllByBusiness_Id(businessId);
        var result = employees.stream().map(employee -> {
            BusinessImage image = Optional.of(employee.getBusinessImage()).orElse(null);
            return DTO.EmployeeInfoDTO.builder()
                    .id(employee.getId())
                    .lastName(employee.getLastName())
                    .firstName(employee.getFirstName())
                    .imageId(image.getId())
                    .imageDTO(businessImageService.getImageData(image))
                    .position(employee.getPosition())
                    .email(employee.getEmail())
                    .phoneNo(employee.getPhoneNo())
                    .description(employee.getDescription()).build();
        }
        ).toList();
        return ResponseEntity.ok(result);
    }

    @Transactional
    public ResponseEntity<?> deleteEmployee(String businessId, String employeeId) {
        employeeInfoRepository.deleteByBusiness_IdAndId(businessId, employeeId);
        return ResponseEntity.ok(DTO.StringMessage.builder()
                .message("Successfully Deleted the user").build());
    }
}
