package com.getlocals.getlocals.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/all")
    public ResponseEntity<String> createAll() {
        List<String> roles = new ArrayList<>(Arrays.asList(
                "ADMIN", "USER", "MANAGER", "OWNER"
        ));
        for (String userRole : roles) {
            Role role = new Role();
            role.setRole(userRole);
            roleRepository.save(role);
        }
        return new ResponseEntity<>("Created all the roles", HttpStatus.OK);
    }
}
