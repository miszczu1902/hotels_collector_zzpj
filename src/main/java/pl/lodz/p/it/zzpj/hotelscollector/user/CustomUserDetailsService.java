package pl.lodz.p.it.zzpj.hotelscollector.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return new UserPrincipal(userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User {} not found", username);
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }));
    }

}
