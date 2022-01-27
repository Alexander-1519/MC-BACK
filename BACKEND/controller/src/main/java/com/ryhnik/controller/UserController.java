package com.ryhnik.controller;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.user.UserAuth;
import com.ryhnik.dto.user.UserAuthRequest;
import com.ryhnik.dto.user.UserInputCreateDto;
import com.ryhnik.dto.user.UserOutputDto;
import com.ryhnik.entity.User;
import com.ryhnik.mapper.UserMapper;
import com.ryhnik.service.UserService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "API for users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> registerUser(@RequestBody UserInputCreateDto userInputCreateDto) {
        User user = userService.registerUser(userMapper.toUser(userInputCreateDto));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toUserOutputDto(user));
    }

    @GetMapping
    public ResponseEntity<PageDto<UserOutputDto>> getAll(@RequestParam Integer page,
                                                         @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users = userService.findAll(pageRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toPagedUserOutputDto(users, users.getPageable()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuth> auth(@RequestBody UserAuthRequest request) {
        UserAuth userAuth = userService.userAuth(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userAuth);
    }
}
