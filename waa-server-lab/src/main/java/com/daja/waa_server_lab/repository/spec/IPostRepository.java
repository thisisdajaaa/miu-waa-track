package com.daja.waa_server_lab.repository.spec;

import com.daja.waa_server_lab.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    List<Post> findAll();

    Optional<Post> findById(Long id);

    Post add(Post createPostDto);
}
