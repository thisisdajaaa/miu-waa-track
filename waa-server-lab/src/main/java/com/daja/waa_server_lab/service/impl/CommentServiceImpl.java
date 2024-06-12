package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.configuration.MapperConfiguration;
import com.daja.waa_server_lab.entity.Comment;
import com.daja.waa_server_lab.entity.Post;
import com.daja.waa_server_lab.entity.User;
import com.daja.waa_server_lab.entity.dto.request.CommentDto;
import com.daja.waa_server_lab.entity.dto.response.CommentDetailDto;
import com.daja.waa_server_lab.exception.CommentException;
import com.daja.waa_server_lab.repository.ICommentRepository;
import com.daja.waa_server_lab.service.spec.ICommentService;
import com.daja.waa_server_lab.service.spec.ICustomUserDetailsService;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final IPostService postService;
    private final ICustomUserDetailsService customUserDetailsService;
    private final MapperConfiguration mapperConfiguration;

    public CommentServiceImpl(ICommentRepository commentRepository, IPostService postService,
                              ICustomUserDetailsService customUserDetailsService, MapperConfiguration mapperConfiguration) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.customUserDetailsService = customUserDetailsService;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<CommentDetailDto> findAll(Long postId, Map<String, String> filters) {
        Post post = mapperConfiguration.convert(postService.findById(postId), Post.class);
        List<Comment> comments = post.getComments();

        if (filters != null && !filters.isEmpty()) {
            if (filters.containsKey("name")) {
                String name = filters.get("name");
                comments = comments.stream()
                        .filter(comment -> comment.getContent().contains(name))
                        .toList();
            }
        }

        return comments.stream()
                .map(comment -> mapperConfiguration.convert(comment, CommentDetailDto.class))
                .toList();
    }

    @Override
    public CommentDetailDto findById(Long id) {
        Comment foundComment = commentRepository.findById(id).orElseThrow(CommentException.NotFoundException::new);

        return CommentDetailDto.builder()
                .id(foundComment.getId())
                .content(foundComment.getContent())
                .postId(foundComment.getPost().getId())
                .createdAt(foundComment.getCreatedAt())
                .createdBy(foundComment.getCreatedBy())
                .build();
    }

    @Override
    public CommentDetailDto add(Long postId, CommentDto commentDto) {
        Post post = mapperConfiguration.convert(postService.findById(postId), Post.class);
        User user = customUserDetailsService.getAuthenticatedUser();

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .post(post)
                .author(user)
                .build();

        CommentDetailDto savedComment = mapperConfiguration.convert(commentRepository.save(comment), CommentDetailDto.class);
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

        if (updatedDto.getContent() != null)
            existingComment.setContent(updatedDto.getContent());

        Comment savedComment = commentRepository.save(existingComment);

        return CommentDetailDto.builder()
                .id(savedComment.getId())
                .content(savedComment.getContent())
                .postId(savedComment.getPost().getId())
                .createdAt(savedComment.getCreatedAt())
                .createdBy(savedComment.getCreatedBy())
                .build();
    }

    @Override
    public CommentDetailDto findByPostIdAndCommentId(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPostIdAndCommentId(postId, commentId)
                .orElseThrow(CommentException.NotFoundException::new);

        return CommentDetailDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .createdAt(comment.getCreatedAt())
                .createdBy(comment.getCreatedBy())
                .build();
    }
}
