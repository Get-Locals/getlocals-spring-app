package com.getlocals.getlocals.auth;

import com.getlocals.getlocals.config.JWTService;
import com.getlocals.getlocals.config.UserPrincipal;
import com.getlocals.getlocals.role.Role;
import com.getlocals.getlocals.role.RoleRepository;
import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.user.UserRepository;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final Long DAY = (long) (1000*60*60*24);

    public ResponseEntity<?> register(DTO.UserRegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>("User already exists with that email", HttpStatus.CONFLICT);
        }
        Role userRole = roleRepository.findByRole(CustomEnums.RolesEnum.USER.getVal());
        var user = User.builder()
                .isActive(false)
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(encoder.encode(registerDTO.getPassword()))
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(getAuthToken(user));

    }

    public ResponseEntity<?> authenticate(DTO.UserAuthDTO authDTO) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDTO.getEmail(),
                            authDTO.getPassword()
                    )
            );
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        var user = userRepository.findByEmail(authDTO.getEmail()).get();
        return ResponseEntity.ok(getAuthToken(user));

    }


    public AuthenticationResponse refreshToken(String refreshToken) {
        var email = jwtService.extractEmail(refreshToken);
        var user = userRepository.findByEmail(email);
        return getAuthToken(user.get());
    }

    private AuthenticationResponse getAuthToken(User user) {
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("scope", user.getRolesString());
        extraClaims.put("name", user.getName());
        return AuthenticationResponse.builder()
                .access(jwtService.generateToken(new UserPrincipal(user), extraClaims, DAY))
                .refresh(jwtService.generateToken(new UserPrincipal(user), extraClaims, DAY * 7))
                .name(user.getName())
                .username(user.getEmail())
                .build();
    }

    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return principal.getUsername();
    }

    public Boolean validate_token(String token) {
        return jwtService.validateToken(token, getLoggedInUserEmail());
    }
}
