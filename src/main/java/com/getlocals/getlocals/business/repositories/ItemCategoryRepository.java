package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, String> {

    List<ItemCategory> findAllByBusiness_Id(String id);

}
