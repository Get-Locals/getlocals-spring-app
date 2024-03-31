package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.business.entities.Item;
import com.getlocals.getlocals.business.repositories.BusinessImageRepository;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.ItemCategoryRepository;
import com.getlocals.getlocals.business.repositories.ItemRepository;
import com.getlocals.getlocals.utils.DTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private BusinessImageRepository businessImageRepository;


    @Transactional
    public ResponseEntity<?> createOrUpdateItem(String businessId, String categoryId, DTO.MenuDTO itemDTO) {
        Item item = null;
        if (itemDTO.getId() != null) {
            item = itemRepository.getReferenceById(itemDTO.getId());
            item.setImage(businessImageRepository.getReferenceById(itemDTO.getImageId()));
        } else {
            item = Item.builder()
                    .price(itemDTO.getPrice())
                    .name(itemDTO.getName())
                    .currency("$")
                    .ingredients(itemDTO.getIngredients())
                    .description(itemDTO.getDescription())
                    .category(itemCategoryRepository.getReferenceById(categoryId))
                    .image(businessImageRepository.getReferenceById(itemDTO.getImageId()))
                    .build();
        }
        itemRepository.save(item);

        itemDTO.setId(item.getId());
        return ResponseEntity.ok(itemDTO);
    }

    public ResponseEntity<?> getItems(String businessId, String categoryId) {
        List<Item> items = itemRepository.getAllByCategory_Id(categoryId);

        var res = items.stream().map(item -> {
            DTO.BusinessImageDTO imageDTO = null;

            if (item.getImage() != null) {
                var image = businessImageRepository.getReferenceById(item.getImage().getId());
                byte[] imageData = new byte[0];
                try {
                    imageData = image.getImageData().getBytes(1, (int) image.getImageData().length());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String base64ImageData = Base64.getEncoder().encodeToString(imageData);
                imageDTO = DTO.BusinessImageDTO.builder()
                        .image(base64ImageData)
                        .extension(image.getExtension())
                        .id(image.getId())
                        .name(image.getName())
                        .build();
            }

            return DTO.MenuDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .ingredients(item.getIngredients())
                    .currency(item.getCurrency())
                    .description(item.getDescription())
                    .imageId(item.getId())
                    .image(imageDTO).build();
        }).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    public ResponseEntity<?> deleteItem(String itemId) {
        itemRepository.deleteById(itemId);
        return ResponseEntity.ok(
                DTO.StringMessage.builder()
                        .message("Successfully Deleted Item")
                        .build()
        );
    }
}
