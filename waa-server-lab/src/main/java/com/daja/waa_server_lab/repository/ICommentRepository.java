package com.daja.waa_server_lab.repository;

import com.daja.waa_server_lab.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.id = :commentId")
    Optional<Comment> findByPostIdAndCommentId(@Param("postId") Long postId, @Param("commentId") Long commentId);
}
