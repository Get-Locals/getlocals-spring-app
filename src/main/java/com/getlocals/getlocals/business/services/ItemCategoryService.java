package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.ItemCategory;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.ItemCategoryRepository;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCategoryService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    public void createItemCategory(String name, String businessId) {
        var itemCategory = ItemCategory.builder()
                .name(name)
                .business(businessRepository.getReferenceById(businessId))
                .build();

        itemCategoryRepository.save(itemCategory);
    }

    public ResponseEntity<?> getAll(String businessId) {
        List<ItemCategory> itemCategories = itemCategoryRepository.findAllByBusiness_Id(businessId);
        return ResponseEntity.ok(
                itemCategories.stream().map(item -> DTO.ItemCategoryDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .build()).collect(Collectors.toList()));
    }

    public ResponseEntity<?> deleteCategory(String businessId, String categoryId) {
        itemCategoryRepository.deleteById(categoryId);
        return ResponseEntity.ok(
                DTO.StringMessage.builder()
                        .message("Deleted Successfully")
                        .build()
        );
    }
}
