package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;

import java.util.List;

public interface HotelRepository extends JpaRepository<HotelEntity, String> {


}
