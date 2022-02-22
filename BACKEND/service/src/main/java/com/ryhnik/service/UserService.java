package com.ryhnik.service;

import com.ryhnik.dto.user.UserAuth;
import com.ryhnik.dto.user.UserAuthRequest;
import com.ryhnik.dto.user.UserInputCreateDto;
import com.ryhnik.dto.user.UserRoleDto;
import com.ryhnik.entity.Master;
import com.ryhnik.entity.User;
import com.ryhnik.entity.UserRole;
import com.ryhnik.entity.UserRoleName;
import com.ryhnik.exception.*;
import com.ryhnik.mapper.UserMapper;
import com.ryhnik.repository.MasterRepository;
import com.ryhnik.repository.RoleRepository;
import com.ryhnik.repository.UserRepository;
import com.ryhnik.service.jwt.JwtProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final MasterRepository masterRepository;
    private final EmailService emailService;
    private final FileService fileService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       MasterRepository masterRepository,
                       EmailService emailService,
                       FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.masterRepository = masterRepository;
        this.emailService = emailService;
        this.fileService = fileService;
    }

    @Transactional
    public User registerUser(UserInputCreateDto createDto) {
        boolean existsByUsername = userRepository.existsByUsername(createDto.getUsername());
        if (existsByUsername) {
            throw ExceptionBuilder.builder(Code.USER_EXCEPTION)
                    .withMessage("username exist = " + createDto.getUsername())
                    .build(MasterClubException.class);
        }

        User user = userMapper.toUser(createDto);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        UserRole role = roleRepository.findByName(UserRoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(Code.ROLE_NOT_FOUND));

        user.setRole(role);

        user.setIsMaster(false);
        user.setApproved(false);

        User saved = userRepository.save(user);

        Master savedMaster = null;
        if (createDto.getRole() == UserRoleDto.MASTER) {
            Master master = new Master();
            master.setInfo(createDto.getInfo());
            if (createDto.getStartedAt() != null) {
                master.setStartedAt(LocalDate.ofEpochDay(createDto.getStartedAt()));
            }
            master.setCategory(createDto.getCategory());
            master.setUser(saved);
            user.setIsMaster(true);
            savedMaster = masterRepository.save(master);
        }

        saved.setMaster(savedMaster);
        return userRepository.save(saved);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(id));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserAuth userAuth(UserAuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(Code.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw ExceptionBuilder.builder(Code.VALIDATION_FAILED)
                    .withMessage("Passwords are not match")
                    .build(MasterClubException.class);
        }

        return new UserAuth(jwtProvider.generateToken(user.getUsername()), user.getRole().getName().name());
    }

    public void checkEmail(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) {
            throw ExceptionBuilder.builder(Code.EMAIL_EXCEPTION)
                    .withMessage("Email already exists")
                    .build(MasterClubException.class);
        }
    }

    public void approveAccount(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (!existsByEmail) {
            throw ExceptionBuilder.builder(Code.EMAIL_EXCEPTION)
                    .withMessage("Can't find email = " + email)
                    .build(MasterClubException.class);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(Code.USER_NOT_FOUND));


        user.setApproved(true);
        userRepository.save(user);
    }

    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(username));
    }

    public String saveAvatar(MultipartFile multipartFile, String username) {
        return fileService.saveAvatar(multipartFile, username);
    }

    public String getAvatarById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Code.USER_NOT_FOUND));

        return fileService.findByName(user.getUsername()).getHttpRequest().getRequestLine().getUri();
    }
}
