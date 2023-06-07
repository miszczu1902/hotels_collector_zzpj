package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.HotelsApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.HotelService;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.service.RoomService;

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
        var hotel = hotelService.getHotelById(id);
        if(hotel.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        long createdRoomId = roomService.addRoom(roomRequest, hotel.get());
        return ResponseEntity
                .created(URI.create(URI_HOTELS + "/room/" +createdRoomId)).build();
    }

    @Override
    public ResponseEntity<HotelResponse> getHotel(String id) {
        var hotel = hotelService.getHotelById(id);
        if (hotel.isPresent()) {
            var responseBody = HotelMapper.toHotelResponse(hotel.get());
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<HotelListResponse> getHotels() {
        if(hotelService.getAllHotels() == null) {
            return ResponseEntity.notFound().build();
        }
        var responseBody = hotelService.getAllHotels().stream().map(HotelMapper::toHotelResponse).toList();
        return ResponseEntity.ok(new HotelListResponse().hotels(responseBody));
    }

    @Override
    public ResponseEntity<RoomListResponse> getRoomsInHotel(String id) {
        var hotel = hotelService.getHotelById(id);
        if(hotel.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        var rooms = roomService.getAllRoomsInHotel(hotel.get());
        var responseBody = rooms.stream().map(HotelMapper::toRoomResponse).toList();
        return ResponseEntity.ok(new RoomListResponse().rooms(responseBody));
    }

    @Override
    public ResponseEntity<Void> deleteHotel(String id) {
        var deleted = hotelService.deleteHotel(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteRoomInHotel(String id) {
        var deleted = roomService.deleteRoom(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<RoomResponse> getRoomInHotel(String id) {
        var room = roomService.getRoom(id);
        if (room.isPresent()) {
            var responseBody = HotelMapper.toRoomResponse(room.get());
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> addFacilitiesToRoom(String id, RoomFacilitiesRequest roomFacilitiesRequest) {
        var room = roomService.getRoom(id);
        if (room.isPresent()) {
            roomService.addFacilities(id, roomFacilitiesRequest.getFacilities().toString());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> editRoomInHotel(String id, EditRoomRequest editRoomRequest) {
        var room = roomService.getRoom(id);
        if (room.isPresent()) {
            roomService.editRoom(id,editRoomRequest);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
