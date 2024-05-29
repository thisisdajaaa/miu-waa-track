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
    public List<Post> findByTitle(String title) {
        return postList.stream()
                .filter(post -> post.getTitle().contains(title) || post.getTitle().equalsIgnoreCase(title))
                .toList();
    }

    @Override
    public List<Post> findByAuthor(String author) {
        return postList.stream()
                .filter(post -> post.getAuthor().contains(author) || post.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    @Override
    public List<Post> findByContent(String content) {
        return postList.stream()
                .filter(post -> post.getContent().contains(content) || post.getContent().equalsIgnoreCase(content))
                .toList();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postList.stream().filter(i -> Objects.equals(i.getId(), id)).findFirst();
    }

    @Override
    public Post add(Post createPostDto) {
        createPostDto.setId((long) count.incrementAndGet());
        postList.add(createPostDto);
        return createPostDto;
    }

    @Override
    public Optional<Post> delete(Long id) {
        Optional<Post> foundPost = findById(id);
        foundPost.ifPresent(postList::remove);
        return foundPost;
    }

    @Override
    public Optional<Post> update(Long id, Post updatedPost) {
        Optional<Post> foundPost = findById(id);

        foundPost.ifPresent(existingPost -> {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setAuthor(updatedPost.getAuthor());
            existingPost.setContent(updatedPost.getContent());
        });

        return foundPost;
    }


}
