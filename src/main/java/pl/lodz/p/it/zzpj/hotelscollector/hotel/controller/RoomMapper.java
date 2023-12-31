package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import org.openapitools.model.RoomFacilitiesResponse;
import org.openapitools.model.RoomRequest;
import org.openapitools.model.RoomResponse;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.Facilite;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;

import java.math.BigDecimal;

public class RoomMapper {

    public static RoomResponse toRoomResponse(RoomEntity roomEntity) {
        return new RoomResponse()
                .id(BigDecimal.valueOf(roomEntity.getId()))
                .roomSize(BigDecimal.valueOf(roomEntity.getRoomSize()))
                .maximumGuestNumber(roomEntity.getMaximumGuestNumber())
                .isAirConditioning(roomEntity.isAirConditioning())
                .isSoundproofing(roomEntity.isSoundproofing())
                .description(roomEntity.getDescription())
                .roomFacilities(roomEntity.getRoomFacilities()
                        .replace("[", " ")
                        .replace("]", " "));
    }

    private static RoomFacilitiesResponse toRoomFacilitiesResponse(Facilite facilite) {
        return RoomFacilitiesResponse.valueOf(facilite.name());
    }

    public static RoomEntity toRoomEntity(RoomRequest roomRequest, HotelEntity hotelEntity) {
        return new RoomEntity(
                roomRequest.getRoomSize().longValue(),
                roomRequest.getMaximumGuestNumber(),
                roomRequest.getRoomFacilities().stream().map(HotelMapper::toFacilite).toList().toString()
                        .replace("[", " ")
                        .replace("]", " "),
                roomRequest.getIsAirConditioning(),
                roomRequest.getIsSoundproofing(),
                roomRequest.getDescription(),
                hotelEntity);
    }
}
