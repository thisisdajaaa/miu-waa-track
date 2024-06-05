package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Comment;
import com.daja.waa_server_lab.entity.Post;
import com.daja.waa_server_lab.entity.dto.request.CommentDto;
import com.daja.waa_server_lab.entity.dto.response.CommentDetailDto;
import com.daja.waa_server_lab.exception.CommentException;
import com.daja.waa_server_lab.repository.ICommentRepository;
import com.daja.waa_server_lab.service.spec.ICommentService;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;

    private final IPostService postService;

    private final MapperConfiguration mapperConfiguration;

    public CommentServiceImpl(ICommentRepository commentRepository, IPostService postService,
                              MapperConfiguration mapperConfiguration) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<CommentDetailDto> findAll(Map<String, String> filters) {
        return commentRepository.findAll().stream().map(
                        i -> CommentDetailDto.builder().id(i.getId()).name(i.getName()).postId(i.getPost().getId()).build())
                .toList();
    }

    @Override
    public CommentDetailDto findById(Long id) {
        Comment foundComment = commentRepository.findById(id).orElseThrow(CommentException.NotFoundException::new);

        return CommentDetailDto.builder()
                .id(foundComment.getId())
                .name(foundComment.getName())
                .postId(foundComment.getPost().getId())
                .build();
    }

    @Override
    public CommentDetailDto add(Long postId, CommentDto commentDto) {
        Post post = mapperConfiguration.convert(postService.findById(postId), Post.class);

        Comment comment = Comment.builder()
                .name(commentDto.getName())
                .post(post)
                .build();

        CommentDetailDto savedComment = mapperConfiguration.convert(commentRepository.save(comment),
                CommentDetailDto.class);
        savedComment.setPostId(post.getId());

        return savedComment;
    }

    @Override
    public CommentDetailDto delete(Long id) {
        CommentDetailDto foundComment = findById(id);

        commentRepository.deleteById(id);

        return foundComment;
    }

    @Override
    public CommentDetailDto update(Long id, CommentDto updatedDto) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(CommentException.NotFoundException::new);

        if (updatedDto.getName() != null)
            existingComment.setName(updatedDto.getName());

        Comment savedComment = commentRepository.save(existingComment);

        return CommentDetailDto.builder()
                .id(savedComment.getId())
                .name(savedComment.getName())
                .postId(savedComment.getPost().getId())
                .build();
    }

    @Override
    public CommentDetailDto findByPostIdAndCommentId(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPostIdAndCommentId(postId, commentId)
                .orElseThrow(CommentException.NotFoundException::new);
        
        return CommentDetailDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .postId(comment.getPost().getId())
                .build();
    }
}
