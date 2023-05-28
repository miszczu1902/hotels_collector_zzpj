package pl.lodz.p.it.zzpj.hotelscollector.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.HotelRequest;
import pl.lodz.p.it.zzpj.hotelscollector.hotel.entity.HotelEntity;

public class HotelMapper {

    public static HotelEntity toHotelEntity(HotelRequest hotelRequest) {
        return new HotelEntity(
                hotelRequest.getName(),
                hotelRequest.getLongitude().toPlainString(),
                hotelRequest.getLatitude().toPlainString(),
                hotelRequest.getCity(),
                hotelRequest.getStreet(),
                hotelRequest.getNumber(),
                hotelRequest.getAdditionalAddressInformation(),
                hotelRequest.getPhoneNumber(),
                null
        );
    }
}
