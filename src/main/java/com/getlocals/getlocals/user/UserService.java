package com.getlocals.getlocals.user;

import com.getlocals.getlocals.auth.AuthService;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public DTO.UserDTO getLoggedInUser() {

        var user = userRepository.findByEmail(authService.getLoggedInUserEmail());
        return user.getUserDTO();
    }

    public User getLoggedInUserEntity() {

        return userRepository.findByEmail(authService.getLoggedInUserEmail());
    }
}
