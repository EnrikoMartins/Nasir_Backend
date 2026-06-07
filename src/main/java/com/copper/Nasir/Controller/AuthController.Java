package com.copper.Nasir.Controller;
 
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
 
    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody User user) {
        String token = authService.register(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("token", token));
    }
 
    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");
        String token = authService.login(email, password);
        return ResponseEntity.ok(Map.of("token", token));
    }
}