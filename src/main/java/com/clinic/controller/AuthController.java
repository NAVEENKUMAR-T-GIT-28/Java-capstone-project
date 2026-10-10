package com.clinic.controller;

import com.clinic.dto.LoginRequest;
import com.clinic.entity.User;
import com.clinic.repository.UserRepository;
import com.clinic.service.TokenService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users; private final PasswordEncoder encoder; private final TokenService tokens;
    public AuthController(UserRepository users, PasswordEncoder encoder, TokenService tokens){this.users=users;this.encoder=encoder;this.tokens=tokens;}
    @PostMapping("/token")
    public ResponseEntity<?> token(@Valid @RequestBody LoginRequest request) {
        User user=users.findByEmail(request.getEmail()).orElse(null);
        if(user==null || !encoder.matches(request.getPassword(), user.getPasswordHash()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid email or password"));
        return ResponseEntity.ok(Map.of("token",tokens.generateToken(user.getEmail()),"email",user.getEmail(),"role",user.getRole(),"tokenType","Bearer"));
    }
}
