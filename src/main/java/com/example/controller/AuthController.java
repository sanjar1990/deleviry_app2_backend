package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<ApiResponseDTO>registration(@Valid @RequestBody RegistrationDTO registrationDTO){
        return ResponseEntity.ok(authService.registration(registrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO>login(@Valid @RequestBody AuthDTO authDTO){
        return ResponseEntity.ok(authService.login(authDTO));
    }
}
