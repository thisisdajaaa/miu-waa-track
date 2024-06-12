package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.dto.request.LoginDto;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.AuthenticationDetailDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.IAuthenticationService;
import com.daja.waa_server_lab.service.spec.IJWTService;
import com.daja.waa_server_lab.service.spec.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final CustomUserDetailsService customUserDetailsService;
    private final IJWTService jwtService;
    private final MapperConfiguration mapperConfiguration;

    public AuthenticationServiceImpl(IUserService userService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, CustomUserDetailsService customUserDetailsService, IJWTService jwtService, MapperConfiguration mapperConfiguration) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public AuthenticationDetailDto register(UserDto createUserDto) {
        UserDetailDto userDetailDto = userService.add(createUserDto);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDetailDto.getEmail());
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

    @Override
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

        jwtService.revokeToken(refreshToken);

        final String oldAccessToken = request.getHeader("Old-Access-Token");

        if (oldAccessToken != null && oldAccessToken.startsWith("Bearer ")) {
            jwtService.revokeToken(oldAccessToken.substring(7));
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

        ResponseHelper.CustomResponse<AuthenticationDetailDto> customResponse = new ResponseHelper.CustomResponse<>(
                true, "Successfully refreshed tokens!", authResponse);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        new ObjectMapper().writeValue(response.getOutputStream(), customResponse);
    }

    @Override
    public UserDetailDto getAuthenticatedUserDetails() {
        User foundUser = customUserDetailsService.getAuthenticatedUser();
        return mapperConfiguration.convert(foundUser, UserDetailDto.class);
    }
}
