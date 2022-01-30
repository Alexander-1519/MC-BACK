package com.ryhnik.service;

import com.ryhnik.dto.user.UserAuth;
import com.ryhnik.dto.user.UserAuthRequest;
import com.ryhnik.dto.user.UserInputCreateDto;
import com.ryhnik.dto.user.UserRoleDto;
import com.ryhnik.entity.*;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.EntityNotFoundException;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.mapper.UserMapper;
import com.ryhnik.repository.MasterRepository;
import com.ryhnik.repository.RoleRepository;
import com.ryhnik.repository.UserRepository;
import com.ryhnik.service.jwt.JwtProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final MasterRepository masterRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       MasterRepository masterRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.masterRepository = masterRepository;
    }

    public User registerUser(UserInputCreateDto createDto) {
        boolean existsByUsername = userRepository.existsByUsername(createDto.getUsername());
        if (existsByUsername) {
            throw ExceptionBuilder.builder(Code.UNEXPECTED)
                    .withMessage("username exist = " + createDto.getUsername())
                    .build(MasterClubException.class);
        }

        User user = userMapper.toUser(createDto);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (createDto.getRole() == UserRoleDto.MASTER) {
            Master master = new Master();
            master.setInfo(createDto.getInfo());
            master.setStartedAt(LocalDate.ofEpochDay(createDto.getStartedAt()));
            master.setCategory(createDto.getCategory());
            Master savedMaster = masterRepository.save(master);

            user.addMaster(savedMaster);
        }

        UserRole role = roleRepository.findByName(UserRoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));

        user.setRole(role);

        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserAuth userAuth(UserAuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException(Code.UNEXPECTED);
        }

        return new UserAuth(jwtProvider.generateToken(user.getUsername()), user.getRole().getName().name());
    }
}
