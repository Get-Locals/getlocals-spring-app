package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> getAllByCategory_Id(String categoryId);
    void deleteAllByCategory_Id(String categoryId);

    Optional<Item> getItemByImage_Id(String imageId);
}
