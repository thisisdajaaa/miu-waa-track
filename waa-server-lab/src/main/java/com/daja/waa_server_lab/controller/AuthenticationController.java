package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.entity.dto.request.LoginDto;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.AuthenticationDetailDto;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHelper.CustomResponse<AuthenticationDetailDto>> register(
            @RequestBody UserDto createUserDto, HttpServletResponse response) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully registered user!",
                        authenticationService.register(createUserDto)),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseHelper.CustomResponse<AuthenticationDetailDto>> login(
            @RequestBody LoginDto loginDto, HttpServletResponse response) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully logged in user!",
                        authenticationService.authenticate(loginDto)),
                HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
