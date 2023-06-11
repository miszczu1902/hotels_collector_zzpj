package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserActivationTokenEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;

import java.time.LocalDateTime;
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

    @Scheduled(fixedRateString = "PT1H")
    @Transactional
    public void deleteExpiredPasswords() {
        userActivationTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}

