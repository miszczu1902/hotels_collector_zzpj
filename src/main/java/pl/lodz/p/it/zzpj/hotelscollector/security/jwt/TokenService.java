package pl.lodz.p.it.zzpj.hotelscollector.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtEncoder encoder;

    public String generateToken(String username, Authentication authentication) {
        final JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .subject(username).claim("roles",
                        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

