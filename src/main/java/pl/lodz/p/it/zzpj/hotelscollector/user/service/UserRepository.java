package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(Long id);

    Boolean existsUserEntityByEmail(String username);

    Boolean existsUserEntityByUsername(String username);

}
