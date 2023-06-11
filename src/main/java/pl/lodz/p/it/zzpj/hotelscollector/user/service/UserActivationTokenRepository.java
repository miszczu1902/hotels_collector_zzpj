package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserActivationTokenEntity;

import java.time.LocalDateTime;

@Repository
public interface UserActivationTokenRepository extends JpaRepository<UserActivationTokenEntity, String> {
    @Modifying
    @Query("DELETE FROM UserActivationTokenEntity user WHERE user.user.username = ?1 AND NOT user.token = ?2")
    void deleteAllByUser(String user, String newToken);

    @Modifying
    @Query("DELETE FROM UserActivationTokenEntity where expiresAt < ?1")
    void deleteExpiredTokens(LocalDateTime dateTime);
}

