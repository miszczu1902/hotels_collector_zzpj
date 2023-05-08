package pl.lodz.p.it.zzpj.hotelscollector.user.activation.token;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TokenUserDTO(@NotBlank String username,
                           @Email @NotNull String email,
                           @NotBlank String token,
                           @NotBlank String callbackRoute) {
}

