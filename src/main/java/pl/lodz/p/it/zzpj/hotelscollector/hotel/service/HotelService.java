package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.controller.HotelMapper;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;

import java.util.List;
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

    @Transactional
    public Optional<HotelEntity> getHotelById(String id) {
        return hotelRepository.findById(id);
    }

    @Transactional
    public List<HotelEntity> getAllHotels() {
        var hotels = hotelRepository.findAll();
        if(hotels.isEmpty()) {
            return null;
        }
        return hotels;
    }


    @Transactional
    public boolean deleteHotel(String id) {
        var hotel = hotelRepository.findById(id);
        if(hotel.isPresent()) {
            hotelRepository.delete(hotel.get());
        } else {
            return false;
        }
        return true;
    }

}
