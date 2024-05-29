package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Post;
import com.daja.waa_server_lab.entity.dto.request.PostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.exception.PostException;
import com.daja.waa_server_lab.repository.spec.IPostRepository;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final MapperConfiguration mapperConfiguration;

    @Autowired
    public PostServiceImpl(IPostRepository postRepository, MapperConfiguration mapperConfiguration) {
        this.postRepository = postRepository;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<PostDetailDto> findAll(Map<String, String> filters) {
        List<Post> posts = postRepository.findAll();

        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("title")) {
                String title = filters.get("title");
                posts = postRepository.findByTitle(title);
            }

            if (filters.containsKey("author")) {
                String author = filters.get("author");
                posts = postRepository.findByAuthor(author);
            }

            if (filters.containsKey("content")) {
                String content = filters.get("content");
                posts = postRepository.findByContent(content);
            }
        }

        return posts.stream()
                .map(post -> mapperConfiguration.convert(post, PostDetailDto.class))
                .toList();
    }

    @Override
    public PostDetailDto findById(Long id) {
        Post foundPost = postRepository.findById(id).orElseThrow(PostException.NotFoundException::new);
        return mapperConfiguration.convert(foundPost, PostDetailDto.class);
    }

    @Override
    public PostDetailDto add(PostDto postDto) {
        Post addedPost = postRepository.add(mapperConfiguration.convert(postDto, Post.class));
        return mapperConfiguration.convert(addedPost, PostDetailDto.class);
    }

    @Override
    public PostDetailDto delete(Long id) {
        PostDetailDto foundPost = findById(id);
        return mapperConfiguration.convert(postRepository.delete(foundPost.getId()), PostDetailDto.class);
    }

    @Override
    public PostDetailDto update(Long id, PostDto updatedDto) {
        PostDetailDto foundPost = findById(id);
        Post updatedPost = mapperConfiguration.convert(foundPost, Post.class);

        if (updatedDto.getTitle() != null) updatedPost.setTitle(updatedDto.getTitle());
        if (updatedDto.getAuthor() != null) updatedPost.setAuthor(updatedDto.getAuthor());
        if (updatedDto.getContent() != null) updatedPost.setContent(updatedDto.getContent());

        Post savedPost = postRepository.update(updatedPost.getId(), updatedPost).orElseThrow(PostException.NotFoundException::new);

        return mapperConfiguration.convert(savedPost, PostDetailDto.class);
    }
}
