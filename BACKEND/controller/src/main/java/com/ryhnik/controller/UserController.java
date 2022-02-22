package com.ryhnik.controller;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.user.UserOutputDto;
import com.ryhnik.entity.User;
import com.ryhnik.mapper.UserMapper;
import com.ryhnik.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

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

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageDto<UserOutputDto>> getAll(@RequestParam Integer page,
                                                         @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users = userService.findAll(pageRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toPagedUserOutputDto(users, users.getPageable()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserOutputDto> getById(@PathVariable Long id) {
        User user = userService.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toUserOutputDto(user));
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserOutputDto> getCurrentUser(Principal principal) {
        User currentUser = userService.getCurrentUser(principal.getName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toUserOutputDto(currentUser));
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<String> saveAvatar(@RequestBody MultipartFile multipartFile,
                                             @PathVariable Long id,
                                             Principal principal) {
        String url = userService.saveAvatar(multipartFile, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(url);
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<String> getAvatarById(@PathVariable Long id, Principal principal) {
        String url = userService.getAvatarById(id);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(url);
    }
}
