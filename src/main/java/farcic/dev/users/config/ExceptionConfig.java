package farcic.dev.users.config;

import farcic.dev.users.dto.response.ErrorResponseDto;
import farcic.dev.users.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> userAlreadyException(UserAlreadyExistsException e) {

        ErrorResponseDto error = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFoundException(Exception e) {

        ErrorResponseDto error = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(CepNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> cepNotFoundException(Exception e) {
        ErrorResponseDto error = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> validationException(MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().get(0).getDefaultMessage();

        ErrorResponseDto error = new ErrorResponseDto(
                message,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(InvalidPasswordExeception.class)
    public ResponseEntity<ErrorResponseDto> invalidPasswordException(Exception e) {

        ErrorResponseDto error = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    public ResponseEntity<ErrorResponseDto> passwordConfirmationException(Exception e) {
        ErrorResponseDto error = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> accessDeniedException(Exception e) {

        ErrorResponseDto error = new ErrorResponseDto(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

}
