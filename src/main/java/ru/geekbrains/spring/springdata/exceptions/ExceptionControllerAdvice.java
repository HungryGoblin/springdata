package ru.geekbrains.spring.springdata.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(BadRequestException e) {
        log.error(e.getMessage());
        CommonException err = new CommonException(e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        CommonException err = new CommonException(e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleNoAuthException(NoAuthException e) {
        log.error(e.getMessage());
        CommonException err = new CommonException(e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }
}
