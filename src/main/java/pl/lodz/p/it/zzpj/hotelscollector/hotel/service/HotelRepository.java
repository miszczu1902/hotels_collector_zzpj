package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.Hotel;

import java.util.List;

public interface HotelRepository extends CrudRepository<Hotel, Long> {

    List<Hotel> findHotelByCity(String city);

    Hotel findHotelById(long id);

}
