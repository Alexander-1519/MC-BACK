package com.ryhnik.service;

import com.ryhnik.entity.SecurityUser;
import com.ryhnik.entity.User;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.EntityNotFoundException;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> ExceptionBuilder.builder(Code.USER_EXCEPTION)
                        .withMessage("Can't find user with username = " + username)
                        .build(MasterClubException.class));

        return SecurityUser.fromEntityToCustomUser(user);
    }
}
