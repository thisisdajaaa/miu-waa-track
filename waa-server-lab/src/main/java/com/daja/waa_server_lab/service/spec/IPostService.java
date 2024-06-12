package com.daja.waa_server_lab.service.spec;

import com.daja.waa_server_lab.entity.dto.request.PostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;

import java.util.List;
import java.util.Map;

public interface IPostService {
    List<PostDetailDto> findAll(Boolean isUserPost, Map<String, String> filters);

    PostDetailDto findById(Long id);

    PostDetailDto add(PostDto postDto);

    PostDetailDto delete(Long id);

    PostDetailDto update(Long id, PostDto updatedDto);

    PostDetailDto findByUserIdAndPostId(Long postId);
}
