package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Role;
import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;
import com.daja.waa_server_lab.exception.RoleException;
import com.daja.waa_server_lab.exception.UserException;
import com.daja.waa_server_lab.repository.IRoleRepository;
import com.daja.waa_server_lab.repository.IUserRepository;
import com.daja.waa_server_lab.service.spec.IUserService;
import jakarta.persistence.Tuple;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final MapperConfiguration mapperConfiguration;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, MapperConfiguration mapperConfiguration, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapperConfiguration = mapperConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDetailDto> findAll(Map<String, String> filters, Integer postCount, String postTitle) {
        List<User> users = userRepository.findAll();

        if (postCount != null && postCount > 0) {
            List<Tuple> results = userRepository.findUsersWithPostsByCount(postCount);

            users = results.stream()
                    .map(result -> User.builder()
                            .id(result.get("id", Long.class))
                            .name(result.get("name", String.class))
                            .build())
                    .collect(Collectors.toList());
        }

        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                String name = filters.get("name");
                users = users.stream()
                        .filter(user -> user.getName().contains(name))
                        .collect(Collectors.toList());
            }
        }

        if (postTitle != null && !postTitle.isEmpty()) {
            users = userRepository.findUsersByPostTitle(postTitle);
        }

        return users.stream().map(i -> mapperConfiguration.convert(i, UserDetailDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDetailDto findById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(UserException.NotFoundException::new);
        return mapperConfiguration.convert(foundUser, UserDetailDto.class);
    }

    @Override
    public UserDetailDto add(UserDto userDto) {
        User user = mapperConfiguration.convert(userDto, User.class);

        List<Role> roles = userDto.getRoleIds().stream()
                .map(roleRepository::findById)
                .map(optionalRole -> optionalRole.orElseThrow(RoleException.NotFoundException::new))
                .collect(Collectors.toList());

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User addedUser = userRepository.save(user);
        return mapperConfiguration.convert(addedUser, UserDetailDto.class);
    }

    @Override
    public UserDetailDto delete(Long id) {
        UserDetailDto foundUser = findById(id);
        userRepository.deleteById(foundUser.getId());
        return mapperConfiguration.convert(foundUser, UserDetailDto.class);
    }

    @Override
    public UserDetailDto update(Long id, UserDto updatedDto) {
        User foundUser = userRepository.findById(id).orElseThrow(UserException.NotFoundException::new);

        if (updatedDto.getName() != null)
            foundUser.setName(updatedDto.getName());

        if (updatedDto.getEmail() != null)
            foundUser.setEmail(updatedDto.getEmail());

        if (updatedDto.getPassword() != null)
            foundUser.setPassword(passwordEncoder.encode(updatedDto.getPassword()));

        if (updatedDto.getRoleIds() != null) {
            List<Role> roles = updatedDto.getRoleIds().stream()
                    .map(roleRepository::findById)
                    .map(optionalRole -> optionalRole.orElseThrow(RoleException.NotFoundException::new))
                    .collect(Collectors.toList());

            foundUser.setRoles(roles);
        }

        User savedUser = userRepository.save(foundUser);
        return mapperConfiguration.convert(savedUser, UserDetailDto.class);
    }

    @Override
    public Integer getUserListCount() {
        return (int) userRepository.count();
    }
}
