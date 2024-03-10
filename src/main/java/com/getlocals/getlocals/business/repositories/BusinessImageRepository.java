package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.BusinessImage;
import com.getlocals.getlocals.utils.CustomEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessImageRepository extends JpaRepository<BusinessImage, String> {

    List<BusinessImage> getBusinessImagesByBusiness_IdAndType(String id, CustomEnums.BusinessImageTypeEnum type);
}
