package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Role;
import com.daja.waa_server_lab.entity.dto.request.RoleDto;
import com.daja.waa_server_lab.entity.dto.response.RoleDetailDto;
import com.daja.waa_server_lab.exception.RoleException;
import com.daja.waa_server_lab.repository.IRoleRepository;
import com.daja.waa_server_lab.service.spec.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;

    private final MapperConfiguration mapperConfiguration;

    public RoleServiceImpl(IRoleRepository roleRepository, MapperConfiguration mapperConfiguration) {
        this.roleRepository = roleRepository;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<RoleDetailDto> findAll(Map<String, String> filters) {
        return roleRepository.findAll().stream().map(
                        i -> RoleDetailDto.builder().id(i.getId()).name(i.getName()).build())
                .toList();
    }

    @Override
    public RoleDetailDto findById(Long id) {
        Role foundRole = roleRepository.findById(id).orElseThrow(RoleException.NotFoundException::new);

        return RoleDetailDto.builder()
                .id(foundRole.getId())
                .name(foundRole.getName())
                .build();
    }

    @Override
    public RoleDetailDto add(RoleDto roleDto) {
        Role role = Role.builder()
                .name(roleDto.getName())
                .build();

        return mapperConfiguration.convert(roleRepository.save(role),
                RoleDetailDto.class);
    }

    @Override
    public RoleDetailDto delete(Long id) {
        RoleDetailDto foundRole = findById(id);

        roleRepository.deleteById(id);

        return foundRole;
    }

    @Override
    public Integer getRoleListCount() {
        return roleRepository.findAll().size();
    }

    @Override
    public RoleDetailDto update(Long id, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(id).orElseThrow(RoleException.NotFoundException::new);

        if (roleDto.getName() != null)
            existingRole.setName(roleDto.getName());

        Role savedRole = roleRepository.save(existingRole);

        return RoleDetailDto.builder()
                .id(savedRole.getId())
                .name(savedRole.getName())
                .build();
    }
}
