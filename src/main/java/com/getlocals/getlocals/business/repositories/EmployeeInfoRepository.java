package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.EmployeeInfo;
import com.getlocals.getlocals.utils.CustomEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, String> {

    Optional<EmployeeInfo> findEmployeeInfoByIdAndBusiness_Id(String id, String businessId);

    List<EmployeeInfo> findAllByBusiness_Id(String businessId);

    List<EmployeeInfo> findAllByBusiness_IdAndPosition(String businessId, CustomEnums.BusinessEmployeeTypeEnum position);

    void deleteByBusiness_IdAndId(String businessId, String id);

    Optional<EmployeeInfo> getEmployeeInfoByBusinessImage_Id(String imageId);

}
