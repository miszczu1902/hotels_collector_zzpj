package pl.lodz.p.it.zzpj.hotelscollector.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.user.activation.token.UserActivationTokenRepository;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.UserAlreadyExistsException;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActivationTokenRepository userActivationTokenRepository;

    @Transactional
    public Optional<UserEntity> addUserFromRegistration(RegisterUserDTO user) throws UserAlreadyExistsException {
        checkIfUserAlreadyExists(user.username(), user.email());
        return Optional.of(userRepository.save(new UserEntity(user.email(), user.username(),
                passwordEncoder.encode(user.password()), false, true, UserRole.CLIENT)));
    }

    @Transactional
    public Optional<UserEntity> verifyUser(String token) {
        return userActivationTokenRepository.findById(token).flatMap(userToken -> {
            if (userToken.getExpiresAt().isAfter(LocalDateTime.now())) {
                userActivationTokenRepository.delete(userToken);
                userToken.getUser().setIsActive(true);
                return Optional.of(userRepository.save(userToken.getUser()));
            }
            log.warn("Token {} is already expired", token);
            return Optional.empty();
        }).or(() -> {
            log.warn("Token {} does not exist in the database", token);
            return Optional.empty();
        });
    }

    private void checkIfUserAlreadyExists(String username, String email) throws UserAlreadyExistsException {
        if (userRepository.existsUserEntityByUsername(username)) {
            log.warn("User with username {} already exists.", username);
            throw new UserAlreadyExistsException("User with that username already exists.");
        } else if (userRepository.existsUserEntityByEmail(email)) {
            log.warn("User with email {} already exists.", email);
            throw new UserAlreadyExistsException("User with that email already exists.");
        }
    }


}
