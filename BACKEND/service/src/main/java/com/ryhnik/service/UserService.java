package com.ryhnik.service;

import com.ryhnik.dto.user.UserAuth;
import com.ryhnik.dto.user.UserAuthRequest;
import com.ryhnik.entity.User;
import com.ryhnik.entity.UserRole;
import com.ryhnik.entity.UserRoleName;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.EntityNotFoundException;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.repository.RoleRepository;
import com.ryhnik.repository.UserRepository;
import com.ryhnik.service.jwt.JwtProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
    }

    public User registerUser(User user) {
        boolean existsByUsername = userRepository.existsByUsername(user.getUsername());
        if (existsByUsername) {
            throw ExceptionBuilder.builder(Code.UNEXPECTED)
                    .withMessage("username exist = " + user.getUsername())
                    .build(MasterClubException.class);
        }

        UserRole role = roleRepository.findByName(UserRoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
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

    public UserAuth userAuth(UserAuthRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new EntityNotFoundException(Code.UNEXPECTED);
        }

        return new UserAuth(jwtProvider.generateToken(user.getUsername()), user.getRole().getName().name());
    }
}
