package com.tehzzcode.identity_service.service.impl;

import com.tehzzcode.identity_service.dto.GetTokenDTO;
import com.tehzzcode.identity_service.dto.UserIdentityDTO;
import com.tehzzcode.identity_service.entity.UserIdentity;
import com.tehzzcode.identity_service.repository.UserIdentityRepository;
import com.tehzzcode.identity_service.service.AuthService;
import com.tehzzcode.identity_service.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private UserIdentityRepository identityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public String registerUser(UserIdentityDTO userIdentityDTO) {
        if (userIdentityDTO == null) {
            log.warn("Attempted to register a null userIdentity");
            throw new IllegalArgumentException("User identity cannot be null");
        }

        if (!isValidUserIdentity(userIdentityDTO)) {
            log.warn("Invalid userIdentityDTO data: {}", userIdentityDTO);
            throw new IllegalArgumentException("Invalid user data provided");
        }

        log.info("Registering user: {}", userIdentityDTO.getEmail());

        try {
            userIdentityDTO.setPassword(passwordEncoder.encode(userIdentityDTO.getPassword()));
            identityRepository.save(userIdentityDTO.toEntity());
            log.info("User registered successfully: {}", userIdentityDTO.getEmail());
            return "User added to the database";
        } catch (DataIntegrityViolationException e) {
            log.error("User registration failed due to data integrity violation: {}", e.getMessage());
            throw new RuntimeException("User registration failed: Duplicate or invalid data");
        } catch (Exception e) {
            log.error("Unexpected error during user registration: {}", e.getMessage());
            throw new RuntimeException("User registration failed due to an unexpected error");
        }
    }


    public String getToken(GetTokenDTO getTokenDTO) {
        if (getTokenDTO == null || getTokenDTO.getUsername().isBlank() || getTokenDTO.getPassword().isBlank()) {
            log.warn("Invalid token request: missing username or password");
            throw new IllegalArgumentException("Username and password are required");
        }

        Optional<UserIdentity> optionalUser = identityRepository.findByEmail(getTokenDTO.getUsername());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserIdentity user = optionalUser.get();
        if (!passwordEncoder.matches(getTokenDTO.getPassword(), user.getPassword())) {
            log.warn("Invalid password for user: {}", getTokenDTO.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }

        log.info("Token generated for user: {}", getTokenDTO.getUsername());
        return jwtService.generateToken(user.getUsername());
    }


    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


    private boolean isValidUserIdentity(UserIdentityDTO userIdentityDTO) {
        return userIdentityDTO.getEmail() != null && !userIdentityDTO.getEmail().isBlank()
            && userIdentityDTO.getPassword() != null && !userIdentityDTO.getPassword().isBlank();
    }
}
