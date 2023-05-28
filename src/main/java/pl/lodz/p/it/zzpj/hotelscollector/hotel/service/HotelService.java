package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.HotelRequest;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.controller.HotelMapper;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Transactional
    public long addHotel(HotelRequest hotelRequest) {
        HotelEntity createdHotel = hotelRepository.save(HotelMapper.toHotelEntity(hotelRequest));
        return createdHotel.getId();
    }

    public Optional<HotelEntity> getHotelById(String id) {
        return hotelRepository.findById(id);
    }
}
