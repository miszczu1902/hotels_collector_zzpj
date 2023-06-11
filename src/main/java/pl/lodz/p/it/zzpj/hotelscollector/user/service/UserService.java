package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.utils.UserRole;
import pl.lodz.p.it.zzpj.hotelscollector.user.dto.RegisterUserDTO;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;
import pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions.*;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    @Autowired
    private HttpServletRequest request;

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

    @Transactional
    public void modifyUserRole(String username, String role) throws RoleDoesNotExistException, UserOwnsRoleException, UserDoesntExistException, CanNotModifySelfStateException {
        final String user = request.getUserPrincipal().getName();
        if (username.equals(user)) {
            log.warn("you cant modify self role");
            throw new CanNotModifySelfStateException("You cant modify self role");
        }
        var modifiedUser = userRepository.findByUsername(username);
        if (modifiedUser.isPresent()) {
            var modification = modifiedUser.get();
            try {
                UserRole userRole = UserRole.valueOf(role.toUpperCase(Locale.ROOT));
                if (userRole.equals(modification.getRole())) {
                    log.warn("This user owns this role {}", role);
                    throw new UserOwnsRoleException("This user owns this role");
                }
                modification.setRole(userRole);
            } catch (IllegalArgumentException e) {
                log.warn("Specified role doesnt exist {}", role);
                throw new RoleDoesNotExistException("Specified role doesnt exist, roles in system are: ADMIN, CLIENT, MANAGER");
            }
            userRepository.save(modification);
        } else {
            log.warn("Specified user doesnt exist {}", username);
            throw new UserDoesntExistException("User with specified username doesnt exist");
        }
    }

    @Transactional
    public void blockUser(String username) throws UserIsBlockedException, CanNotModifySelfStateException, UserDoesntExistException {
        final String user = request.getUserPrincipal().getName();
        if (username.equals(user)) {
            log.warn("you cant block yourself");
            throw new CanNotModifySelfStateException("You cant block yourself");
        }
        var modifiedUser = userRepository.findByUsername(username);
        if (modifiedUser.isPresent()) {
            var modification = modifiedUser.get();
            if (!modification.getIsEnable()) {
                log.warn("You cant block user that is already blocked {}", username);
                throw new UserIsBlockedException("User is already blocked");
            }
            modification.setIsEnable(false);
            userRepository.save(modification);
        } else {
            log.warn("Specified user doesnt exist {}", username);
            throw new UserDoesntExistException("User with specified username doesnt exist");
        }
    }

    @Transactional
    public void unblockUser(String username) throws UserDoesntExistException, UserIsNotBlockedException, CanNotModifySelfStateException {
        final String user = request.getUserPrincipal().getName();
        if (username.equals(user)) {
            log.warn("you cant unblock yourself");
            throw new CanNotModifySelfStateException("You cant unblock yourself");
        }
        var modifiedUser = userRepository.findByUsername(username);
        if (modifiedUser.isPresent()) {
            var modification = modifiedUser.get();
            if (modification.getIsEnable()) {
                log.warn("You cant unblock user that is not blocked {}", username);
                throw new UserIsNotBlockedException("User is not blocked");
            }
            modification.setIsEnable(true);
            userRepository.save(modification);
        } else {
            log.warn("Specified user doesnt exist {}", username);
            throw new UserDoesntExistException("User with specified username doesnt exist");
        }
    }
}
