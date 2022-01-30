package com.ryhnik.controller;

import com.ryhnik.dto.user.UserAuth;
import com.ryhnik.dto.user.UserAuthRequest;
import com.ryhnik.dto.user.UserInputCreateDto;
import com.ryhnik.dto.user.UserOutputDto;
import com.ryhnik.entity.User;
import com.ryhnik.mapper.UserMapper;
import com.ryhnik.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Auth", description = "API for auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserOutputDto> register(@RequestBody UserInputCreateDto userInputCreateDto) {
        User user = userService.registerUser(userInputCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toUserOutputDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuth> auth(@RequestBody UserAuthRequest request) {
        UserAuth userAuth = userService.userAuth(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userAuth);
    }
}