package ru.Bodyaaaa.exeptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    // 401
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<String> handleAuthenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<>("UNAUTHORIZED: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    //403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return new ResponseEntity<>("ACCESS_DENIED: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

}