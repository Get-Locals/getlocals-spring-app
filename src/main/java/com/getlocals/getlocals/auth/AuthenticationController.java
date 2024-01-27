package com.getlocals.getlocals.auth;

import com.getlocals.getlocals.utils.DTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/")
    public ResponseEntity<AuthenticationResponse> createUser(
            @RequestBody DTO.UserRegisterDTO registerDTO
            ) {
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("/authenticate/")
    public ResponseEntity<AuthenticationResponse> authenticateUser(
            @RequestBody DTO.UserAuthDTO authDTO
            ) {
        return ResponseEntity.ok(authService.authenticate(authDTO));
    }

    @PostMapping("/refresh-token/")
    public ResponseEntity<AuthenticationResponse> refreshAuthToken(
            @RequestParam String refreshToken
    ) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/validate_token")
    public ResponseEntity<Boolean> validate_token(
            @RequestParam String token
    ) {
        return ResponseEntity.ok(authService.validate_token(token));
    }


}
