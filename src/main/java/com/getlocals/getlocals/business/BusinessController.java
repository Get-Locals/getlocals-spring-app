package com.getlocals.getlocals.business;

import com.getlocals.getlocals.auth.AuthenticationResponse;
import com.getlocals.getlocals.business.services.*;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private BusinessImageService businessImageService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private BusinessReviewService businessReviewService;

    @PostMapping("/register/")
    public ResponseEntity<AuthenticationResponse> registerBusiness(
            @RequestBody DTO.BusinessRegisterDTO businessRegisterDTO,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken
    ) {

        return ResponseEntity.ok(businessService.registerBusiness(
                businessRegisterDTO,
                authToken.substring(7)
        ));
    }

    @PostMapping("/items/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER')")
    public ResponseEntity<String> createMenuItems(@RequestBody DTO.AddItemBusinessDTO items) {
        return ResponseEntity.ok(businessService.addMenuItems(items));
    }

    @GetMapping("/{id}/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER')")
    public ResponseEntity<DTO.BusinessRegisterDTO> getBusiness(@PathVariable("id") String businessId) {
        return ResponseEntity.ok(businessService.getBusinessById(businessId));
    }

    @GetMapping("/types/")
    public ResponseEntity<List<DTO.BusinessTypeDTO>> getItems() {
        return ResponseEntity.ok(businessService.getTypes());
    }

    @PostMapping("/types/")
    public ResponseEntity<String> createItems(@RequestParam(required = false) String item) {
        businessService.createTypes(item);
        return ResponseEntity.ok("Created");
    }

    @PutMapping("/about-us/{businessId}/")
    public ResponseEntity<?> updateAboutUsBusiness(@RequestParam String aboutUs,
                                                   @PathVariable("businessId") String id) {
        return businessService.updateBusiness(aboutUs, id);
    }

    @PostMapping(value = "/{id}/upload/{type}/")
    public ResponseEntity<?> uploadImage(
            @PathVariable("id") String id,
            @RequestPart("file") MultipartFile file,
            @PathVariable("type") CustomEnums.BusinessImageTypeEnum type) throws IOException, SQLException {


        return businessImageService.uploadImage(id, file, type);
    }

    @GetMapping("/{id}/images/{type}/")
    public ResponseEntity<?> getBusinessImages(
            @PathVariable("id") String id,
            @PathVariable("type") CustomEnums.BusinessImageTypeEnum type) {
        return businessImageService.getImages(id, type);
    }

    @DeleteMapping("/image/{id}/")
    public ResponseEntity<?> deleteImage(@PathVariable("id") String id) {
        businessImageService.deleteImage(id);
        return ResponseEntity.ok(DTO.StringMessage
                .builder()
                .message("Deleted Successfully")
                .build());
    }

    @GetMapping("/{id}/item-category/")
    public ResponseEntity<?> getBusinessItemCategories(
            @PathVariable("id") String businessId
    ) {
        return itemCategoryService.getAll(businessId);
    }

    @PostMapping("/{id}/item-category/")
    public ResponseEntity<?> createItemCategory(
            @RequestParam("name") String name,
            @PathVariable("id") String businessId
            ) {

        itemCategoryService.createItemCategory(name, businessId);

        return ResponseEntity.ok(
                DTO.StringMessage.builder()
                        .message("Category Created Successfully!!")
                        .build()
        );
    }

    @DeleteMapping("/{id}/item-category/{categoryId}/")
    public ResponseEntity<?> deleteItemCategory(
            @PathVariable("id") String businessId,
            @PathVariable("categoryId") String categoryId
    ) {
        return itemCategoryService.deleteCategory(businessId, categoryId);
    }

    @PutMapping("/{id}/item-category/{categoryId}/item/")
    public ResponseEntity<?> createMenuItem(
            @PathVariable("id") String businessId,
            @PathVariable("categoryId") String categoryId,
            @RequestBody() DTO.MenuDTO itemDTO
    ) {
        return itemService.createOrUpdateItem(businessId, categoryId, itemDTO);
    }

    @GetMapping("/{id}/item-category/{categoryId}/item/")
    public ResponseEntity<?> getMenuItems(
            @PathVariable("id") String businessId,
            @PathVariable("categoryId") String categoryId) {
        return itemService.getItems(businessId, categoryId);
    }

    @DeleteMapping("/item/{id}/")
    public ResponseEntity<?> deleteItem(@PathVariable("id") String itemId) {
        return itemService.deleteItem(itemId);
    }

    @GetMapping("/{id}/reviews/")
    public ResponseEntity<?> getBusinessReviews(
            @PathVariable("id") String businessId,
            @PageableDefault() Pageable pageable ) {
        return businessReviewService.getBusinessReviews(businessId, pageable);
    }
}
