package pl.lodz.p.it.zzpj.hotelscollector.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.OpinionEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;


public interface OpinionRepository extends JpaRepository<OpinionEntity, String> {

    Optional<OpinionEntity> findOpinionEntitiesByUserEntity(Optional<UserEntity> userEntity);

    Optional<OpinionEntity> findOpinionEntitiesByUserEntityAndHotelEntity(Optional<UserEntity> userEntity, Optional<HotelEntity> hotelEntity);

}