package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import org.openapitools.model.*;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.Facilite;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HotelMapper {


    public static HotelEntity toHotelEntity(HotelRequest hotelRequest) {
        return new HotelEntity(
                hotelRequest.getName(),
                hotelRequest.getLongitude(),
                hotelRequest.getLatitude(),
                hotelRequest.getCity(),
                hotelRequest.getStreet(),
                hotelRequest.getNumber(),
                hotelRequest.getAdditionalAddressInformation(),
                hotelRequest.getPhoneNumber(),
                new ArrayList<>()
        );
    }

    public static HotelResponse toHotelResponse(HotelEntity hotelEntity) {
        HotelResponse hotelResponse = new HotelResponse()
                .id(BigDecimal.valueOf(hotelEntity.getId()))
                .name(hotelEntity.getName())
                .longitude(hotelEntity.getLongitude())
                .latitude(hotelEntity.getLatitude())
                .city(hotelEntity.getCity())
                .street(hotelEntity.getStreet())
                .number(hotelEntity.getNumber())
                .additionalAddressInformation(hotelEntity.getAdditionalAddressInformation())
                .phoneNumber(hotelEntity.getPhoneNumber());

        List<RoomResponse> roomResponses = new ArrayList<>();
        List<RoomEntity> roomEntities = hotelEntity.getRoomEntities();

        if (roomEntities != null && !roomEntities.isEmpty()) {
            roomResponses = roomEntities.stream().map(HotelMapper::toRoomResponse).toList();
        }

        hotelResponse.setRooms(roomResponses);
        return hotelResponse;
    }


    public static Facilite toFacilite(RoomFacilitiesResponse roomFacilitiesResponse) {
        return Facilite.valueOf(roomFacilitiesResponse.getValue());
    }

    public static RoomFacilitiesResponse toRoomFacilitiesResponse(Facilite facilite) {
        return RoomFacilitiesResponse.valueOf(facilite.name());
    }

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

    public static RoomEntity toRoomEntity(RoomRequest roomRequest, HotelEntity hotelEntity) {
        return new RoomEntity(roomRequest.getRoomSize().longValue(),
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
