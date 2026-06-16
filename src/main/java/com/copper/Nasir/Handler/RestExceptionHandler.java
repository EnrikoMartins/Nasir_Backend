package com.copper.Nasir.Handler;

import com.copper.Nasir.Exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorMessage> userNotFoundException(UserNotFoundException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // ↓ BadCredentialsException  → lançada pelo AuthenticationManager quando a senha está errada.
    //   InternalAuthenticationServiceException → lançada quando o usuário não existe no banco
    //   (o Spring Security encapsula UsernameNotFoundException nela por segurança).
    //   Sem esses handlers, ambas sobem pela filter chain e chegam ao ExceptionTranslationFilter
    //   do Spring Security, que as converte em 403 via Http403ForbiddenEntryPoint (padrão do Security 6).
    //   Com os handlers aqui, o Spring MVC intercepta antes e retorna 401 com mensagem clara.
    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<RestErrorMessage> authenticationException(RuntimeException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.UNAUTHORIZED, "E-mail ou senha incorretos.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}