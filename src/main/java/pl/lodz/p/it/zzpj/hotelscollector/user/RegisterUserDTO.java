package pl.lodz.p.it.zzpj.hotelscollector.user;

import jakarta.validation.constraints.*;

public record RegisterUserDTO(@Email @NotNull @Size(max = 254) String email,
                              @NotBlank @Pattern(regexp = "\\w{6,32}") String username,
                              @NotBlank @Pattern(regexp = "(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])[\\w!@#$%^&()=+,.<>/?';:{}|]{9,64}") String password) {
}


