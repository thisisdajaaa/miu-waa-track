package com.daja.waa_server_lab.service.impl;

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
    
    public PostServiceImpl(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDetailDto> findAll() {
        return postRepository.findAll();
    }

    @Override
    public PostDetailDto findById(Long id) {
        return postRepository.findById(id).orElseThrow(PostException.NotFoundException::new);
    }

    @Override
    public PostDetailDto add(CreatePostDto createPostDto) {
        return postRepository.add(createPostDto);
    }
}
