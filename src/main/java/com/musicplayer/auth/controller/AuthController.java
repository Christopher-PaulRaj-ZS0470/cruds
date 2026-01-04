package com.musicplayer.modules.auth.controller;

import com.musicplayer.auth.service.ApiCallerService;
import com.musicplayer.modules.auth.dto.AuthenticationRequest;
import com.musicplayer.modules.auth.dto.AuthenticationResponse;
import com.musicplayer.modules.auth.dto.RegisterRequest;
import com.musicplayer.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final ApiCallerService apiCallerService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/authenticateAndCallConfig")
    public ResponseEntity<String> authenticateAndCallConfig(@RequestBody AuthenticationRequest request,
                                                            @RequestParam String variable) {
        // 1. Authenticate and get JWT
        AuthenticationResponse authResponse = service.authenticate(request);
        String jwtToken = authResponse.getAccessToken();

        // 2. Call config API with JWT
        String configResponse = apiCallerService.callConfigApiWithJwt(jwtToken, variable);

        return ResponseEntity.ok(configResponse);
    }

}
