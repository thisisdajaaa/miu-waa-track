package com.daja.waa_server_lab.service.spec;

import com.daja.waa_server_lab.entity.dto.request.LoginDto;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.AuthenticationDetailDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {
    AuthenticationDetailDto register(UserDto createUserDto);

    AuthenticationDetailDto authenticate(LoginDto loginDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    UserDetailDto getAuthenticatedUserDetails();
}
