package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.BusinessImage;
import com.getlocals.getlocals.business.repositories.BusinessImageRepository;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
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
                .map(image -> {
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
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    public void deleteImage(String id) {
        businessImageRepository.deleteById(id);
    }
}
