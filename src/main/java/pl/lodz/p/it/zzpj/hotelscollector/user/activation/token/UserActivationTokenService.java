package pl.lodz.p.it.zzpj.hotelscollector.user.activation.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.hotelscollector.user.UserEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserActivationTokenService {
    private final UserActivationTokenRepository userActivationTokenRepository;
    private final UserRepository userRepository;

    public String createVerificationToken(UserEntity user) {
        final String token = UUID.randomUUID().toString();
        userRepository.findByUsername(user.getUsername()).map(userEntity -> userActivationTokenRepository.save(
                new UserActivationTokenEntity(token, LocalDateTime.now().plusDays(1), userEntity)));
        return token;
    }

    public Optional<UserActivationTokenEntity> resetVerificationToken(String username) {
        return userRepository.findByUsername(username).map(user -> userActivationTokenRepository.save(
                new UserActivationTokenEntity(UUID.randomUUID().toString(), LocalDateTime.now().plusDays(1), user))).or(() -> {
            log.warn("User {} not found", username);
            return Optional.empty();
        });
    }

    @Transactional
    public void deleteOldTokensForUser(String username, String token) {
        userActivationTokenRepository.deleteAllByUser(username, token);
    }

    @Scheduled(fixedRateString = "PT1H")
    @Transactional
    public void deleteExpiredPasswords() {
        userActivationTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}

