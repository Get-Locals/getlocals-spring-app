package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.BusinessImage;
import com.getlocals.getlocals.utils.CustomEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessImageRepository extends JpaRepository<BusinessImage, String> {

    List<BusinessImage> getBusinessImagesByBusiness_IdAndType(String id, CustomEnums.BusinessImageTypeEnum type);

    Optional<BusinessImage> getBusinessImageByBusiness_IdAndId(String businessId, String id);

    Optional<BusinessImage> getBusinessImageByBusiness_IdAndType(String businessId, CustomEnums.BusinessImageTypeEnum type);
}
