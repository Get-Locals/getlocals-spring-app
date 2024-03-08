package com.getlocals.getlocals.business;

import com.getlocals.getlocals.auth.AuthenticationResponse;
import com.getlocals.getlocals.business.services.BusinessService;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

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
}
