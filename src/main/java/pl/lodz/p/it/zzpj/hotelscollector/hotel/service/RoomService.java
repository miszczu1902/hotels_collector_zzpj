package pl.lodz.p.it.zzpj.hotelscollector.hotel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.EditRoomRequest;
import org.openapitools.model.RoomRequest;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.controller.HotelMapper;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public List<RoomEntity> getAllRoomsInHotel(HotelEntity hotelEntity) {
        return roomRepository.findByHotelEntity(hotelEntity);
    }

    @Transactional
    public boolean deleteRoom(String id) {
        var room = roomRepository.findById(id);
        if(room.isPresent()) {
            roomRepository.delete(room.get());
        } else {
            return false;
        }
        return true;
    }

    @Transactional
    public Optional<RoomEntity> getRoom(String id) {
        return roomRepository.findById(id);
    }

    @Transactional
    public long addRoom(RoomRequest roomRequest, HotelEntity hotelEntity) {
        RoomEntity createdRoom = roomRepository.save(HotelMapper.toRoomEntity(roomRequest, hotelEntity));
        return createdRoom.getId();
    }

    @Transactional
    public boolean addFacilities(String id, String facilities) {
        var modifiedRoom = roomRepository.findById(id);
        if(modifiedRoom.isPresent()) {
            var modification = modifiedRoom.get();
            modification.setRoomFacilities(modification.getRoomFacilities().concat(facilities));
            roomRepository.save(modification);
        } else {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean editRoom(String id, EditRoomRequest roomRequest) {
        var modifiedRoom = roomRepository.findById(id);
        if(modifiedRoom.isPresent()) {
            var modification = modifiedRoom.get();
            modification.setMaximumGuestNumber(roomRequest.getMaximumGuestNumber());
            modification.setSoundproofing(roomRequest.getIsSoundproofing());
            modification.setAirConditioning(roomRequest.getIsAirConditioning());
            modification.setDescription(roomRequest.getDescription());
            modification.setRoomFacilities(roomRequest.getRoomFacilities().toString()
                    .replace("[", " ")
                    .replace("]", " "));
            roomRepository.save(modification);
        } else {
            return false;
        }
        return true;

    }
}
