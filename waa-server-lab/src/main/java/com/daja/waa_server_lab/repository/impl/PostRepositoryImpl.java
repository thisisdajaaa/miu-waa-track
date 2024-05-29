package com.daja.waa_server_lab.repository.impl;

import com.daja.waa_server_lab.entity.Post;
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

    List<Post> postList = new ArrayList<>();

    @Override
    public List<Post> findAll() {
        return postList;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postList.stream().filter(i -> Objects.equals(i.getId(), id)).findFirst();
    }

    @Override
    public Post add(Post createPostDto) {
        Post newPost = new Post();
        newPost.setId((long) count.incrementAndGet());
        newPost.setTitle(createPostDto.getTitle());
        newPost.setAuthor(createPostDto.getAuthor());
        newPost.setContent(createPostDto.getContent());

        postList.add(newPost);

        return newPost;
    }
}
