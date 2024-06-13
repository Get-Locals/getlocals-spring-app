package com.getlocals.getlocals.business;

import com.getlocals.getlocals.auth.AuthenticationResponse;
import com.getlocals.getlocals.business.services.*;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private BusinessTimingService businessTimingService;

    @Autowired
    private EmployeeInfoService employeeInfoService;

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
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<String> createMenuItems(@RequestBody DTO.AddItemBusinessDTO items) {
        return ResponseEntity.ok(businessService.addMenuItems(items));
    }

    @GetMapping("/{id}/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<DTO.BusinessRegisterDTO> getBusiness(@PathVariable("id") String businessId) {
        return ResponseEntity.ok(businessService.getBusinessById(businessId));
    }

    @GetMapping("/public/{id}/")
    public ResponseEntity<?> getPublicBusinessInfo(@PathVariable("id") String businessId) {
        return businessService.getPublicBusinessInfo(businessId);
    }

    @GetMapping("/public/types/")
    public ResponseEntity<List<DTO.BusinessTypeDTO>> getItems() {
        return ResponseEntity.ok(businessService.getTypes());
    }

    @PostMapping("/types/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> createItems(@RequestParam(required = false) String item) {
        businessService.createTypes(item);
        return ResponseEntity.ok("Created");
    }

    @PutMapping("/about-us/{businessId}/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> updateAboutUsBusiness(@RequestParam String aboutUs,
                                                   @PathVariable("businessId") String id) {
        return businessService.updateBusiness(aboutUs, id);
    }

    @PostMapping(value = "/{id}/upload/{type}/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> uploadImage(
            @PathVariable("id") String id,
            @RequestPart("file") MultipartFile file,
            @PathVariable("type") CustomEnums.BusinessImageTypeEnum type) throws IOException, SQLException {


        return businessImageService.uploadImage(id, file, type);
    }

    @GetMapping("/public/{id}/logo/")
    public ResponseEntity<?> getBusinessLogo(
            @PathVariable("id") String id
    ) {
        return businessImageService.getLogo(id);
    }

    @GetMapping("/public/{id}/images/{type}/")
    public ResponseEntity<?> getBusinessImages(
            @PathVariable("id") String id,
            @PathVariable("type") CustomEnums.BusinessImageTypeEnum type) {
        return businessImageService.getImages(id, type);
    }

    @GetMapping("/public/{id}/image/{imageId}/")
    public ResponseEntity<?> getImage(
            @PathVariable("id") String businessId,
            @PathVariable("imageId") String imageId
    ) throws RuntimeException {
        return businessImageService.getImage(businessId, imageId);
    }

    @DeleteMapping("/image/{id}/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> deleteImage(@PathVariable("id") String id) {
        businessImageService.deleteImage(id);
        return ResponseEntity.ok(DTO.StringMessage
                .builder()
                .message("Deleted Successfully")
                .build());
    }

    @GetMapping("/public/{id}/item-category/")
    public ResponseEntity<?> getBusinessItemCategories(
            @PathVariable("id") String businessId
    ) {
        return itemCategoryService.getAll(businessId);
    }

    @PostMapping("/{id}/item-category/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> deleteItemCategory(
            @PathVariable("id") String businessId,
            @PathVariable("categoryId") String categoryId
    ) {
        return itemCategoryService.deleteCategory(businessId, categoryId);
    }

    @PutMapping("/{id}/item-category/{categoryId}/item/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> createMenuItem(
            @PathVariable("id") String businessId,
            @PathVariable("categoryId") String categoryId,
            @RequestBody() DTO.MenuDTO itemDTO
    ) {
        return itemService.createOrUpdateItem(businessId, categoryId, itemDTO);
    }

    @GetMapping("/public/{id}/item-category/{categoryId}/item/")
    public ResponseEntity<?> getMenuItems(
            @PathVariable("id") String businessId,
            @PathVariable("categoryId") String categoryId) {
        return itemService.getItems(businessId, categoryId);
    }

    @DeleteMapping("/item/{id}/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> deleteItem(@PathVariable("id") String itemId) {
        return itemService.deleteItem(itemId);
    }

    @GetMapping("/{id}/reviews/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> getBusinessReviews(
            @PathVariable("id") String businessId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return businessReviewService.getBusinessReviews(businessId, pageable);
    }

    @PostMapping("/public/{id}/review/")
    public ResponseEntity<?> createBusinessReview(
            @PathVariable("id") String businessID,
            @RequestBody() DTO.BusinessReviewDTO reviewDTO) {
        return businessReviewService.createBusinessReview(reviewDTO, businessID);
    }

    @PutMapping("/{id}/timing/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> updateBusinessTimings(
            @PathVariable("id") String businessId,
            @RequestBody DTO.BusinessTimingDTO timingDTO
    ) {
        return businessTimingService.updateTimings(businessId, timingDTO);
    }

    @GetMapping("/public/{id}/timings/")
    public ResponseEntity<?> getBusinessTimings(
            @PathVariable("id") String businessId
    ) {
        return businessTimingService.getBusinessTimings(businessId);
    }

    @GetMapping("/public/{id}/business-operation-status/")
    public ResponseEntity<?> getBusinessOperatingStatus(
            @PathVariable("id") String businessId,
            @RequestParam(value = "tomorrow", defaultValue = "false") Boolean tomorrow,
            @RequestParam(value = "today", defaultValue = "false") Boolean today
    ) {
        return businessTimingService.getBusinessOperatingStatus(businessId, tomorrow, today);
    }

    @PutMapping("/{id}/business-operation-status/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> updateBusinessOperatingStatus(
            @PathVariable("id") String businessId,
            @RequestParam(value = "status") String status
    ) {
        return businessTimingService.updateBusinessOperatingStatus(businessId, status);
    }

    @GetMapping("/{id}/contact-requests/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> getAllContactRequests(
            @PathVariable("id") String businessId
    ) {
        return businessService.getAllContactRequests(businessId);
    }

    @PostMapping("/public/{id}/contact-requests/")
    public ResponseEntity<?> createContactRequest(
            @PathVariable("id") String businessId,
            @RequestBody DTO.ContactRequestDTO requestDTO
    ) {
        return businessService.createContactRequest(businessId, requestDTO);
    }

    @PostMapping("/{id}/employee-info/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> createEmployeeInfo(
            @PathVariable("id") String businessId,
            @RequestBody DTO.EmployeeInfoDTO employeeInfoDTO
    ) {
        return employeeInfoService.createEmployeeInfo(businessId, employeeInfoDTO);
    }

    @PutMapping("/{id}/employee-info/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> updateEmployeeInfo(
            @PathVariable("id") String businessId,
            @RequestBody DTO.EmployeeInfoDTO employeeInfoDTO
    ) {
        return employeeInfoService.updateEmployeeInfo(businessId, employeeInfoDTO);
    }

    @GetMapping("/public/{id}/employee-info/")
    public ResponseEntity<?> GetEmployees(
            @PathVariable("id") String businessId
    ) {
        return employeeInfoService.getEmployees(businessId);
    }

    @DeleteMapping("/{id}/employee-info/")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable("id") String businessId,
            @RequestParam("employeeId") String employeeId
    ) {
        return employeeInfoService.deleteEmployee(businessId, employeeId);
    }


    @GetMapping("/public/{businessUsername}/template-information/")
    public ResponseEntity<?> getTemplateInformation(
            @PathVariable("businessUsername") String businessUsername
    ) {
        return businessService.getTemplateInformation(businessUsername);
    }

    @GetMapping("/public/{businessId}/contact/")
    public ResponseEntity<?> getBusinessContactInformation(
            @PathVariable("businessId") String businessId
    ) {
        return businessService.getBusinessContactInformation(businessId);
    }

    @PutMapping("/{businessId}/contact/")
    public ResponseEntity<?> updateBusinessContactInformation(
            @PathVariable("businessId") String businessId,
            @RequestBody DTO.BusinessContactInformation contactInformation
    ) {
        return businessService.createOrUpdateBusinessContactInformation(businessId, contactInformation);
    }
}
