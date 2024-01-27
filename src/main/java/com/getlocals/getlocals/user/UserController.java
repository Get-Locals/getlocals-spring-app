package com.getlocals.getlocals.user;

import com.getlocals.getlocals.utils.DTO;
    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<DTO.UserDTO> getLoggedInUser() {
        return ResponseEntity.ok(userService.getLoggedInUser());
    }

}
