package pl.lodz.p.it.zzpj.hotelscollector.user.activation.token;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.hotelscollector.user.UserEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_activation_tokens")
public class UserActivationTokenEntity {
    @Id
    @NotNull
    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserEntity user;
}

