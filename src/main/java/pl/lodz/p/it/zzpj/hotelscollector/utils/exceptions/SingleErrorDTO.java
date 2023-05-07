package pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record SingleErrorDTO(HttpStatus status, LocalDateTime timestamp, String message) {
}

