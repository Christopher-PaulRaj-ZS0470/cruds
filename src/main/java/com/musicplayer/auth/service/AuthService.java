package com.musicplayer.modules.auth.service;

import com.musicplayer.auth.entity.User;
import com.musicplayer.auth.service.JwtService;
import com.musicplayer.config.repo.UserRepository;

import com.musicplayer.modules.auth.Role;
import com.musicplayer.modules.auth.dto.AuthenticationRequest;
import com.musicplayer.modules.auth.dto.AuthenticationResponse;
import com.musicplayer.modules.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser =  userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        // In a real app we would generate a refresh token here too
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken("dummy_refresh_token_impl_later")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken("dummy_refresh_token_impl_later")
                .build();
    }
}
