package com.getlocals.getlocals.user;

import com.getlocals.getlocals.auth.AuthService;
import com.getlocals.getlocals.business.entities.Business;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.services.BusinessService;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private BusinessRepository businessRepository;

    public DTO.UserDTO getLoggedInUser() {

        var user = userRepository.findByEmail(authService.getLoggedInUserEmail()).get();
        return user.getUserDTO();
    }

    public User getLoggedInUserEntity() {
        return userRepository.findByEmail(authService.getLoggedInUserEmail()).get();
    }

    public ResponseEntity<?> getUserProfile() {
        User loggedInUser = this.getLoggedInUserEntity();
        List<DTO.BusinessInfoDTO> userBusinesses = businessRepository.getBusinessesByOwnerOrManager(loggedInUser, loggedInUser)
                .stream().map(business -> DTO.BusinessInfoDTO.builder()
                        .name(business.getName())
                        .id(business.getId()).build()
                ).toList();

        return ResponseEntity.ok(DTO.UserProfileDTO.builder()
                .user(loggedInUser.getUserDTO())
                .businesses(userBusinesses).build());
    }
}
