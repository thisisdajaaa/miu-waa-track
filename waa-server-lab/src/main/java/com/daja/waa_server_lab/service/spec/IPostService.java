package com.daja.waa_server_lab.service.spec;

import com.daja.waa_server_lab.entity.dto.request.CreatePostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;

import java.util.List;

public interface IPostService {
    List<PostDetailDto> findAll();

    PostDetailDto findById(Long id);

    PostDetailDto add(CreatePostDto createPostDto);
}
