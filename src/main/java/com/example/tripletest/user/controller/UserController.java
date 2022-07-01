package com.example.tripletest.user.controller;

import com.example.tripletest.user.dto.UserDto;
import com.example.tripletest.user.entity.UserEntity;
import com.example.tripletest.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {

    private final UserService userService;

    @PostMapping("/user/new")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity(userService.register(userDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user")
    public UserDto login(@RequestBody UserDto userDto) throws Exception {
        return userService.login(userDto);
    }

    @GetMapping("/users")
    public List<UserEntity> findAll() {
        return userService.findUsers();
    }
}
