package com.daja.waa_server_lab.repository.impl;

import com.daja.waa_server_lab.entity.dto.request.CreatePostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.repository.spec.IPostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private static final AtomicInteger count = new AtomicInteger(0);

    List<PostDetailDto> postDetailDtos = new ArrayList<>();

    @Override
    public List<PostDetailDto> findAll() {
        return postDetailDtos;
    }

    @Override
    public Optional<PostDetailDto> findById(Long id) {
        return postDetailDtos.stream().filter(i -> Objects.equals(i.getId(), id)).findFirst();
    }

    @Override
    public PostDetailDto add(CreatePostDto createPostDto) {
        PostDetailDto newPost = new PostDetailDto();
        newPost.setId((long) count.incrementAndGet());
        newPost.setTitle(createPostDto.getTitle());
        newPost.setAuthor(createPostDto.getAuthor());
        newPost.setContent(createPostDto.getContent());

        postDetailDtos.add(newPost);

        return newPost;
    }
}
