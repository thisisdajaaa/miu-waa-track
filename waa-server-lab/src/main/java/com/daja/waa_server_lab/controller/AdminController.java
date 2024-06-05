package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.ICustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final ICustomUserDetailsService customUserDetailsService;

    public AdminController(ICustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<String>> getAdminName() {

        User user = customUserDetailsService.getAuthenticatedUser();

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(
                        true, "Successfully retrieved admin name!",
                        user.getName()),
                HttpStatus.OK);
    }
}
