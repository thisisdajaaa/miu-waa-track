package com.daja.waa_server_lab.service.spec;

import com.daja.waa_server_lab.entity.Role;
import com.daja.waa_server_lab.entity.dto.request.RoleDto;
import com.daja.waa_server_lab.entity.dto.response.RoleDetailDto;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    RoleDetailDto add(RoleDto roleDto);

    RoleDetailDto findById(Long id);

    List<RoleDetailDto> findAll(Map<String, String> filters);

    RoleDetailDto update(Long id, RoleDto roleDto);

    RoleDetailDto delete(Long id);

    Integer getRoleListCount();
}
