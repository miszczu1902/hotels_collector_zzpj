package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.HotelsApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.HotelService;

import java.net.URI;

import static pl.lodz.p.it.zzpj.hotelscollector.utils.Constans.URI_HOTELS;

@RequiredArgsConstructor
@RestController
public class HotelController implements HotelsApi {

    private final HotelService hotelService;

    @Override
    public ResponseEntity<HotelResponse> addHotel(HotelRequest hotelRequest) {
        long createdHotelId = hotelService.addHotel(hotelRequest);
        return ResponseEntity
                .created(URI.create(URI_HOTELS + "/hotel/" +createdHotelId)).build();
    }

    @Override
    public ResponseEntity<Void> addRoom(String id, RoomRequest roomRequest) {
        hotelService.addRoomInHotel(id, roomRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<HotelResponse> getHotel(String id) {
        var hotel = hotelService.getHotelById(id);
        return hotel.map(hotelResponse -> ResponseEntity.ok().body(hotelResponse)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<HotelListResponse> getHotels() {
        var hotels = hotelService.getAllHotels();
        return hotels.getHotels().isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(hotels);
    }

    @Override
    public ResponseEntity<RoomListResponse> getRoomsInHotel(String id) {
        var rooms = hotelService.getAllRoomsInHotel(id);
        return rooms == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(rooms);
    }
}
