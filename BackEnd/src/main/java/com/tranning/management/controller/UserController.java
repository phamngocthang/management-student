package com.tranning.management.controller;

import com.tranning.management.common.response.DataResponse;
import com.tranning.management.common.response.ListResponse;
import com.tranning.management.common.utilities.ApiResources;
import com.tranning.management.dto.ChangePasswordRequest;
import com.tranning.management.dto.LoginRequest;
import com.tranning.management.dto.UserDto;
import com.tranning.management.model.User;
import com.tranning.management.service.UserSevice;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD Rest APIs for User Resource",
        description = "CRUD REST APIs - Create User, Update User, Get User, Delete User, Login, ChangePassword"
)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserSevice userSevice;
    @GetMapping("login")
    public DataResponse<UserDto> login(@RequestBody LoginRequest loginRequest) {
        return userSevice.login(loginRequest);
    }

    @PostMapping(ApiResources.ADD)
    public DataResponse<UserDto> add(@RequestBody UserDto userDto) {
        return userSevice.create(userDto);
    }

    @PutMapping(ApiResources.UPDATE)
    public DataResponse<UserDto> update(@PathVariable("id") Integer id,
                                        @RequestBody UserDto userDto) {
        return userSevice.update(userDto, id);
    }

    @GetMapping(ApiResources.GET_ALL)
    public ListResponse<UserDto> getAll() {
        return userSevice.getAll();
    }

    @GetMapping(ApiResources.GET_BY_ID)
    public DataResponse<UserDto> getById(@RequestParam("id") Integer id) {
        return userSevice.getById(id);
    }

    @DeleteMapping(ApiResources.DELETE)
    public DataResponse<String> delete(@PathVariable("id") Integer id) {
        return userSevice.deleteById(id);
    }

    @PutMapping("change-password/{id}")
    public DataResponse<UserDto> changePassword(@PathVariable("id") Integer id,
                                                @RequestBody ChangePasswordRequest changePasswordRequest) {
        return userSevice.changePassword(id, changePasswordRequest);
    }
}
