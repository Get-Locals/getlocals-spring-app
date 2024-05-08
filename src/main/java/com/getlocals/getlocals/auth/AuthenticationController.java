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
    public ResponseEntity<?> registerUser(
            @RequestBody DTO.UserRegisterDTO registerDTO
            ) {
        return authService.register(registerDTO, false);
    }

    @PostMapping("/authenticate/")
    public ResponseEntity<?> authenticateUser(
            @RequestBody DTO.UserAuthDTO authDTO
            ) {
        return authService.authenticate(authDTO);
    }

    @PostMapping("/refresh-token/")
    public ResponseEntity<AuthenticationResponse> refreshAuthToken(
            @RequestParam String refreshToken
    ) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/validate-token/{token}/")
    public ResponseEntity<Boolean> validate_token(
            @PathVariable String token
    ) {
        return ResponseEntity.ok(authService.validate_token(token));
    }


}
