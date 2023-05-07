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

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> UserAlreadyExistsException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ResponseBodyErrors(List.of(new SingleErrorDTO(CONFLICT, LocalDateTime.now(), ex.getMessage()))),
                new HttpHeaders(), CONFLICT, request);
    }
}

