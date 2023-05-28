package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;

public interface RoomRepository extends JpaRepository<RoomEntity, String> {
}
