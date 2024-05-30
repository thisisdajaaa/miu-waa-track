package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.entity.dto.request.PostDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final IPostService postService;

    @Autowired
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<PostDetailDto>>> getPosts(
            @RequestParam(required = false) String filter) {

        Map<String, String> filterMap = new HashMap<>();

        if (filter != null && !filter.isEmpty()) {
            String[] filters = filter.split(",");
            for (String f : filters) {
                String[] keyValue = f.split(":");
                if (keyValue.length == 2) filterMap.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved posts!", postService.findAll(filterMap)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved post!", postService.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a post!", postService.add(postDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> deletePost(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a post!", postService.delete(id)),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<PostDetailDto>> updatePost(
            @PathVariable Long id, @RequestBody PostDto postDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a post!", postService.update(id, postDto)),
                HttpStatus.OK);
    }
}
