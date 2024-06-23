package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.BusinessImage;
import com.getlocals.getlocals.business.repositories.BusinessImageRepository;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.EmployeeInfoRepository;
import com.getlocals.getlocals.business.repositories.ItemRepository;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessImageService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessImageRepository businessImageRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;


    public ResponseEntity<?> uploadImage(String id, MultipartFile file, CustomEnums.BusinessImageTypeEnum type) throws IOException, SQLException {
        var businessImage = BusinessImage.builder()
                .imageData(new SerialBlob(file.getBytes()))
                .business(businessRepository.getReferenceById(id))
                .name(file.getOriginalFilename())
                .extension(file.getContentType())
                .type(type)
                .build();
        businessImageRepository.save(businessImage);
        return ResponseEntity.ok(
                DTO.StringMessage.builder().message(businessImage.getId()).build()
        );

    }

    public ResponseEntity<?> getImages(String id, CustomEnums.BusinessImageTypeEnum type) {
        List<BusinessImage> images = businessImageRepository.getBusinessImagesByBusiness_IdAndType(id, type);
        List<DTO.BusinessImageDTO> res = images.stream()
                .map(this::getImageData)
                .collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    public void deleteImage(String id) {
        itemRepository.getItemByImage_Id(id).ifPresent(item -> {
            item.setImage(null);
            itemRepository.save(item);
        });
        employeeInfoRepository.getEmployeeInfoByBusinessImage_Id(id).ifPresent(employeeInfo -> {
            employeeInfo.setBusinessImage(null);
            employeeInfoRepository.save(employeeInfo);
        });
        businessImageRepository.deleteById(id);
    }

    public ResponseEntity<?> getImage(String businessId, String imageId) throws RuntimeException {

        BusinessImage image = businessImageRepository.getBusinessImageByBusiness_IdAndId(businessId, imageId).orElse(null);
        return getImageByteResponse(image);
    }

    public DTO.BusinessImageDTO getImageData(BusinessImage image) {
        if (image == null) {
            return null;
        }
        try {
            byte[] imageData = image.getImageData().getBytes(1, (int) image.getImageData().length());
            String base64ImageData = Base64.getEncoder().encodeToString(imageData);

            return DTO.BusinessImageDTO.builder()
                    .id(image.getId())
                    .name(image.getName())
                    .image(base64ImageData)
                    .extension(image.getExtension())
                    .type(image.getType()).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> getLogo(String id) {

        BusinessImage image = businessImageRepository.
                getBusinessImageByBusiness_IdAndType(id, CustomEnums.BusinessImageTypeEnum.LOGO).orElseThrow();
        return ResponseEntity.ok(getImageData(image));
    }

    private ResponseEntity<?> getImageByteResponse(BusinessImage image) {
        if (image != null) {
            try {
                // Return the image data as byte array
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(image.getImageData().getBytes(1, (int) image.getImageData().length()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
