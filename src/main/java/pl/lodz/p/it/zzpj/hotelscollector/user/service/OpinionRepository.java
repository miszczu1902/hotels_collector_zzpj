package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.OpinionEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;

import java.util.Optional;


public interface OpinionRepository extends JpaRepository<OpinionEntity, String> {

    Optional<OpinionEntity> findOpinionEntitiesByUserEntity(Optional<UserEntity> userEntity);

}