package com.getlocals.getlocals.auth;

import com.getlocals.getlocals.config.JWTService;
import com.getlocals.getlocals.config.UserPrincipal;
import com.getlocals.getlocals.role.Role;
import com.getlocals.getlocals.role.RoleRepository;
import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.user.UserRepository;
import com.getlocals.getlocals.utils.DTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AuthenticationResponse register(DTO.UserRegisterDTO registerDTO) {
        Role userRole = roleRepository.findByRole("USER");
        var user = User.builder()
                .isActive(false)
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(encoder.encode(registerDTO.getPassword()))
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        return getAuthToken(user);

    }

    public AuthenticationResponse authenticate(DTO.UserAuthDTO authDTO) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDTO.getEmail(),
                            authDTO.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var user = userRepository.findByEmail(authDTO.getEmail());
        return getAuthToken(user);

    }


    public AuthenticationResponse refreshToken(String refreshToken) {
        var email = jwtService.extractEmail(refreshToken);
        var user = userRepository.findByEmail(email);
        return getAuthToken(user);
    }

    private AuthenticationResponse getAuthToken(User user) {
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("roles", user.getRolesString());
        extraClaims.put("name", user.getName());
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(new UserPrincipal(user), extraClaims, DAY))
                .refreshToken(jwtService.generateToken(new UserPrincipal(user), extraClaims, DAY * 2))
                .build();
    }

    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return principal.getUsername();
    }
}
