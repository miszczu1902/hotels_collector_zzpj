package pl.lodz.p.it.zzpj.hotelscollector.user;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO(@NotBlank String token) {
}
