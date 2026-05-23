package com.copper.Nasir.Handler;

import com.copper.Nasir.Exception.UserNotFoundException;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException)
    public ResponseEntity<RestErrorMessage> userNotFoundException() {

    }
}
/// Sugestão: @ExceptionHandler(UserNotFoundException.class)
    // public ResponseEntity<RestErrorMessage> userNotFoundExceptionHandler(UserNotFoundException exception) {
       // RestErrorMessage errorResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
       // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
