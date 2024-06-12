package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.entity.dto.request.CommentDto;
import com.daja.waa_server_lab.entity.dto.response.CommentDetailDto;
import com.daja.waa_server_lab.helper.QueryParamHelper;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.ICommentService;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {
    private final ICommentService commentService;
    private final IPostService postService;

    public CommentController(ICommentService commentService, IPostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<CommentDetailDto>>> getComments(
            @PathVariable Long postId,
            @RequestParam(required = false) String filter
    ) {
        postService.findById(postId);

        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(
                        true, "Successfully retrieved comments!",
                        commentService.findAll(postId, transformedFilter)),
                HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseHelper.CustomResponse<CommentDetailDto>> getComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        postService.findById(postId);

        CommentDetailDto comment = commentService.
                findByPostIdAndCommentId(postId, commentId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved comment!", comment),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<CommentDetailDto>> createComment(
            @PathVariable Long postId,
            @RequestBody CommentDto commentDto) {

        postService.findById(postId);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a comment!", commentService.add(postId, commentDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseHelper.CustomResponse<CommentDetailDto>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        postService.findById(postId);

        CommentDetailDto deletedComment = commentService.delete(commentId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a comment!", deletedComment),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ResponseHelper.CustomResponse<CommentDetailDto>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto
    ) {
        postService.findById(postId);

        CommentDetailDto updatedComment = commentService.update(commentId, commentDto);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a comment!", updatedComment),
                HttpStatus.OK);
    }
}
