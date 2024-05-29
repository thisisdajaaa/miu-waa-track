package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Post;
import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.dto.request.PostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;
import com.daja.waa_server_lab.exception.PostException;
import com.daja.waa_server_lab.exception.UserException;
import com.daja.waa_server_lab.repository.IPostRepository;
import com.daja.waa_server_lab.repository.IUserRepository;
import com.daja.waa_server_lab.service.spec.IPostService;
import com.daja.waa_server_lab.service.spec.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final MapperConfiguration mapperConfiguration;

    @Autowired
    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository, MapperConfiguration mapperConfiguration) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

//            if (filters.containsKey("author")) {
//                String author = filters.get("author");
//                posts = postRepository.findByAuthor(author);
//            }

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
        User foundUser = userRepository.findById(postDto.getUserId())
                .orElseThrow(UserException.NotFoundException::new);

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(foundUser)
                .build();

        Post savedPost = postRepository.save(post);

        PostDetailDto savedPostDto = mapperConfiguration.convert(savedPost, PostDetailDto.class);
        savedPostDto.setUserId(foundUser.getId());

        return savedPostDto;
    }

    @Override
    public PostDetailDto delete(Long id) {
        PostDetailDto foundPost = findById(id);

        postRepository.deleteById(id);

        return mapperConfiguration.convert(foundPost, PostDetailDto.class);
    }

    @Override
    public PostDetailDto update(Long id, PostDto updatedDto) {
        Post existingPost = postRepository.findById(id).orElseThrow(PostException.NotFoundException::new);

        if (updatedDto.getTitle() != null) existingPost.setTitle(updatedDto.getTitle());
        if (updatedDto.getContent() != null) existingPost.setContent(updatedDto.getContent());
        if (updatedDto.getUserId() != null) {
            User newUser = userRepository.findById(updatedDto.getUserId())
                    .orElseThrow(UserException.NotFoundException::new);

            existingPost.setUser(newUser);
        }

        Post savedPost = postRepository.save(existingPost);

        return mapperConfiguration.convert(savedPost, PostDetailDto.class);
    }
}
