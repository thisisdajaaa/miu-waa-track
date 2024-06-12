package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.entity.dto.request.PostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.helper.QueryParamHelper;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<PostDetailDto>>> getPosts(
            @RequestParam(required = false) String filter) {
        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(
                        true, "Successfully retrieved posts!",
                        postService.findAll(false, transformedFilter)),
                HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> getPost(
            @PathVariable Long postId) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved post!", postService.findById(postId)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> createPost(
            @RequestBody PostDto postDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a post!", postService.add(postDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> deletePost(
            @PathVariable Long postId) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a post!", postService.delete(postId)),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> updatePost(
            @PathVariable Long postId, @RequestBody PostDto postDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a post!", postService.update(postId, postDto)),
                HttpStatus.OK);
    }
}
