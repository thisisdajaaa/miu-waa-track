package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.service.spec.IJWTService;
import com.daja.waa_server_lab.service.spec.ILogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements ILogoutService {
    private final IJWTService jwtService;

    public LogoutServiceImpl(IJWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final String token = authHeader.substring(7);
        jwtService.revokeToken(token);

        SecurityContextHolder.clearContext();
    }
}
