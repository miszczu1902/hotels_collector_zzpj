package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {

    List<RoomEntity> findByHotelEntity(HotelEntity hotel);
}
