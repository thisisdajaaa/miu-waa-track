package com.daja.waa_server_lab.repository;

import com.daja.waa_server_lab.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:title%")
    List<Post> findByTitle(@Param("title") String title);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:content%")
    List<Post> findByContent(@Param("content") String content);

    @Query("SELECT p FROM Post p WHERE p.id = :postId AND p IN (SELECT u.posts FROM User u WHERE u.id = :userId)")
    Optional<Post> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
