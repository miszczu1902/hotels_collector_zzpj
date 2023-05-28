package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import org.openapitools.api.HotelsApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
public class HotelController implements HotelsApi {

    @Override
    public ResponseEntity<Void> addHotel(HotelRequest hotelRequest) {
        return HotelsApi.super.addHotel(hotelRequest);
    }

    @Override
    public ResponseEntity<Void> addRoom(String id, RoomRequest roomRequest) {
        return HotelsApi.super.addRoom(id, roomRequest);
    }

    @Override
    public ResponseEntity<HotelResponse> getHotel(String id) {
        return HotelsApi.super.getHotel(id);
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
