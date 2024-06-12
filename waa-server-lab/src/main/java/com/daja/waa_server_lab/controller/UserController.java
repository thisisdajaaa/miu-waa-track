package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.aspect.annotation.ExecutionTime;
import com.daja.waa_server_lab.entity.dto.request.UserDto;
import com.daja.waa_server_lab.entity.dto.response.PostDetailDto;
import com.daja.waa_server_lab.entity.dto.response.UserDetailDto;
import com.daja.waa_server_lab.helper.QueryParamHelper;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.IAuthenticationService;
import com.daja.waa_server_lab.service.spec.IPostService;
import com.daja.waa_server_lab.service.spec.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;
    private final IPostService postService;
    private final IAuthenticationService authenticationService;

    public UserController(IUserService userService, IPostService postService, IAuthenticationService authenticationService) {
        this.userService = userService;
        this.postService = postService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<UserDetailDto>>> getUsers(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Integer postCount,
            @RequestParam(required = false) String postTitle) {

        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved users!",
                        userService.findAll(transformedFilter, postCount, postTitle)),
                HttpStatus.OK);
    }

    @ExecutionTime
    @GetMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<UserDetailDto>> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved user!", userService.findById(id)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<UserDetailDto>> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a user!", userService.add(userDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<UserDetailDto>> deleteUser(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a user!", userService.delete(id)),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<UserDetailDto>> updateUser(
            @PathVariable Long id, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a user!",
                        userService.update(id, userDto)),
                HttpStatus.OK);
    }


    @GetMapping("/me")
    public ResponseEntity<ResponseHelper.CustomResponse<UserDetailDto>> fetchAuthenticatedUserDetails() {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully fetched logged in user details!",
                        authenticationService.getAuthenticatedUserDetails()),
                HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseHelper.CustomResponse<List<PostDetailDto>>> getPosts(
            @RequestParam(required = false) String filter) {
        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(
                        true, "Successfully retrieved user posts!",
                        postService.findAll(true, transformedFilter)),
                HttpStatus.OK);
    }
}
