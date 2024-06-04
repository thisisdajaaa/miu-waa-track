package com.daja.waa_server_lab.controller;

import com.daja.waa_server_lab.aspect.annotation.ExecutionTime;
import com.daja.waa_server_lab.entity.dto.request.RoleDto;
import com.daja.waa_server_lab.entity.dto.response.RoleDetailDto;
import com.daja.waa_server_lab.helper.QueryParamHelper;
import com.daja.waa_server_lab.helper.ResponseHelper;
import com.daja.waa_server_lab.service.spec.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<RoleDetailDto>>> getRoles(
            @RequestParam(required = false) String filter) {

        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved roles!",
                        roleService.findAll(transformedFilter)),
                HttpStatus.OK);
    }

    @ExecutionTime
    @GetMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<RoleDetailDto>> getRole(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved role!", roleService.findById(id)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<RoleDetailDto>> createRole(@RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created a role!", roleService.add(roleDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<RoleDetailDto>> deleteRole(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted a role!", roleService.delete(id)),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseHelper.CustomResponse<RoleDetailDto>> updateRole(
            @PathVariable Long id, @RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated a role!",
                        roleService.update(id, roleDto)),
                HttpStatus.OK);
    }
}
