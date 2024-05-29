package com.daja.waa_server_lab.repository.spec;

import com.daja.waa_server_lab.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    List<Post> findAll();

    List<Post> findByTitle(String title);

    List<Post> findByAuthor(String author);

    List<Post> findByContent(String content);

    Optional<Post> findById(Long id);

    Post add(Post newPost);

    Optional<Post> delete(Long id);

    Optional<Post> update(Long id, Post updatedPost);
}
