package pl.lodz.p.it.zzpj.hotelscollector.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.zzpj.hotelscollector.security.jwt.TokenService;
import pl.lodz.p.it.zzpj.hotelscollector.user.service.UserService;
import pl.lodz.p.it.zzpj.hotelscollector.user.activation.token.RegistrationCompleteEvent;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.*;
import pl.lodz.p.it.zzpj.hotelscollector.user.service.UserActivationTokenService;

import java.net.URI;

import static pl.lodz.p.it.zzpj.hotelscollector.utils.Constans.URI_USERS;

@RequiredArgsConstructor
@RestController
@RequestMapping(URI_USERS)
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserActivationTokenService userActivationTokenService;
    private final ApplicationEventPublisher eventPublisher;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Hi! It works!");
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody RegisterUserDTO user) {
        return userService.addUserFromRegistration(user).map(userEntity -> {
            final String token = userActivationTokenService.createVerificationToken(userEntity);
            eventPublisher.publishEvent(new RegistrationCompleteEvent(new TokenUserDTO(userEntity.getUsername(),
                    userEntity.getEmail(), token, user.callbackRoute())));
            return ResponseEntity.created(URI.create(URI_USERS + "/" + userEntity.getEmail()))
                    .body(new UserDTO(userEntity.getEmail(), userEntity.getUsername(), userEntity.getIsActive(),
                            userEntity.getIsEnable()));
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/activate")
    public ResponseEntity<UserDTO> confirmRegistration(@Valid @RequestBody TokenDTO tokenDTO) {
        return userService.verifyUser(tokenDTO.token()).map(user -> ResponseEntity.ok(
                        new UserDTO(user.getEmail(), user.getUsername(), user.getIsActive(), user.getIsEnable())))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }


    @PostMapping("/login")
    public ResponseEntity<Void> token(@RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateToken(authentication))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/modify-role")
    public ResponseEntity<Void> modifyRole(@RequestBody ModifyRoleDTO modifyRoleDTO) {
        userService.modifyUserRole(modifyRoleDTO.username(), modifyRoleDTO.role());
        return ResponseEntity.ok().build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/block-user")
    public ResponseEntity<Void> blockUser(@RequestBody BlockUserDTO blockUserDTO) {
        userService.blockUser(blockUserDTO.username());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/unblock-user")
    public ResponseEntity<Void> unblockUser(@RequestBody BlockUserDTO blockUserDTO) {
        userService.unblockUser(blockUserDTO.username());
        return ResponseEntity.ok().build();
    }
}
