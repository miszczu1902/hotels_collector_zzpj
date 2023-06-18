package pl.lodz.p.it.zzpj.hotelscollector.rent.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;
import pl.lodz.p.it.zzpj.hotelscollector.rent.entity.RentEntity;
import pl.lodz.p.it.zzpj.hotelscollector.user.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, UUID> {

    List<RentEntity> findAllByUser(UserEntity user);

    Optional<RentEntity> findByIdAndIsActive(UUID rentId, boolean isActive);

    Optional<RentEntity> findByRoomAndBeginTimeLessThanEqualAndEndTimeGreaterThanEqualAndIsActive(RoomEntity room, LocalDate beginTime, LocalDate endTime, boolean isActive);

}
