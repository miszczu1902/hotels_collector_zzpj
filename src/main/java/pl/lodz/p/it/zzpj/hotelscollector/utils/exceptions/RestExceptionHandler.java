package pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> UserAlreadyExistsException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(CONFLICT, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), CONFLICT, request);
    }
    @ExceptionHandler(value = {RoleDoesNotExistException.class})
    public ResponseEntity<Object> RoleDoesNotExistException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(CONFLICT, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(value = {UserDoesntExistException.class})
    public ResponseEntity<Object> UserDoesntExistException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(CONFLICT, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(value = {CanNotModifySelfStateException.class})
    public ResponseEntity<Object> CanNotModifySelfStateException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(CONFLICT, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(value = {UserIsNotBlockedException.class})
    public ResponseEntity<Object> UserIsNotBlockedException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(BAD_REQUEST, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserIsBlockedException.class})
    public ResponseEntity<Object> UserIsBlockedException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(BAD_REQUEST, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserOwnsRoleException.class})
    public ResponseEntity<Object> UserOwnsRoleException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(BAD_REQUEST, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), BAD_REQUEST, request);
    }
}

