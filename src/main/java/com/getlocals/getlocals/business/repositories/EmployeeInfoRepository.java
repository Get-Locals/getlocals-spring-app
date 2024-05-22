package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, String> {
}
