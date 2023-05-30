package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import org.openapitools.model.*;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.Facilite;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.RoomEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        return new HotelResponse()
                .id(BigDecimal.valueOf(hotelEntity.getId()))
                .name(hotelEntity.getName())
                .longitude(BigDecimal.valueOf(Long.parseLong(hotelEntity.getLongitude())))
                .latitude(BigDecimal.valueOf(Long.parseLong(hotelEntity.getLatitude())))
                .city(hotelEntity.getCity())
                .street(hotelEntity.getStreet())
                .number(hotelEntity.getNumber())
                .additionalAddressInformation(hotelEntity.getAdditionalAddressInformation())
                .phoneNumber(hotelEntity.getPhoneNumber())
                .rooms(hotelEntity.getRoomEntities().stream().map(HotelMapper::toRoomResponse).toList());
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
                .roomFacilities(roomEntity.getRoomFacilities().stream().map(HotelMapper::toRoomFacilitiesResponse).toList());
    }
}
