package com.copper.Nasir.Controller;

import com.copper.Nasir.DTO.AuthResponseDTO;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody User user) {
        AuthResponseDTO response = authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");
        AuthResponseDTO response = authService.login(email, password);
        return ResponseEntity.ok(response);
    }
}
