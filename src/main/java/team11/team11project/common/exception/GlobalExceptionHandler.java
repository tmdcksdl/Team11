package team11.team11project.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundAuthException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundAuthException(NotFoundAuthException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTokenException(InvalidTokenException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateEmailException(DuplicateEmailException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handlerResponseStatusException(ResponseStatusException e) {

        log.error("오류 메시지: {}", e.getMessage());

        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), e.getStatusCode().value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserRoleNotFoundException(UserRoleNotFoundException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundEmailException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundEmailException(NotFoundEmailException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleIncorrectPasswordException(IncorrectPasswordException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(ReviewNotAllowedException.class)
    public ResponseEntity<ExceptionResponse> handleReviewNotAllowedException(ReviewNotAllowedException e) {
        log.error("오류 메시지 : {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }
}
