package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;
import com.daja.waa_server_lab.exception.UserException;
import com.daja.waa_server_lab.repository.IUserRepository;
import com.daja.waa_server_lab.service.spec.IUserService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final MapperConfiguration mapperConfiguration;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, MapperConfiguration mapperConfiguration) {
        this.userRepository = userRepository;
        this.mapperConfiguration = mapperConfiguration;
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
                    .toList();
        }


        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                String name = filters.get("name");
                users = users.stream()
                        .filter(user -> user.getName().contains(name))
                        .toList();
            }
        }

        if (postTitle != null && !postTitle.isEmpty()) {
            users = userRepository.findUsersByPostTitle(postTitle);
        }


        return users.stream().map(i -> mapperConfiguration.convert(i, UserDetailDto.class)).toList();
    }

    @Override
    public UserDetailDto findById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(UserException.NotFoundException::new);
        return mapperConfiguration.convert(foundUser, UserDetailDto.class);
    }

    @Override
    public UserDetailDto add(UserDto userDto) {
        User addedUser = userRepository.save(mapperConfiguration.convert(userDto, User.class));
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
        UserDetailDto foundUser = findById(id);
        User updatedUser = mapperConfiguration.convert(foundUser, User.class);

        if (updatedDto.getName() != null)
            updatedUser.setName(updatedDto.getName());

        User savedUser = userRepository.save(updatedUser);

        return mapperConfiguration.convert(savedUser, UserDetailDto.class);
    }
}
