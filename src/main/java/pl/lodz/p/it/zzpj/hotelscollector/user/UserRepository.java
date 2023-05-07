package pl.lodz.p.it.zzpj.hotelscollector.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    Boolean existsUserEntityByEmail(String username);

    Boolean existsUserEntityByUsername(String username);

}
