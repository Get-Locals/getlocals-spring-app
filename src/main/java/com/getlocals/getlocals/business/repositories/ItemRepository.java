package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> getAllByBusiness_IdAndCategory_Id(String businessId, String categoryId);
}
