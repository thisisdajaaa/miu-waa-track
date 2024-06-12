package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Post;
import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.dto.request.PostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;
import com.daja.waa_server_lab.exception.PostException;
import com.daja.waa_server_lab.repository.IPostRepository;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final MapperConfiguration mapperConfiguration;

    public PostServiceImpl(IPostRepository postRepository, CustomUserDetailsService customUserDetailsService,
                           MapperConfiguration mapperConfiguration) {
        this.postRepository = postRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<PostDetailDto> findAll(Boolean isUserPost, Map<String, String> filters) {
        List<Post> posts;

        if (isUserPost) {
            User user = customUserDetailsService.getAuthenticatedUser();
            posts = user.getPosts();
        } else {
            posts = postRepository.findAll();
        }

        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("title")) {
                String title = filters.get("title");
                posts = posts.stream()
                        .filter(post -> post.getTitle().contains(title))
                        .toList();
            }

            if (filters.containsKey("content")) {
                String content = filters.get("content");
                posts = posts.stream()
                        .filter(post -> post.getContent().contains(content))
                        .toList();
            }
        }

        return posts.stream()
                .map(post -> {
                    PostDetailDto dto = mapperConfiguration.convert(post, PostDetailDto.class);
                    dto.setAuthor(mapperConfiguration.convert(post.getAuthor(), UserDetailDto.class));
                    return dto;
                })
                .toList();
    }

    @Override
    public PostDetailDto findById(Long id) {
        Post foundPost = postRepository.findById(id).orElseThrow(PostException.NotFoundException::new);
        PostDetailDto dto = mapperConfiguration.convert(foundPost, PostDetailDto.class);
        dto.setAuthor(mapperConfiguration.convert(foundPost.getAuthor(), UserDetailDto.class));
        return dto;
    }

    @Override
    public PostDetailDto add(PostDto postDto) {
        User user = customUserDetailsService.getAuthenticatedUser();
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(user)
                .build();
        Post savedPost = postRepository.save(post);
        PostDetailDto dto = mapperConfiguration.convert(savedPost, PostDetailDto.class);
        dto.setAuthor(mapperConfiguration.convert(savedPost.getAuthor(), UserDetailDto.class));
        return dto;
    }

    @Override
    public PostDetailDto delete(Long id) {
        PostDetailDto foundPost = findById(id);
        postRepository.deleteById(id);
        return foundPost;
    }

    @Override
    public PostDetailDto update(Long id, PostDto updatedDto) {
        Post existingPost = postRepository.findById(id).orElseThrow(PostException.NotFoundException::new);
        if (updatedDto.getTitle() != null)
            existingPost.setTitle(updatedDto.getTitle());
        if (updatedDto.getContent() != null)
            existingPost.setContent(updatedDto.getContent());
        Post savedPost = postRepository.save(existingPost);
        PostDetailDto dto = mapperConfiguration.convert(savedPost, PostDetailDto.class);
        dto.setAuthor(mapperConfiguration.convert(savedPost.getAuthor(), UserDetailDto.class));
        return dto;
    }

    @Override
    public PostDetailDto findByUserIdAndPostId(Long postId) {
        User user = customUserDetailsService.getAuthenticatedUser();
        Post existingPost = postRepository.findByUserIdAndPostId(user.getId(), postId).orElseThrow(PostException.NotFoundException::new);
        PostDetailDto dto = mapperConfiguration.convert(existingPost, PostDetailDto.class);
        dto.setAuthor(mapperConfiguration.convert(existingPost.getAuthor(), UserDetailDto.class));
        return dto;
    }
}
