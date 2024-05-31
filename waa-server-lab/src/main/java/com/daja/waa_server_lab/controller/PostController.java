package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.entity.dto.request.PostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.helper.QueryParamHelper;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.IPostService;
import com.daja.waa_server_lab.service.spec.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/{userId}/posts")
public class PostController {
    private final IPostService postService;
    private final IUserService userService;

    @Autowired
    public PostController(IPostService postService, IUserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<PostDetailDto>>> getPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) String filter) {

        // Ensure the user exists
        userService.findById(userId);

        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(
                        true, "Successfully retrieved posts!",
                        postService.findAll(transformedFilter)),
                HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> getPost(
            @PathVariable Long userId,
            @PathVariable Long postId) {

        // Ensure the user exists
        userService.findById(userId);

        // Ensure the post exists and belongs to the user
        postService.findByUserIdAndPostId(userId, postId);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved post!", postService.findById(postId)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> createPost(
            @PathVariable Long userId, @RequestBody PostDto postDto) {

        // Ensure the user exists
        userService.findById(userId);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a post!", postService.add(userId, postDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> deletePost(
            @PathVariable Long userId, @PathVariable Long postId) {

        // Ensure the user exists
        userService.findById(userId);

        // Ensure the post exists and belongs to the user
        postService.findByUserIdAndPostId(userId, postId);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a post!", postService.delete(postId)),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> updatePost(
            @PathVariable Long userId, @PathVariable Long postId, @RequestBody PostDto postDto) {

        // Ensure the user exists
        userService.findById(userId);

        // Ensure the post exists and belongs to the user
        postService.findByUserIdAndPostId(userId, postId);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a post!", postService.update(postId, postDto)),
                HttpStatus.OK);
    }
}
