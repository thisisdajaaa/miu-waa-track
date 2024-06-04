package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.entity.Role;
import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.dto.request.LoginDto;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.AuthenticationDetailDto;
import com.daja.waa_server_lab.exception.RoleException;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.repository.IRoleRepository;
import com.daja.waa_server_lab.repository.IUserRepository;
import com.daja.waa_server_lab.service.spec.IAuthenticationService;
import com.daja.waa_server_lab.service.spec.IJWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final IJWTService jwtService;

    public AuthenticationServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, IJWTService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticationDetailDto register(UserDto createUserDto) {
        User user = User.builder()
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .build();

        List<Role> roles = createUserDto.getRoleIds().stream()
                .map(roleRepository::findById)
                .map(optionalRole -> optionalRole.orElseThrow(RoleException.NotFoundException::new))
                .collect(Collectors.toList());
        user.setRoles(roles);

        userRepository.save(user);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String jwtToken = jwtService.generateToken(userDetails);
        final String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthenticationDetailDto(jwtToken, refreshToken, userDetails.getUsername());
    }

    @Override
    public AuthenticationDetailDto authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        final String jwtToken = jwtService.generateToken(userDetails);
        final String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthenticationDetailDto(jwtToken, refreshToken, userDetails.getUsername());
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ResponseHelper.respondWithUnauthorizedError(response, "Refresh token is missing or invalid");
            return;
        }

        refreshToken = authHeader.substring(7);

        try {
            userEmail = jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            ResponseHelper.respondWithUnauthorizedError(response, "Refresh token is not valid");
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            ResponseHelper.respondWithUnauthorizedError(response, "Refresh token is not valid");
            return;
        }

        final String newAccessToken = jwtService.generateToken(userDetails);
        final String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Refresh-Token", "Bearer " + newRefreshToken);

        AuthenticationDetailDto authResponse = AuthenticationDetailDto.builder()
                .email(userDetails.getUsername())
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }
}
