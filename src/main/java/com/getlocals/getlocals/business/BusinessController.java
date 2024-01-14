package com.getlocals.getlocals.business;

import com.getlocals.getlocals.auth.AuthenticationResponse;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerBusiness(
            @RequestBody DTO.BusinessRegisterDTO businessRegisterDTO,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken
    ) {

        return ResponseEntity.ok(businessService.registerBusiness(
                businessRegisterDTO,
                authToken.substring(7)
        ));
    }

    @PostMapping("/items")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER')")
    public ResponseEntity<String> createMenuItems(
            @RequestBody DTO.AddItemBusinessDTO items
    ) {
        return ResponseEntity.ok(businessService.addMenuItems(items));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER')")
    public ResponseEntity<Business> getBusiness(@PathVariable("id") String businessId) {
        return ResponseEntity.ok(businessService.getBusinessById(businessId));
    }
}
