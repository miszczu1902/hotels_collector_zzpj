package pl.lodz.p.it.zzpj.hotelscollector.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(@Email @NotNull @Size(max = 254) String email,
                      @NotNull @Size(min = 6, max = 32) String username,
                      @NotNull Boolean activationStatus,
                      @NotNull Boolean enabledStatus) {
}

