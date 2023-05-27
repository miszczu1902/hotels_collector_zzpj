package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
