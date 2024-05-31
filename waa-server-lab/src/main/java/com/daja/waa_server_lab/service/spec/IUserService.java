package com.daja.waa_server_lab.service.spec;

import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;

import java.util.List;
import java.util.Map;

public interface IUserService {
    List<UserDetailDto> findAll(Map<String, String> filters, Integer postCount, String postTitle);

    UserDetailDto findById(Long id);

    UserDetailDto add(UserDto postDto);

    UserDetailDto delete(Long id);

    UserDetailDto update(Long id, UserDto updatedDto);
}
