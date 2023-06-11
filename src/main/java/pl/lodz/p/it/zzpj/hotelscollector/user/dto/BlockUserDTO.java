package pl.lodz.p.it.zzpj.hotelscollector.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BlockUserDTO(@NotBlank @Pattern(regexp = "\\w{6,32}") String username) {
}
