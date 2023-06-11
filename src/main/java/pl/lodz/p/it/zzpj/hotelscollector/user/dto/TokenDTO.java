package pl.lodz.p.it.zzpj.hotelscollector.user.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO(@NotBlank String token) {
}
