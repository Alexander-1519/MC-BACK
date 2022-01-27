package com.ryhnik.service.jwt;

import com.ryhnik.entity.User;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.EntityNotFoundException;
import com.ryhnik.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.days}")
    private String days;
    private final UserRepository userRepository;

    public JwtProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String login) {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().getName());
        claims.put("username", login);
        Date date = Date.from(LocalDate.now().plusDays(Integer.parseInt(days)).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            LOG.warn("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (String) claims.get("username");
    }
}
