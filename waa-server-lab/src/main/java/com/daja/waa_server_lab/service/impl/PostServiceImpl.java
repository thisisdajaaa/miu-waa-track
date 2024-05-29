package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Post;
import com.daja.waa_server_lab.entity.dto.request.CreatePostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.exception.PostException;
import com.daja.waa_server_lab.repository.spec.IPostRepository;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final MapperConfiguration mapperConfiguration;
    
    public PostServiceImpl(IPostRepository postRepository, MapperConfiguration mapperConfiguration) {
        this.postRepository = postRepository;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<PostDetailDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(i -> mapperConfiguration.convert(i, PostDetailDto.class)).toList();

    }

    @Override
    public PostDetailDto findById(Long id) {
        Post foundPost = postRepository.findById(id).orElseThrow(PostException.NotFoundException::new);
        return mapperConfiguration.convert(foundPost, PostDetailDto.class);
    }

    @Override
    public PostDetailDto add(CreatePostDto createPostDto) {
        Post addedPost = postRepository.add(mapperConfiguration.convert(createPostDto, Post.class));
        return mapperConfiguration.convert(addedPost, PostDetailDto.class);
    }
}
