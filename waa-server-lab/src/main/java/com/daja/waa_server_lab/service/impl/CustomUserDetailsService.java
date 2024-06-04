package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.UserPrincipal;
import com.daja.waa_server_lab.exception.UserException;
import com.daja.waa_server_lab.repository.IUserRepository;
import com.daja.waa_server_lab.service.spec.ICustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements ICustomUserDetailsService {
    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findUserByEmail(username).orElseThrow(UserException.NotFoundException::new);
        return new UserPrincipal(foundUser);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new UserException.NotFoundException("No authenticated user found!");
        }

        return userRepository.findUserByEmail(userDetails.getUsername()).orElseThrow(
                () -> new UserException.NotFoundException("Authenticated user not found in database!")
        );
    }
}
