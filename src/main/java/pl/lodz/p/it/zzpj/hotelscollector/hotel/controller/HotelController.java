package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.HotelsApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.HotelService;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.RoomService;
import pl.lodz.p.it.zzpj.hotelscollector.user.UserService;

import java.net.URI;

import static pl.lodz.p.it.zzpj.hotelscollector.utils.Constans.URI_HOTELS;

@RequiredArgsConstructor
@RestController
public class HotelController implements HotelsApi {

    private final HotelService hotelService;
    private final RoomService roomService;

    @Override
    public ResponseEntity<HotelResponse> addHotel(HotelRequest hotelRequest) {
        long createdHotelId = hotelService.addHotel(hotelRequest);
        return ResponseEntity
                .created(URI.create(URI_HOTELS + "/hotel/" +createdHotelId)).build();
    }

    @Override
    public ResponseEntity<Void> addRoom(String id, RoomRequest roomRequest) {
        return HotelsApi.super.addRoom(id, roomRequest);
    }

    @Override
    public ResponseEntity<HotelResponse> getHotel(String id) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<HotelListResponse> getHotels() {
        return HotelsApi.super.getHotels();
    }

    @Override
    public ResponseEntity<RoomListResponse> getRoomsInHotel(String id) {
        return HotelsApi.super.getRoomsInHotel(id);
    }
}
