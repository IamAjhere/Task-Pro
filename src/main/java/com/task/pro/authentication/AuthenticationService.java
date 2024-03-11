package com.task.pro.authentication;

import com.task.pro.config.JWTService;
import com.task.pro.exceptions.CustomException;
import com.task.pro.exceptions.CustomExceptionStore;
import com.task.pro.user.Role;
import com.task.pro.user.User;
import com.task.pro.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final UserRepository repository;
    private  final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var checkIfAlreadyExist = repository.findByEmail(request.getEmail());
        // Return Error if User already exist
        checkIfAlreadyExist.ifPresent(user -> {
            throw new CustomException(CustomExceptionStore.USER_EXISTS);
        });
        //Return Error if Team Member role is selected
        if (request.getRole() == Role.TEAM_MEMBER) {
            throw new CustomException(CustomExceptionStore.TEAM_MEMBER_NOT_ALLOWED);
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        repository.save(user);
        var jwtToken = getJwtToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String getJwtToken(User user) {
        return jwtService.generateToken(Map.of(
                "userId", user.getId(),
                "email", user.getEmail(),
                "userType", user.getRole()
        ), user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new CustomException(CustomExceptionStore.INVALID_LOGIN);
        }

        var user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        var jwtToken = getJwtToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
