package pl.lodz.p.it.zzpj.hotelscollector.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import javax.validation.constraints.NotNull;

public record ModifyRoleDTO(@NotBlank @Pattern(regexp = "\\w{6,32}") String username, @NotNull String role) {

}
