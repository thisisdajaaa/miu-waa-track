package com.daja.waa_server_lab.repository.spec;

import com.daja.waa_server_lab.entity.dto.request.CreatePostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    List<PostDetailDto> findAll();

    Optional<PostDetailDto> findById(Long id);

    PostDetailDto add(CreatePostDto createPostDto);
}
