package com.copper.Nasir.Controller;

/* import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Security.AuthService;
import com.copper.Nasir.Security.LoginRequest;
import com.copper.Nasir.Security.LoginResponse;
import com.copper.Nasir.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // Cria o construtor automático para os dois serviços abaixo
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    // =========================================================================
    // 1. ENDPOINT DE CADASTRO
    // =========================================================================
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // =========================================================================
    // 2. ENDPOINT DE LOGIN
    // =========================================================================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
*/
