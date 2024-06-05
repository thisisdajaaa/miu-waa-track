package com.daja.waa_server_lab.helper;


import com.daja.waa_server_lab.entity.dto.request.RoleDto;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.service.spec.IRoleService;
import com.daja.waa_server_lab.service.spec.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeederHelper implements CommandLineRunner {
    private final IRoleService roleService;

    private final IUserService userService;

    public SeederHelper(IRoleService roleService, IUserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedRolesTable();
        seedUsers();
    }

    private void seedRolesTable() {
        if (roleService.getRoleListCount() != 0)
            return;

        String[] roleNames = { "ADMIN", "USER" };

        for (String roleName : roleNames) {
            RoleDto createRoleDto = new RoleDto(roleName);
            try {
                roleService.add(createRoleDto);
                System.out.println("Seeded " + roleName + " role.");
            } catch (Exception e) {
                System.out.println("Role " + roleName + " already exists or could not be created: " + e.getMessage());
            }
        }
    }


    private void seedUsers() {
        if (userService.getUserListCount() != 0)
            return;

        UserDto createUserDto = new UserDto("Dann Anthony J. Astillero", "dann.astillero@miu.edu", "test12345", List.of(1L));

        try {
            userService.add(createUserDto);
        } catch (Exception e) {
            System.out.println("User " + createUserDto.getEmail() + " already exists or could not be created: "
                    + e.getMessage());
        }
    }
}
