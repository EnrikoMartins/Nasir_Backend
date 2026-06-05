package com.copper.Nasir.Security;

/* import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest request) {
        // O Spring Security usa o AuthenticationManager para validar e-mail e senha automaticamente
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // Se a linha acima não estourar erro (BadCredentialsException), significa que o login é válido!
        // Então geramos o token usando o e-mail do usuário
        String token = jwtService.generateToken(request.getEmail());

        // Retorna o token envelopado no DTO de resposta
        return new LoginResponse(token);
    }
}
*/
