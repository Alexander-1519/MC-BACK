package com.ryhnik.service;

import com.ryhnik.dto.user.UserAuth;
import com.ryhnik.dto.user.UserAuthRequest;
import com.ryhnik.dto.user.UserInputCreateDto;
import com.ryhnik.dto.user.UserRoleDto;
import com.ryhnik.entity.Master;
import com.ryhnik.entity.User;
import com.ryhnik.entity.UserRole;
import com.ryhnik.entity.UserRoleName;
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
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       MasterRepository masterRepository,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.masterRepository = masterRepository;
        this.emailService = emailService;
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

        UserRole role = roleRepository.findByName(UserRoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));

        user.setRole(role);

        User saved = userRepository.save(user);

        if (createDto.getRole() == UserRoleDto.MASTER) {
            Master master = new Master();
            master.setInfo(createDto.getInfo());
            if(createDto.getStartedAt() != null) {
                master.setStartedAt(LocalDate.ofEpochDay(createDto.getStartedAt()));
            }
            master.setCategory(createDto.getCategory());
            master.setUser(saved);
            Master savedMaster = masterRepository.save(master);

//            saved.addMaster(savedMaster);
        }

        return userRepository.save(saved);
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

    public void checkEmail(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) {
            throw ExceptionBuilder.builder(Code.UNEXPECTED)
                    .withMessage("Email already exists")
                    .build(MasterClubException.class);
        }
    }

    public void approveAccount(String email, String username) {
        boolean existsByEmailAndUsername = userRepository.existsByEmailAndUsername(email, username);
        if(!existsByEmailAndUsername){
            throw ExceptionBuilder.builder(Code.UNEXPECTED)
                    .withMessage("You have no permission to this operation")
                    .build(MasterClubException.class);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));


        user.setApproved(true);
        userRepository.save(user);
    }
}
