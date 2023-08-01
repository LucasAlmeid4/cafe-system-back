package io.github.lucasalmeida.cafeconnection.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OpcaoJaSelecionadaException.class)
    public ResponseEntity<String> handleOpcaoJaSelecionadaException(OpcaoJaSelecionadaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
