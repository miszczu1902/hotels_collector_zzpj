package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.controller.HotelMapper;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.controller.RoomMapper;
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

    @Transactional
    public Optional<HotelResponse> getHotelById(String id) {
        return hotelRepository.findById(id).map(HotelMapper::toHotelResponse);
    }

    @Transactional
    public HotelListResponse getAllHotels() {
        return new HotelListResponse().hotels(hotelRepository.findAll().stream().map(HotelMapper::toHotelResponse).toList());
    }

    @Transactional
    public RoomListResponse getAllRoomsInHotel(String id) {
        var hotel = hotelRepository.findById(id);
        return hotel.map(hotelEntity -> new RoomListResponse().rooms(hotelEntity.getRoomEntities().stream().map(RoomMapper::toRoomResponse).toList())).orElse(null);
    }

    @Transactional
    public void addRoomInHotel(String id, RoomRequest roomRequest) {
        var hotel = hotelRepository.findById(id);
        hotel.ifPresent(hotelEntity -> hotelEntity.getRoomEntities().add(RoomMapper.toRoomEntity(roomRequest)));
    }

}
