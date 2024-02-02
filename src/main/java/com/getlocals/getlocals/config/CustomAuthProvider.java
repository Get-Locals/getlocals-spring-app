package com.getlocals.getlocals.config;

import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.user.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomAuthProvider extends DaoAuthenticationProvider {

    private final UserRepository userRepository;

    @SuppressWarnings("unused")
    private MyUserDetailsService userDetailsService;

    public CustomAuthProvider(UserRepository userRepository, UserDetailsService userDetailsService){
        this.setUserDetailsService(userDetailsService);
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        final Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
